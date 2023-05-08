package com.AIE.WindowPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ImageEdit.CropImage;
import com.AIE.WindowPackage.ImageEdit.ResizeImage;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ImageMenu extends JMenu {

    private static MainFrame frame;

    public ImageMenu(MainFrame frame) {
        super("Edit");
        ImageMenu.frame = frame;
        addMenuItem("Resize Image", "IResize", e -> new ResizeImage(frame, CanvasManager.getCurrentCanvas()));
        addMenuItem("Crop Image", "ICrop", e -> new CropImage(frame, CanvasManager.getCurrentCanvas()));
    }

    public static KeyAdapter getKeyAdapter(Canvas canvas) {
        return new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if(CanvasManager.GET.isCanvasNotSelected(canvas)) return;
                int key = e.getKeyCode();
                if(e.isControlDown() && key == KeyEvent.VK_R) {
                    new ResizeImage(frame, canvas);
                    return;
                }
                if(e.isControlDown() && key == KeyEvent.VK_O)
                    new CropImage(frame, canvas);
            }
        };
    }

    private void addMenuItem(String name, String icon, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        item.setIcon(ImageLoader.loadIcon(icon, ImageLoader.MENU_ICON_SIZE));
        add(item);
    }
}
