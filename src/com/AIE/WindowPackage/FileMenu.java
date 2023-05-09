package com.AIE.WindowPackage;

import com.AIE.AppLog;
import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ImageEdit.ConfirmCanvasClose;
import com.AIE.WindowPackage.ImageEdit.NewImage;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class FileMenu extends JMenu {

    private static final ActionListener saveListener = e -> {
        Canvas canvas = CanvasManager.getCurrentCanvas();
        if(canvas == null)
            return;
        ImageLoader.save(canvas);
    };

    public FileMenu(MainFrame frame) {
        super("File");
        addMenuItem("New Image", "New", e -> {
            Canvas canvas = CanvasManager.getCurrentCanvas();
            if(canvas == null) {
                new NewImage(frame, null);
                return;
            }
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
                AppLog.error("FileMenu", "Could not load Image (" + imgFile.getName() + ")", ex);
                InfoPanel.GET.setErrorInfo("Could not load Image:" + imgFile.getName());
            }
        });
        addMenuItem("Save Image", "Save", saveListener);
        addMenuItem("Close Canvas", "Close", e -> {
            if(CanvasManager.GET.getTabCount() == 1) return;
            Canvas canvas = CanvasManager.getCurrentCanvas();
            if(canvas == null)
                return;
            new ConfirmCanvasClose(frame, canvas);
        });
    }

    public static KeyAdapter getKeyAdapter(Canvas canvas) {
        return new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if(CanvasManager.GET.isCanvasNotSelected(canvas)) return;
                int key = e.getKeyCode();
                if(e.isControlDown() && key == KeyEvent.VK_S)
                    saveListener.actionPerformed(null);
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
