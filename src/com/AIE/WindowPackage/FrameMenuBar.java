package com.AIE.WindowPackage;

import javax.swing.*;

public class FrameMenuBar extends JMenuBar {

    public FrameMenuBar(MainFrame frame) {
        frame.setJMenuBar(this);
        setBounds(0, 0, frame.getWidth(), 50);

        FileMenu fileMenu = new FileMenu();
        add(fileMenu);

        WindowsMenu windowsMenu = new WindowsMenu(frame);
        add(windowsMenu);
    }

}
