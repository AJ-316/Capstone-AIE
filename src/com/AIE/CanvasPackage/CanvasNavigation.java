package com.AIE.CanvasPackage;

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
        Canvas canvas = CanvasManager.getCurrentCanvas();
        if(canvas == null)
            return;

        initNavigation(e.getX(), e.getY(), canvas.getPosX(), canvas.getPosY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!SwingUtilities.isMiddleMouseButton(e)) return;
        navigate(e.getX(), e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Canvas canvas = CanvasManager.getCurrentCanvas();
        if(canvas == null)
            return;

        int addZoom = (int) (-e.getWheelRotation()* Math.pow(canvas.getZoom(), 0.5f));
        if(!canvas.setZoom(canvas.getZoom() + addZoom))
            return;

        int width = (int) ((canvas.getImage().getWidth()/100f*addZoom)/2);
        int height = (int) ((canvas.getImage().getHeight()/100f*addZoom)/2);
        canvas.setPosXY(canvas.getPosX() - width, canvas.getPosY() - height);
    }

    protected void navigate(int mx, int my) {
        Canvas canvas = CanvasManager.getCurrentCanvas();
        if(canvas == null)
            return;

        int diffX = mx - mousePressedX;
        int diffY = my - mousePressedY;

        canvas.setPosXY(startX + diffX, startY + diffY);
    }

    protected void initNavigation(int mx, int my, int cx, int cy) {
        mousePressedX = mx;
        mousePressedY = my;
        startX = cx;
        startY = cy;
    }
}
