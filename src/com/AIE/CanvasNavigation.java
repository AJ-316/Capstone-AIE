package com.AIE;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class CanvasNavigation extends CanvasManagerNavigation {

    @Override
    public void mousePressed(MouseEvent e) {
        if(!SwingUtilities.isMiddleMouseButton(e)) return;
        Canvas canvas = (Canvas) e.getSource();
        initNavigation(canvas.getX() + e.getX(), canvas.getY() + e.getY(),
                CanvasManager.getCurrentCanvas().getX(), CanvasManager.getCurrentCanvas().getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!SwingUtilities.isMiddleMouseButton(e)) return;
        Canvas canvas = (Canvas) e.getSource();
        navigate(canvas.getX() + e.getX(), canvas.getY() + e.getY());
    }
}
