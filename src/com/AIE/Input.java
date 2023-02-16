package com.AIE;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private final int NUM_KEYS = 189;
    private final boolean[] keys = new boolean[NUM_KEYS];
    private final boolean[] keysLast = new boolean[NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private final boolean[] buttons = new boolean[NUM_BUTTONS];
    private final boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private int mouseX, mouseY;
    private int mouseScroll;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
