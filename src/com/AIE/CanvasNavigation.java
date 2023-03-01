package com.AIE;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class CanvasNavigation extends MouseAdapter {

    private int startX, startY;
    private int mousePressedX, mousePressedY;

    @Override
    public void mousePressed(MouseEvent e) {
        if(!SwingUtilities.isMiddleMouseButton(e)) return;
        initNavigation(e.getX(), e.getY(),
                CanvasManager.getCurrentCanvas().getPosX(), CanvasManager.getCurrentCanvas().getPosY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!SwingUtilities.isMiddleMouseButton(e)) return;
        navigate(e.getX(), e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        CanvasManager.getCurrentCanvas().setZoom(CanvasManager.getCurrentCanvas().getZoom()-e.getWheelRotation()*5);
    }

    protected void navigate(int mx, int my) {
        int diffX = mx - mousePressedX;
        int diffY = my - mousePressedY;

        CanvasManager.getCurrentCanvas().setPosXY(startX + diffX, startY + diffY);
    }

    protected void initNavigation(int mx, int my, int cx, int cy) {
        mousePressedX = mx;
        mousePressedY = my;
        startX = cx;
        startY = cy;
    }
}
