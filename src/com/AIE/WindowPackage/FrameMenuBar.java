package com.AIE.WindowPackage;

import javax.swing.*;

public class FrameMenuBar extends JMenuBar {

    public FrameMenuBar(MainFrame frame) {
        frame.setJMenuBar(this);
        setBounds(0, 0, frame.getWidth(), 50);

        FileMenu fileMenu = new FileMenu(frame);
        add(fileMenu);

        ImageMenu imageMenu = new ImageMenu(frame);
        add(imageMenu);

        WindowMenu windowMenu = new WindowMenu(frame);
        add(windowMenu);

        EffectMenu effectMenu = new EffectMenu(frame);
        add(effectMenu);
    }

}
