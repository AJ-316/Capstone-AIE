package com.AIE;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class CanvasManagerNavigation extends MouseAdapter {

    private int startX, startY;
    private int mousePressedX, mousePressedY;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        CanvasManager.getCurrentCanvas().setScale(CanvasManager.getCurrentCanvas().getScale()-e.getWheelRotation());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!SwingUtilities.isMiddleMouseButton(e)) return;
        initNavigation(e.getX(), e.getY(), CanvasManager.getCurrentCanvas().getX(), CanvasManager.getCurrentCanvas().getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!SwingUtilities.isMiddleMouseButton(e)) return;
        navigate(e.getX(), e.getY());
    }

    protected void navigate(int mx, int my) {
        int diffX = mx - mousePressedX;
        int diffY = my - mousePressedY;

        CanvasManager.getCurrentCanvas().setLocation(startX + diffX, startY + diffY);
    }

    protected void initNavigation(int mx, int my, int cx, int cy) {
        mousePressedX = mx;
        mousePressedY = my;
        startX = cx;
        startY = cy;
    }


}
