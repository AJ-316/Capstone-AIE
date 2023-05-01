package com.AIE.StackPackage;

import com.AIE.WindowPackage.ToolPackage.SmoothIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SavedDataButton extends JButton implements ActionListener {

    public static final int ICON_SIZE = 24;
    private final UndoManager undoManager;
    private final int id;
    private final Color color;
    private boolean isUndo = true;

    public SavedDataButton(SmoothIcon icon, String action, int id, UndoManager undoManager) {
        super(action);
        if(icon != null) setIcon(icon);
        setBorder(new EmptyBorder(getBorder().getBorderInsets(this)));
        this.undoManager = undoManager;
        this.id = id;
        color = getBackground();
        addActionListener(this);
        setToolTipText(action);
    }

    public void undoHighlight() {
        setBackground(Color.black);
        isUndo = false;
    }

    public void redoHighlight() {
        setBackground(color);
        isUndo = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isUndo)
            undoManager.undo(id);
        else undoManager.redo(id);
    }
}
