package com.AIE.WindowPackage.ToolPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.ImageLoader;
import com.AIE.StackPackage.SavedData;
import com.AIE.StackPackage.SavedDataButton;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

public abstract class AbstractTool extends JRadioButton implements ItemListener {

    protected Cursor cursor;
    protected SavedData data;
    private static final int ICON_SIZE = 50;
    private static final int OVERLAY_INSET = 2;
    private static final int OVERLAY_ARC = 15;
    protected static final Integer[] SIZES = new Integer[] {1,2,3,4,5,6,7,8,9,10,
            11,12,13,14,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100};
    protected static final Float[] SIZES_F = new Float[] {.1f,.2f,.3f,.4f,.5f,.6f,
            .7f,.8f,.9f,1.0f, 1.1f,1.2f,1.3f,1.4f,1.5f,2.0f,2.5f,3.0f,3.5f,4.0f,4.5f,
            5.0f,5.5f, 6.0f,6.5f,7.0f,7.5f,8.0f,8.5f,9.0f,9.5f,10.0f};

    public AbstractTool(String name, Cursor cursor) {
        super();
        this.cursor = cursor;
        addItemListener(this);
        setName(name);
        setIcon(ImageLoader.loadIcon(name, ICON_SIZE));
        setToolTipText(name);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.DESELECTED)
            return;
        // Change cursor
        toolSelected();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        int pos = OVERLAY_INSET/2;
        int width = getWidth()-OVERLAY_INSET;
        int height = getHeight()-OVERLAY_INSET;

        if(model.isSelected()) {
            g.setColor(new Color(255, 255, 255, 75));
            g2.fillRoundRect(pos, pos, width, height, OVERLAY_ARC, OVERLAY_ARC);
            g.setColor(Color.white);
            g2.drawRoundRect(pos, pos, width, height, OVERLAY_ARC, OVERLAY_ARC);
        } else {
            g.setColor(new Color(255, 255, 255, 15));
            g2.drawRoundRect(pos, pos, width, height, OVERLAY_ARC, OVERLAY_ARC);
        }

        if(model.isRollover() && !model.isSelected()) {
            g.setColor(new Color(255, 255, 255, 50));
            g2.fillRoundRect(pos, pos, width, height, OVERLAY_ARC, OVERLAY_ARC);
        }

        super.paint(g2);
    }

    protected void toolSelected() {
        Toolbar.EDITOR.setCurrentEditor(getName());
        if(InfoPanel.GET == null) return;
        InfoPanel.GET.setActivityInfo("Selected Tool: " + getName());
    }

    public abstract void inputConflictClear(Canvas canvas, MouseEvent e);

    public abstract void pressed(Canvas canvas, MouseEvent e);
    public abstract void released(Canvas canvas, MouseEvent e);
    public abstract void dragged(Canvas canvas, MouseEvent e);
    public abstract void moved(Canvas canvas, MouseEvent e);

    protected void saveOld(Canvas canvas, boolean color) {
        data = new SavedData(canvas,
                color ? new SmoothIcon(SavedDataButton.ICON_SIZE-10, canvas.getColor()) :
                ImageLoader.loadIcon(getName(), SavedDataButton.ICON_SIZE),
                (color ? "      " : "")+getName());
    }

    protected void saveOld(Canvas canvas, String name) {
        data = new SavedData(canvas,
                ImageLoader.loadIcon(name, SavedDataButton.ICON_SIZE), name);
    }

    protected void saveCurrent() {
        if(data == null) return;
        data.saveNewImage();
        data = null;
    }
}