package com.AIE.WindowPackage;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractWindow extends JDialog {

    private final String iconFile;
    public AbstractWindow(String icon, MainFrame mainFrame, int width, int height, int offsetLocX, int offsetLocY) {
        super(mainFrame);
        setLayout(new FlowLayout());
        setLocation((int) (mainFrame.getLocation().getX() + offsetLocX),
                (int) (mainFrame.getLocation().getY() + offsetLocY));
        setSize(width, height);
        setResizable(false);
        setFocusable(false);
        setFocusableWindowState(false);
        iconFile = icon;
    }

    public String getIconFile() {
        return iconFile;
    }
}
