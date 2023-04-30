package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.EditorPanels.ToolEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class BrushTool extends AbstractTool {

    private final JComboBox<Integer> size;
    private final JComboBox<Integer> outline;
    private final JCheckBox isFilled;
    public BrushTool() {
        super("brush", "Brush", new Cursor(Cursor.HAND_CURSOR));

        size = new JComboBox<>(Arrays.copyOfRange(SIZES, 1, SIZES.length));
        size.setEditable(true);
        outline = new JComboBox<>(SIZES);
        outline.setEditable(true);
        isFilled = new JCheckBox();
        Toolbar.EDITOR.addToolEdits(getName(),
                ToolEditor.create(new JLabel("Selected Tool: Brush"), 20),
                ToolEditor.create(new JLabel("Size:"), 5),
                ToolEditor.create(size, 20),
                ToolEditor.create(new JLabel("Thickness:"), 5),
                ToolEditor.create(outline, 20),
                ToolEditor.create(new JLabel("Filled Shape:"), 5),
                ToolEditor.create(isFilled, 0));
    }

    @Override
    protected void toolSelected() {
        Toolbar.EDITOR.setCurrentEditor(getName());
    }

    private void paint(Canvas canvas, int x, int y) {
        Integer brushRadius = (Integer) size.getSelectedItem();
        Integer outlineThickness = (Integer) outline.getSelectedItem();
        if(brushRadius == null || outlineThickness == null) return;

        canvas.changePixelLinearly(x, y, isFilled.isSelected(), brushRadius, outlineThickness);
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
    public void inputConflictClear(Canvas canvas, MouseEvent e) {
        canvas.releasePixels();
    }

    @Override
    public void dragged(Canvas canvas, MouseEvent e) {
        paint(canvas, e.getX(), e.getY());
    }

    @Override
    public void moved(Canvas canvas, MouseEvent e) {}
}
