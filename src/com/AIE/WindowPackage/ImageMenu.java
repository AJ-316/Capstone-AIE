package com.AIE.WindowPackage;

import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ImageEdit.Crop;
import com.AIE.WindowPackage.ImageEdit.Resize;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ImageMenu extends JMenu {

    public ImageMenu(MainFrame frame) {
        super("Edit");
        addMenuItem("Resize Image", "resize", e -> new Resize(frame, CanvasManager.getCurrentCanvas()));
        addMenuItem("Crop Image", "crop", e -> new Crop(frame, CanvasManager.getCurrentCanvas()));
    }

    private void addMenuItem(String name, String icon, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        item.setIcon(ImageLoader.loadIcon(icon, ImageLoader.MENU_ICON_SIZE));
        add(item);
    }
}
