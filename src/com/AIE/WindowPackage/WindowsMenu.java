package com.AIE.WindowPackage;

import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ToolPackage.Toolbar;

import javax.swing.*;
import java.util.HashMap;

public class WindowsMenu extends JMenu {

    private final HashMap<String, AbstractWindow> windows;

    public WindowsMenu(MainFrame mainFrame) {
        super("Windows");
        windows = new HashMap<>();

        Toolbar toolbar = new Toolbar(mainFrame);
        addWindow(Toolbar.NAME, toolbar);

        ColorPalette colorPalette = new ColorPalette(mainFrame);
        addWindow(ColorPalette.NAME, colorPalette);
    }

    private void addWindow(String name, AbstractWindow window) {
        windows.put(name, window);
        JMenuItem item = new JMenuItem(name);

        item.addActionListener(e -> window.setVisible(!window.isVisible()));

        add(item);
    }// TODO: Might need to change (never required to access windows using HashMap)
    public AbstractWindow getWindow(String name) {
        return windows.get(name);
    }
}
