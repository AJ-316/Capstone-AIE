package com.AIE.WindowPackage.ToolPackage.Shapes;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.EditorPanels.ToolEditor;
import com.AIE.WindowPackage.ToolPackage.AbstractTool;
import com.AIE.WindowPackage.ToolPackage.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ShapesTool extends AbstractTool {

    private final JComboBox<Shape> shapes;
    private final JCheckBox isFilled;
    private final JComboBox<Integer> roundedCorners;
    private final JComboBox<Float> outline;
    private Shape currentShape;

    public ShapesTool() {
        super("shapes", "Multiple Shapes", new Cursor(Cursor.HAND_CURSOR));
        shapes = new JComboBox<>(new Shape[]{
                new Rectangle(),
                new RoundRectangle(),
                new Oval(),
                new Polygon()
        });
        outline = new JComboBox<>(SIZES_F);
        outline.setSelectedIndex(9);
        outline.setEditable(true);
        roundedCorners = new JComboBox<>(SIZES);
        roundedCorners.setEditable(true);
        isFilled = new JCheckBox();
        isFilled.setSelected(true);
        Toolbar.EDITOR.addToolEdits(getName(),
                ToolEditor.create(new JLabel("Selected Tool: Shapes"), 20),
                ToolEditor.create(new JLabel("Shape: "), 5),
                ToolEditor.create(shapes, 20),
                ToolEditor.create(new JLabel("Filled Shape: "), 5),
                ToolEditor.create(isFilled, 20),
                ToolEditor.create(new JLabel("Outline Thickness: "), 5),
                ToolEditor.create(outline, 20),
                ToolEditor.create(new JLabel("Sharp Corners: "), 5),
                ToolEditor.create(roundedCorners, 0));
    }

    @Override
    protected void toolSelected() {
        Toolbar.EDITOR.setCurrentEditor(getName());
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        if(currentShape == null || currentShape.isNotDrawing()) {
            currentShape = (Shape) shapes.getSelectedItem();
            assert currentShape != null;
            currentShape.createImage(canvas);
        }

        currentShape.pressed(canvas, e);
        Toolbar.locked(true);
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {
        if(currentShape == null || !currentShape.drawing)
            return;

        currentShape.released(canvas, e);
        currentShape.setCurrentConstraints(null);
        canvas.confirmPreview();
        canvas.disablePreviewMode();
        Toolbar.locked(false);
    }

    @Override
    public void inputConflictClear(Canvas canvas, MouseEvent e) {
        if(currentShape == null || !currentShape.drawing)
            return;

        currentShape.released(canvas, e);
        currentShape.setCurrentConstraints(null);
        canvas.confirmPreview();
        canvas.disablePreviewMode();
        Toolbar.locked(false);
    }

    @Override
    public void dragged(Canvas canvas, MouseEvent e) {
        Integer rounded = (Integer) roundedCorners.getSelectedItem();
        Float stroke = (Float) outline.getSelectedItem();
        assert rounded != null;
        assert stroke != null;
        currentShape.setCurrentConstraints(new ShapeConstraints(isFilled.isSelected(),
                rounded, stroke));
        currentShape.paint(canvas, e);
    }

    @Override
    public void moved(Canvas canvas, MouseEvent e) {
        if(currentShape == null || currentShape.isNotDrawing()) return;

        if(currentShape.getCurrentConstraints() == null) {
            Integer rounded = (Integer) roundedCorners.getSelectedItem();
            Float stroke = (Float) outline.getSelectedItem();
            assert rounded != null;
            assert stroke != null;
            currentShape.setCurrentConstraints(new ShapeConstraints(isFilled.isSelected(),
                    rounded, stroke));
        }
        currentShape.paint(canvas, e);
    }
}
