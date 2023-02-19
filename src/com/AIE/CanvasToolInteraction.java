package com.AIE;

import com.AIE.WindowPackage.ToolsPackage.AbstractTool;
import com.AIE.WindowPackage.ToolsPackage.Toolbar;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasToolInteraction extends MouseAdapter {

    private final Canvas canvas;

    public CanvasToolInteraction(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        AbstractTool tool = Toolbar.getCurrentTool();
        if(tool == null) return;

        if(invalidInput(e)) {
            tool.inputConflictClear(canvas, e);
            return;
        }
        tool.pressed(canvas, e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        AbstractTool tool = Toolbar.getCurrentTool();
        if(tool == null) return;

        if(invalidInput(e)) {
            tool.inputConflictClear(canvas, e);
            return;
        }
        tool.released(canvas, e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        AbstractTool tool = Toolbar.getCurrentTool();
        if(tool == null) return;

        if(invalidInput(e)) {
            tool.inputConflictClear(canvas, e);
            return;
        }
        tool.dragged(canvas, e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse on canvas x:" + e.getX() + ", y:" + e.getY());
        AbstractTool tool = Toolbar.getCurrentTool();
        if(tool != null)
            tool.moved(canvas, e);
    }

    private boolean invalidInput(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isRightMouseButton(e))
            return SwingUtilities.isMiddleMouseButton(e);
        return true;
    }
}
