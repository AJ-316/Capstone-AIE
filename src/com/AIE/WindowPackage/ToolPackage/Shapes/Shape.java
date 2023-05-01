package com.AIE.WindowPackage.ToolPackage.Shapes;

import com.AIE.CanvasPackage.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class Shape {

    private final String name;
    protected boolean drawing;
    protected BufferedImage canvasImage;
    protected BufferedImage backgroundImage;
    protected BufferedImage shapeImage;
    protected ShapeConstraints currentConstraints;

    public Shape(String name) {
        this.name = name;
    }

    public boolean isNotDrawing() {
        return !drawing;
    }

    public void createImage(Canvas canvas) {
        BufferedImage canvasImg = canvas.getImage();
        shapeImage = new BufferedImage(canvasImg.getWidth(),
                canvasImg.getHeight(), canvasImg.getType());

        canvasImage = new BufferedImage(canvasImg.getWidth(),
                canvasImg.getHeight(), canvasImg.getType());

        backgroundImage = new BufferedImage(canvasImg.getWidth(),
                canvasImg.getHeight(), canvasImg.getType());
        Graphics2D g2d = backgroundImage.createGraphics();
        g2d.drawImage(canvasImg, 0, 0, null);
        g2d.dispose();
    }

    public abstract void paint(Canvas canvas, MouseEvent e);
    public abstract void pressed(Canvas canvas, MouseEvent e);
    public abstract void released(Canvas canvas, MouseEvent e);

    public abstract void moved(Canvas canvas, MouseEvent e);

    @Override
    public String toString() {
        return name;
    }

    protected void antiAlias(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void setCurrentConstraints(ShapeConstraints currentConstraints) {
        this.currentConstraints = currentConstraints;
    }

    public ShapeConstraints getCurrentConstraints() {
        return currentConstraints;
    }
}
