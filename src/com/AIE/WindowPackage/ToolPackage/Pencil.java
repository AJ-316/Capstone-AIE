package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.EditorPanels.ToolEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Pencil extends AbstractTool {

    public Pencil() {
        super("pencil", "Pencil", new Cursor(Cursor.HAND_CURSOR)); //"Pencil\nShortcut Key: P"

        Toolbar.EDITOR.addToolEdits(getName(), ToolEditor.create(new JLabel("Selected Tool: Pencil"), 5));
        setSelected(true);
    }

    @Override
    protected void toolSelected() {
        Toolbar.EDITOR.setCurrentEditor(getName());
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        canvas.changePixelLinearly(e.getX(), e.getY(), false, 1, 1);
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
        canvas.changePixelLinearly(e.getX(), e.getY(), false, -1, 1);
        canvas.updateCanvas();
    }

    @Override
    public void moved(Canvas canvas, MouseEvent e) {}
}
