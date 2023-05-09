package com.AIE;

import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ToolPackage.SmoothIcon;
import com.AIE.CanvasPackage.Canvas;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ImageLoader {

    private static JFileChooser chooser;
    private static MainFrame frame;
    public static int MENU_ICON_SIZE = 16;

    private static final String NO_IMAGE = "empty";
    private static final HashMap<String, BufferedImage> LOADED_IMAGES = new HashMap<>();

    private static final HashMap<Canvas, String[]> currentlySaved = new HashMap<>();

    public static void init(MainFrame frame) {
        ImageLoader.frame = frame;

        chooser = new JFileChooser();
        addExtensionFilters(
                ".png", "PNG (*.png)",
                ".jpg,.jpeg", "JPG (*.jpg; *jpeg)",
                ".bmp", "BMP (*.bmp)",
                ".gif", "GIF (*.gif)");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        loadImage(NO_IMAGE);
    }

    private static void addExtensionFilters(String... extensions) {
        for(int i = 0; i < extensions.length; i+=2) {
            chooser.addChoosableFileFilter(new ExtensionFileFilter(extensions[i], extensions[i+1]));
        }
    }

    public static File load() {
        chooser.setDialogTitle("Load Image");
        int state = chooser.showOpenDialog(frame);
        if(state != JFileChooser.APPROVE_OPTION)
            return null;
        return chooser.getSelectedFile();
    }

    public static void save(Canvas canvas) {
        if(currentlySaved.get(canvas) != null) {
            try {
                saveImage(canvas.getImage(), currentlySaved.get(canvas)[0], currentlySaved.get(canvas)[1]);
                canvas.setLastSaved(true);
            } catch (IOException e) {
                AppLog.error("ImageLoader", "Could not save Image (" +
                        currentlySaved.get(canvas)[0]+currentlySaved.get(canvas)[1] + ")", e);
                throw new RuntimeException(e);
            }
            return;
        }
        chooser.setDialogTitle("Save Image");
        int state = chooser.showSaveDialog(frame);

        if(state == JFileChooser.APPROVE_OPTION) {
            String ext = chooser.getFileFilter().toString();
            String path = chooser.getSelectedFile().getPath();
            if(!path.endsWith(ext))
                path += ext;
            ext = ext.substring(1);

            // Will add more cases later
            try {
                switch (ext) {
                    case "png", "gif" -> {
                        saveImage(canvas.getImage(), path, ext);
                        canvas.setLastSaved(true);
                    }
                    case "jpg", "bmp" -> {
                        BufferedImage image = createImage(canvas.getImage(), BufferedImage.TYPE_INT_RGB);
                        saveImage(image, path, ext);
                        canvas.setLastSaved(true);
                    }
                    default -> System.err.println("File did not save: " + path);
                }
                currentlySaved.put(canvas, new String[]{path, ext});
            } catch (IOException e) {
                AppLog.error("ImageLoader", "Could not save Image (" +
                        currentlySaved.get(canvas)[0]+currentlySaved.get(canvas)[1] + ")", e);
                throw new RuntimeException(e);
            }
        }
    }

    private static void saveImage(BufferedImage image, String path, String ext) throws IOException {
        ImageIO.write(image, ext, new File(path));
        System.out.println("File Saved: " + path);
    }

    public static BufferedImage createImage(BufferedImage newImage, int type) {
        BufferedImage image = new BufferedImage(
                newImage.getWidth(), newImage.getHeight(), type);
        Graphics2D g = image.createGraphics();
        g.drawImage(newImage, 0, 0, null);
        g.dispose();
        return image;
    }

    public static BufferedImage createImage(BufferedImage newImage, int width, int height, int type) {
        BufferedImage image = new BufferedImage(width, height, type);
        Graphics2D g = image.createGraphics();
        g.drawImage(newImage, 0, 0, null);
        g.dispose();
        return image;
    }

    private static class ExtensionFileFilter extends FileFilter {
        private final String ext;
        private final String[] extensions;
        private final String desc;

        public ExtensionFileFilter(String ext, String desc) {
            this.extensions = ext.split(",");
            this.ext = extensions[0];
            this.desc = desc;
        }

        @Override
        public boolean accept(File f) {
            if(f.isDirectory())
                return true;

            for(String ext : extensions) {
                if(f.getName().toLowerCase().endsWith(ext))
                    return true;
            }

            return false;
        }

        @Override
        public String getDescription() {
            return desc;
        }

        @Override
        public String toString() {
            return ext;
        }
    }

    public static BufferedImage loadImage(String file) {
        try {
            BufferedImage image = LOADED_IMAGES.get(file);
            if(image == null) {
                URL url = Main.class.getResource("resources/" + file + ".png");
                if(url == null)
                    return LOADED_IMAGES.get(NO_IMAGE);

                image = ImageIO.read(url);
                LOADED_IMAGES.put(file, image);
            }
            return image;
        } catch (IOException e) {
            AppLog.error("ImageLoader", "Could not Load Resource Image (" + file + ")", e);
            throw new RuntimeException(e);
        }
    }

    public static SmoothIcon loadIcon(String file, int size) {
        return new SmoothIcon(loadImage("icons/" + file), size);
    }

}
