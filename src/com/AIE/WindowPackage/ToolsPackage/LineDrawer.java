package com.AIE.WindowPackage.ToolsPackage;

import com.AIE.Canvas;

import java.util.ArrayList;

public class LineDrawer {

    private final ArrayList<Pixel> trackedPixels;

    public LineDrawer() {
        this.trackedPixels = new ArrayList<>();
    }

    public void addPixel(int x, int y) {
        trackedPixels.add(new Pixel(x, y));
    }

    public void drawLines(Canvas canvas) {
        for(int i = 0; i < trackedPixels.size(); i++) {
            if(trackedPixels.size() <= 1)
                return;
            Pixel p1 = trackedPixels.get(i);
            Pixel p2 = trackedPixels.get(i+1);
            try {
                drawLine(p1.x, p1.y, p2.x, p2.y, canvas);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            trackedPixels.remove(i);
            i--;
        }
    }

    private void drawLine(int x1, int y1, int x2, int y2, Canvas canvas) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            canvas.changePixelColor(x1, y1);
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
