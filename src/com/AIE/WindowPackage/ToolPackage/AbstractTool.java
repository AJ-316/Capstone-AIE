package com.AIE.WindowPackage.ToolPackage;

import com.AIE.Canvas;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

public abstract class AbstractTool extends JRadioButton implements ItemListener {

    protected Cursor cursor;

    public AbstractTool(String icon, Cursor cursor) {
        super();
        this.cursor = cursor;
        addItemListener(this);
        setIcons(icon);
    }

    private void setIcons(String icon) {
        setIcon(MainFrame.loadImage(icon, 0.5f));
        setSelectedIcon(MainFrame.loadImage(icon+"_pressed", 0.5f));
        setRolloverIcon(MainFrame.loadImage(icon+"_rollover", 0.5f));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.DESELECTED)
            return;
        // Change cursor

        toolSelected();
    }

    protected abstract void toolSelected();

    public abstract void inputConflictClear(Canvas canvas, MouseEvent e);

    public abstract void pressed(Canvas canvas, MouseEvent e);
    public abstract void released(Canvas canvas, MouseEvent e);
    public abstract void dragged(Canvas canvas, MouseEvent e);
    public abstract void moved(Canvas canvas, MouseEvent e);
}