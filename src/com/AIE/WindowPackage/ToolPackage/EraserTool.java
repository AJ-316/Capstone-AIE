package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.EditorPanels.ToolEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class EraserTool extends AbstractTool {

    private final JComboBox<Integer> size;

    public EraserTool() {
        super("eraser", "Eraser", new Cursor(Cursor.HAND_CURSOR));
        size = new JComboBox<>(SIZES);
        size.setEditable(true);
        Toolbar.EDITOR.addToolEdits(getName(),
                ToolEditor.create(new JLabel("Selected Tool: Eraser"), 20),
                ToolEditor.create(new JLabel("Size:"), 5),
                ToolEditor.create(size, 0));
    }

    @Override
    protected void toolSelected() {
        Toolbar.EDITOR.setCurrentEditor(getName());
    }

    private void paint(Canvas canvas, int x, int y) {
        Integer eraserSize = (Integer) size.getSelectedItem();
        if(eraserSize == null) return;
        canvas.changePixelLinearly(x, y, null, true, eraserSize, 0);
        canvas.updateCanvas();
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        paint(canvas, e.getX(), e.getY());
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {
        canvas.releasePixels();
    }

    @Override
    public void inputConflictClear(Canvas canvas, MouseEvent e) { canvas.releasePixels(); }

    @Override
    public void dragged(Canvas canvas, MouseEvent e) {
        paint(canvas, e.getX(), e.getY());
    }

    @Override
    public void moved(Canvas canvas, MouseEvent e) {}
}
