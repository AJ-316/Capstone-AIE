package com.AIE.CanvasPackage;

import com.AIE.WindowPackage.ToolPackage.AbstractTool;
import com.AIE.WindowPackage.ToolPackage.Toolbar;

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
        canvas.setBrushType(SwingUtilities.isLeftMouseButton(e) ? 0 : SwingUtilities.isRightMouseButton(e) ? 1 : -1);
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
        canvas.setBrushType(SwingUtilities.isLeftMouseButton(e) ? 0 : SwingUtilities.isRightMouseButton(e) ? 1 : -1);
        tool.dragged(canvas, e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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
