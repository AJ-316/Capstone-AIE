package com.AIE.WindowPackage;

import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ColorPackage.ColorPaletteWindow;
import com.AIE.WindowPackage.ToolPackage.Toolbar;

import javax.swing.*;

public class WindowMenu extends JMenu {

    private static Toolbar toolbar;
    private static ColorPaletteWindow colorPaletteWindow;
    private static History history;

    public WindowMenu(MainFrame mainFrame) {
        super("Windows");
        toolbar = new Toolbar(mainFrame);
        colorPaletteWindow = new ColorPaletteWindow(mainFrame);
        history = new History(mainFrame);

        addMenuItem(toolbar);
        addMenuItem(colorPaletteWindow);
        addMenuItem(history);
    }

    public static void setRelativeLocation(MainFrame frame) {
        toolbar.setRelativeLocation(frame);
        colorPaletteWindow.setRelativeLocation(frame);
        history.setRelativeLocation(frame);
    }

    private void addMenuItem(AbstractWindow window) {
        JMenuItem item = new JMenuItem(window.getName());
        ImageIcon icon = ImageLoader.loadIcon(window.getName(), ImageLoader.MENU_ICON_SIZE);
        window.setIconImage(icon.getImage());

        item.setIcon(icon);
        item.addActionListener(e -> window.setVisible(!window.isVisible()));

        add(item);
    }
}
