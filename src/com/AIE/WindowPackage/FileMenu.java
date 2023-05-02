package com.AIE.WindowPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.ImageLoader;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class FileMenu extends JMenu {

    public FileMenu() {
        super("File");
        addMenuItem("Load Image", "load", e -> {
            Canvas canvas = CanvasManager.getCurrentCanvas();
            BufferedImage image = ImageLoader.load();

            if(canvas == null || image == null)
                return;

            canvas.setNewImage(image);
        });
        addMenuItem("Save Image", "save", e -> {
            Canvas canvas = CanvasManager.getCurrentCanvas();
            if(canvas == null)
                return;
            BufferedImage image = canvas.getImage();
            if(image == null)
                return;
            ImageLoader.save(image);
        });
    }

    private void addMenuItem(String name, String icon, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        item.setIcon(ImageLoader.loadIcon(icon, ImageLoader.MENU_ICON_SIZE));
        add(item);
    }
}
