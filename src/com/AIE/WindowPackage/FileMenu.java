package com.AIE.WindowPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ImageEdit.NewImage;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileMenu extends JMenu {

    public FileMenu(MainFrame frame) {
        super("File");
        addMenuItem("New Image", "New", e -> {
            Canvas canvas = CanvasManager.getCurrentCanvas();
            if(canvas.isReplaceable()) {
                new NewImage(frame, canvas);
                return;
            }
            new NewImage(frame, null);
        });
        addMenuItem("Load Image", "Load", e -> {
            Canvas canvas = CanvasManager.getCurrentCanvas();
            File imgFile = ImageLoader.load();

            if(canvas == null || imgFile == null)
                return;

            try {
                if(canvas.isReplaceable()) {
                    canvas.create(imgFile.getName(), ImageIO.read(imgFile), 0, 0);
                    return;
                }
                new Canvas(imgFile.getName(), ImageIO.read(imgFile));
            } catch (IOException ex) {
                InfoPanel.GET.setErrorInfo("Could not load Image:" + imgFile.getName());
            }
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
