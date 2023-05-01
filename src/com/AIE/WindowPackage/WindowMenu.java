package com.AIE.WindowPackage;

import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ToolPackage.Toolbar;

import javax.swing.*;

public class WindowMenu extends JMenu {

    public WindowMenu(MainFrame mainFrame) {
        super("Windows");
        addMenuItem(new Toolbar(mainFrame));
        addMenuItem(new ColorPalette(mainFrame));
        addMenuItem(new History(mainFrame));
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
