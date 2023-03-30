package com.AIE.WindowPackage;

import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ToolPackage.Toolbar;

import javax.swing.*;
import java.util.HashMap;

public class WindowMenu extends JMenu {

    private final HashMap<String, AbstractWindow> windows;

    public WindowMenu(MainFrame mainFrame) {
        super("Windows");
        windows = new HashMap<>();

        Toolbar toolbar = new Toolbar(mainFrame);
        addMenuItem(Toolbar.NAME, toolbar);

        ColorPalette colorPalette = new ColorPalette(mainFrame);
        addMenuItem(ColorPalette.NAME, colorPalette);
    }

    private void addMenuItem(String name, AbstractWindow window) {
        windows.put(name, window);
        JMenuItem item = new JMenuItem(name);
        ImageIcon icon = ImageLoader.loadIcon(window.getIconFile(), ImageLoader.MENU_ICON_SIZE);
        window.setIconImage(icon.getImage());

        item.setIcon(icon);
        item.addActionListener(e -> window.setVisible(!window.isVisible()));

        add(item);
    }// TODO: Might need to change (never required to access windows using HashMap)
    public AbstractWindow getWindow(String name) {
        return windows.get(name);
    }
}
