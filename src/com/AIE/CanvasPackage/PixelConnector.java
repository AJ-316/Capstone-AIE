package com.AIE.CanvasPackage;

import com.AIE.AppLog;

import java.awt.*;
import java.util.ArrayList;

public class PixelConnector {

    private final ArrayList<Pixel> trackedPixels;
    private final Canvas canvas;

    public PixelConnector(Canvas canvas) {
        this.trackedPixels = new ArrayList<>();
        this.canvas = canvas;
    }

    public void addPixel(int x, int y, Color color, boolean isFilled, int size, int outline) {
        trackedPixels.add(new Pixel(x, y));
        connectPixels(color, isFilled, size, outline);
    }

    public void connectPixels(Color color, boolean isFilled, int size, int outline) {
        for(int i = 0; i < trackedPixels.size(); i++) {
            if(trackedPixels.size() <= 1)
                return;
            Pixel p1 = trackedPixels.get(i);
            Pixel p2 = trackedPixels.get(i+1);
            try {
                connectPixel(p1.x, p1.y, p2.x, p2.y, color,isFilled, size, outline);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                AppLog.error("PixelConnector", "Math Error while connecting pixels", e);
            }
            trackedPixels.remove(i);
            i--;
        }
    }

    private void connectPixel(int x1, int y1, int x2, int y2, Color color, boolean isFilled, int size, int outline) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            if(color == null) {
                canvas.erase(x1, y1, size);
            } else {
                if (size == -1) {
                    canvas.changeRawPixel(x1, y1, color.getRGB());
                } else
                    canvas.drawCircle(x1, y1, color, isFilled, size, outline);
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public void releasePixels() {
        trackedPixels.clear();
    }

    private record Pixel(int x, int y) {}
}
