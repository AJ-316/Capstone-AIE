package com.AIE.WindowPackage;

import javax.swing.*;

public class FrameMenuBar extends JMenuBar {

    public FrameMenuBar(MainFrame frame) {
        frame.setJMenuBar(this);
        setBounds(0, 0, frame.getWidth(), 50);

        FileMenu fileMenu = new FileMenu();
        add(fileMenu);

        WindowMenu windowMenu = new WindowMenu(frame);
        add(windowMenu);

        EffectMenu effectMenu = new EffectMenu(frame);
        add(effectMenu);
    }

}
