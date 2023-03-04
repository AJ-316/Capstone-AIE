package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;

import java.util.ArrayList;

public class PixelConnector {

    private final ArrayList<Pixel> trackedPixels;
    private final Canvas canvas;

    public PixelConnector(Canvas canvas) {
        this.trackedPixels = new ArrayList<>();
        this.canvas = canvas;
    }

    public void addPixel(int x, int y) {
        trackedPixels.add(new Pixel(x, y));
        connectPixels();
    }

    public void connectPixels() {
        for(int i = 0; i < trackedPixels.size(); i++) {
            if(trackedPixels.size() <= 1)
                return;
            Pixel p1 = trackedPixels.get(i);
            Pixel p2 = trackedPixels.get(i+1);
            try {
                connectPixel(p1.x, p1.y, p2.x, p2.y);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            trackedPixels.remove(i);
            i--;
        }
    }

    private void connectPixel(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            canvas.changeRawPixel(x1, y1);
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
