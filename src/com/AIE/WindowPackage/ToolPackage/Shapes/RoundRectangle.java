package com.AIE.WindowPackage.ToolPackage.Shapes;

import com.AIE.CanvasPackage.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RoundRectangle extends Shape {

    private Point startPoint;
    private Point endPoint;

    public RoundRectangle() {
        super("RoundRectangle");
    }

    @Override
    public void paint(Canvas canvas, MouseEvent e) {
        endPoint = canvas.getScaledPoint(e.getPoint());

        Graphics2D shape = shapeImage.createGraphics();
        shape.setBackground(Canvas.TRANSPARENT);
        shape.clearRect(0, 0, shapeImage.getWidth(), shapeImage.getHeight());
        shape.setColor(canvas.getColor());
        antiAlias(shape);

        if (drawing) {
            int x = Math.min(startPoint.x, endPoint.x);
            int y = Math.min(startPoint.y, endPoint.y);
            int width = Math.abs(endPoint.x - startPoint.x);
            int height = Math.abs(endPoint.y - startPoint.y);

            int arcSize = Math.min(width, height) / currentConstraints.rounded();
            shape.setStroke(new BasicStroke(currentConstraints.stroke()));

            if(currentConstraints.isFilled())
                shape.fillRoundRect(x, y, width, height, arcSize, arcSize);
            else shape.drawRoundRect(x, y, width, height, arcSize, arcSize);
        }

        Graphics2D canvasImg = canvasImage.createGraphics();
        canvasImg.drawImage(backgroundImage, 0, 0, null);
        canvasImg.drawImage(shapeImage, 0, 0, null);
        canvasImg.dispose();

        canvas.setPreviewImage(canvasImage);
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        startPoint = canvas.getScaledPoint(e.getPoint());
        endPoint = new Point(startPoint);
        drawing = true;
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {
        if(!drawing) return;
        drawing = false;
        shapeImage = null;
        backgroundImage = null;
        canvasImage = null;
    }

    public void moved(Canvas canvas, MouseEvent e){}
}
