package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.EditorPanels.ToolEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PickerTool extends AbstractTool {

    private final MutableColor color;

    public PickerTool() {
        super("picker", "Pick Color", new Cursor(Cursor.HAND_CURSOR)); //"Pencil\nShortcut Key: P"
        color = new MutableColor(0);
        Toolbar.EDITOR.addToolEdits(getName(), ToolEditor.create(new JLabel("Selected Tool: Color Picker"), 0));
    }

    @Override
    protected void toolSelected() {
        Toolbar.EDITOR.setCurrentEditor(getName());
    }

    private void selectColor(Canvas canvas, int x, int y, int brushID) {
        int picked = canvas.getColor(canvas.getScaledX(x), canvas.getScaledY(y));
        color.setRGBA(picked);
        ColorPalette.selectBrush(brushID);
        ColorPalette.update(color, "brush");
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        selectColor(canvas, e.getX(), e.getY(),
                SwingUtilities.isRightMouseButton(e) ? 1 : 0);
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {}

    @Override
    public void inputConflictClear(Canvas canvas, MouseEvent e) {}

    @Override
    public void dragged(Canvas canvas, MouseEvent e) {
        selectColor(canvas, e.getX(), e.getY(),
                SwingUtilities.isRightMouseButton(e) ? 1 : 0);
    }

    @Override
    public void moved(Canvas canvas, MouseEvent e) {}
}
