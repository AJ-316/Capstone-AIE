package com.AIE.StackPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.WindowPackage.History;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class UndoManager extends KeyAdapter {
    private final Stack<SavedDataListener> undoStack;
    private final Stack<SavedDataListener> redoStack;
    private final Canvas canvas;

    public UndoManager(Canvas canvas) {
        this.canvas = canvas;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        canvas.addKeyListenerToRootPane(this);
    }
    public void saveForUndo(SavedDataListener data) {
        undoStack.add(data);
        History.GET.removeButtons(redoStack.size());
        redoStack.clear();
        History.GET.addButton(new SavedDataButton(data.getIcon(), data.getAction(), undoStack.size(), this));
    }
    public void undo() {
        if(undoStack.isEmpty() || undoStack.size() == 1) return;
        int id = undoStack.size();
        SavedDataListener data = undoStack.pop();
        if(data.undo()) {
            History.GET.getButton(id).undoHighlight();
            redoStack.push(data);
        }
    }
    public void undo(int id) {
        for(int i = undoStack.size(); i > id; i--)
            undo();
    }
    public void redo() {
        if(redoStack.isEmpty()) return;
        int id = undoStack.size() + 1;
        SavedDataListener data = redoStack.pop();
        if(data.redo()) {
            History.GET.getButton(id).redoHighlight();
            undoStack.push(data);
        }
    }
    public void redo(int id) {
        for(int i = 0; i < id-undoStack.size(); i++)
            redo();
    }
    public void dispose() {
        undoStack.clear();
        redoStack.clear();
        History.GET.dispose();
        canvas.removeKeyListenerFromRootPane(this);
    }
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(e.isControlDown() && key == KeyEvent.VK_Z)
            undo();
        if(e.isControlDown() && key == KeyEvent.VK_Y)
            redo();
    }
}
