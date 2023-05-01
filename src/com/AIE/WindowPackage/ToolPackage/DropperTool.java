package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ColorPackage.MutableColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DropperTool extends AbstractTool {

    private final MutableColor color;

    public DropperTool() {
        super("Dropper", new Cursor(Cursor.HAND_CURSOR)); //"Pencil\nShortcut Key: P"
        color = new MutableColor(0);
        Toolbar.EDITOR.addToolEdits(getName());
    }

    @Override
    protected void toolSelected() {
        super.toolSelected();
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
