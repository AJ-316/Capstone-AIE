package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Pencil extends AbstractTool {

    public Pencil() {
        super("icons/pencil", new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createTitledBorder("Pencil"));
        setBorderPainted(true);
    }

    @Override
    protected void toolSelected() {

    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        canvas.changePixelLinearly(e.getX(), e.getY());
        canvas.updateCanvas();
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {
        canvas.releasePixels();
    }

    @Override
    public void inputConflictClear(Canvas canvas, MouseEvent e) {
        canvas.releasePixels();
    }

    @Override
    public void dragged(Canvas canvas, MouseEvent e) {
        canvas.changePixelLinearly(e.getX(), e.getY());
        canvas.updateCanvas();
    }

    @Override
    public void moved(Canvas canvas, MouseEvent e) {}
}
