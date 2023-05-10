package com.AIE.WindowPackage;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractWindow extends JDialog {

    public AbstractWindow(String name, MainFrame mainFrame, int width, int height) {
        super(mainFrame);
        setName(name);
        setTitle(name);
        setLayout(new FlowLayout());
        setSize(width, height);
        setResizable(false);
        setFocusable(false);
        setFocusableWindowState(false);
    }
    public abstract void setRelativeLocation(MainFrame frame);
}