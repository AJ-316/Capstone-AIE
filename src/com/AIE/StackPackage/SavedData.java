package com.AIE.StackPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ToolPackage.SmoothIcon;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SavedData implements SavedDataListener {

    private final Canvas canvas;
    private final BufferedImage old;
    private BufferedImage redone;
    private boolean isUndoable;
    private final String action;
    private final SmoothIcon icon;

    public SavedData(Canvas canvas, SmoothIcon icon, String action) {
        this.canvas = canvas;
        this.icon = icon;
        this.action = action;
        this.old = copyImage(canvas.getImage());
    }

    public void saveNewImage() {
        isUndoable = true;
        canvas.undoManager.saveForUndo(this);
    }

    @Override
    public boolean undo() {
        if(!isUndoable) return false;
        redone = copyImage(canvas.getImage());
        changeCurrent(old);
        return true;
    }

    @Override
    public boolean redo() {
        changeCurrent(redone);
        return true;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public SmoothIcon getIcon() {
        return icon;
    }

    private BufferedImage copyImage(BufferedImage imgA) {
        BufferedImage imgB = new BufferedImage(imgA.getWidth(), imgA.getHeight(), imgA.getType());
        Graphics2D g = imgB.createGraphics();
        g.setBackground(MutableColor.TRANSPARENT);
        g.clearRect(0,0, imgB.getWidth(), imgB.getHeight());
        g.drawImage(imgA, 0, 0, null);
        g.dispose();
        return imgB;
    }

    private void changeCurrent(BufferedImage imgA) {
        BufferedImage imgB = new BufferedImage(imgA.getWidth(), imgA.getHeight(), imgA.getType());
        Graphics2D g = imgB.createGraphics();
        g.setBackground(MutableColor.TRANSPARENT);
        g.clearRect(0,0, imgB.getWidth(), imgB.getHeight());
        g.drawImage(imgA, 0, 0, null);
        g.dispose();
        canvas.setImage(imgB, true);
        canvas.repaint();
    }
}
