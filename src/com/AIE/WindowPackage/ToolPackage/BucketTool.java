package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.EditorPanels.ToolEditor;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class BucketTool extends AbstractTool {

    private final JSlider tolerance;

    public BucketTool() {
        super("bucket", "Bucket Fill", new Cursor(Cursor.HAND_CURSOR));
        JTextField toleranceField = new JTextField(3);
        tolerance = MainFrame.getSlider(toleranceField, 50, 0, 100, 50);
        tolerance.setMinorTickSpacing(10);
        Toolbar.EDITOR.addToolEdits(getName(),
                ToolEditor.create(new JLabel("Selected Tool: Bucket Fill"), 25),
                ToolEditor.create(new JLabel("Tolerance:"), 5),
                ToolEditor.create(tolerance, 2),
                ToolEditor.create(toleranceField, 0));
        setSelected(true);
    }

    @Override
    protected void toolSelected() {
        Toolbar.EDITOR.setCurrentEditor(getName());
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        canvas.floodFill(e.getX(), e.getY(), (int)(tolerance.getValue()/100f * 255)); //, (int)(tolerance.getValue()/100f * 255)
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {}

    @Override
    public void inputConflictClear(Canvas canvas, MouseEvent e) {}

    @Override
    public void dragged(Canvas canvas, MouseEvent e) {
    }

    @Override
    public void moved(Canvas canvas, MouseEvent e) {}
}
