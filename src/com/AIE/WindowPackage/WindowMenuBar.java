package com.AIE.WindowPackage;

import javax.swing.*;

public class WindowMenuBar extends JMenuBar {

    public WindowMenuBar(MainFrame frame) {
        frame.setJMenuBar(this);
        setBounds(0, 0, frame.getWidth(), 50);

        WindowsMenu windowsMenu = new WindowsMenu(frame);
        add(windowsMenu);
    }

}
