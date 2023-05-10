package com.AIE.WindowPackage.ToolPackage.Shapes;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.PanelsPackage.ToolEditor;
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
    private boolean leastOncePaint;
    private int currentButton;

    public ShapesTool() {
        super("Multiple Shapes", new Cursor(Cursor.HAND_CURSOR));
        shapes = new JComboBox<>(new Shape[]{
                new Rectangle(),
                new RoundRectangle(),
                new Ellipse(),
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
        super.toolSelected();
    }

    @Override
    public void pressed(Canvas canvas, MouseEvent e) {
        if(Toolbar.LOCKED && currentButton != e.getButton()) return;

        if(currentShape == null || currentShape.isNotDrawing()) {
            currentShape = (Shape) shapes.getSelectedItem();
            assert currentShape != null;
            currentShape.createImage(canvas);
            saveOld(canvas, currentShape.getName());
        }

        currentShape.pressed(canvas, e);
        Toolbar.locked(true);
        currentButton = e.getButton();
    }

    @Override
    public void released(Canvas canvas, MouseEvent e) {
        if(currentShape != null && currentShape.isValidShape()) {
            if (currentShape == null) return;
            if (!leastOncePaint) currentShape.paint(canvas, e);

            currentShape.released(canvas, e);
            if (currentShape == null || currentShape.drawing)
                return;

            releaseShape(canvas, true);
            return;
        }
        releaseShape(canvas, false);
    }

    private void releaseShape(Canvas canvas, boolean confirm) {
        currentShape.setCurrentConstraints(null);
        currentShape = null;
        if(confirm) canvas.confirmPreview();
        canvas.disablePreviewMode();
        Toolbar.locked(false);
        if(confirm) saveCurrent();
        leastOncePaint = false;
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
        leastOncePaint = true;
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
