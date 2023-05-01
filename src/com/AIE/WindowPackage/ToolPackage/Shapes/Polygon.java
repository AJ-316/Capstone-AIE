package com.AIE.WindowPackage.ToolPackage.Shapes;

import com.AIE.CanvasPackage.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Polygon extends Shape {

    private final ArrayList<Point> vertices;
    private boolean previewLine = true;
    private Color color = null;

    public Polygon() {
        super("Polygon");
        vertices = new ArrayList<>();
    }

    @Override
    public void paint(Canvas canvas, MouseEvent e) {
        if (vertices.size() == 0) return;

        Graphics2D shape = shapeImage.createGraphics();
        shape.setBackground(Canvas.TRANSPARENT);
        shape.clearRect(0, 0, shapeImage.getWidth(), shapeImage.getHeight());
        shape.setColor(color);

        if (drawing) {
            shape.setStroke(new BasicStroke(currentConstraints.stroke()));
            Point point = canvas.getScaledPoint(e.getPoint());
            if (vertices.size() > 2) {
                int[] x = extractAxis(0, point);
                int[] y = extractAxis(1, point);
                if (currentConstraints.isFilled())
                    shape.fillPolygon(x, y, vertices.size());
                else shape.drawPolygon(x, y, vertices.size());
            } else {
                if (vertices.size() > 1) {
                    shape.drawLine(vertices.get(0).x, vertices.get(0).y,
                            vertices.get(1).x, vertices.get(1).y);
                }
            }
            if (previewLine)
                shape.drawLine(vertices.get(vertices.size() - 1).x, vertices.get(vertices.size() - 1).y,
                        point.x, point.y);
        }

        Graphics2D canvasImg = canvasImage.createGraphics();
        canvasImg.drawImage(backgroundImage, 0, 0, null);
        canvasImg.drawImage(shapeImage, 0, 0, null);
        canvasImg.dispose();

        canvas.setPreviewImage(canvasImage);
    }

    public void moved(Canvas canvas, MouseEvent e) {
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) return;

        vertices.add(canvas.getScaledPoint(e.getPoint()));
        drawing = true;
        color = canvas.getColor();
        previewLine = true;
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {
        if (!SwingUtilities.isRightMouseButton(e)) return;
        previewLine = false;
        paint(canvas, e);
        drawing = false;
        shapeImage = null;
        backgroundImage = null;
        canvasImage = null;
        vertices.clear();
    }

    private int[] extractAxis(int axis, Point prePoint) {
        int[] axisPoints = new int[vertices.size() + 1];
        for (int i = 0; i < vertices.size(); i++) {
            axisPoints[i] = axis == 0 ? vertices.get(i).x : vertices.get(i).y;
        }
        axisPoints[vertices.size()] = axis == 0 ? prePoint.x : prePoint.y;
        return axisPoints;
    }
}
