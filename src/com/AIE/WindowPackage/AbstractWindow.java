package com.AIE.WindowPackage;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractWindow extends JDialog {

    public AbstractWindow(String name, MainFrame mainFrame, int width, int height, int offsetLocX, int offsetLocY) {
        super(mainFrame);
        setName(name);
        setTitle(name);
        setLayout(new FlowLayout());
        setLocation((int) (mainFrame.getLocation().getX() + offsetLocX),
                (int) (mainFrame.getLocation().getY() + offsetLocY));
        setSize(width, height);
        setResizable(false);
        setFocusable(false);
        setFocusableWindowState(false);
    }
}
