package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PencilTool extends AbstractTool {

    public PencilTool() {
        super("Pencil", new Cursor(Cursor.HAND_CURSOR));
        Toolbar.EDITOR.addToolEdits(getName());
        setSelected(true);
    }

    @Override
    protected void toolSelected() {
        super.toolSelected();
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        saveOld(canvas, true);
        canvas.changePixelLinearly(e.getX(), e.getY(), false, 1, -1);
        canvas.updateCanvas();
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {
        canvas.releasePixels();
        saveCurrent();
    }

    @Override
    public void inputConflictClear(Canvas canvas, MouseEvent e) {
        canvas.releasePixels();
    }

    @Override
    public void dragged(Canvas canvas, MouseEvent e) {
        canvas.changePixelLinearly(e.getX(), e.getY(), false, 1, -1);
        canvas.updateCanvas();
    }

    @Override
    public void moved(Canvas canvas, MouseEvent e) {}
}
