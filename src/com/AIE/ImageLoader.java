package com.AIE;

import com.AIE.WindowPackage.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {

    private static JFileChooser chooser;
    private static MainFrame frame;

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
    }

    private static void addExtensionFilters(String... extensions) {
        for(int i = 0; i < extensions.length; i+=2) {
            chooser.addChoosableFileFilter(new ExtensionFileFilter(extensions[i], extensions[i+1]));
        }
    }

    public static BufferedImage load() {
        chooser.setDialogTitle("Load Image");
        int state = chooser.showOpenDialog(frame);
        if(state != JFileChooser.APPROVE_OPTION)
            return null;

        try {
            return ImageIO.read(chooser.getSelectedFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(BufferedImage image) {
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
                    case "png", "gif" ->
                        saveImage(image, path, ext);
                    case "jpg", "bmp" -> {
                        image = createImage(image, BufferedImage.TYPE_INT_RGB);
                        saveImage(image, path, ext);
                    }
                    default -> System.err.println("File did not save: " + path);
                }
            } catch (IOException e) {
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
            return ImageIO.read(
                    Objects.requireNonNull(
                            Main.class.getResource("resources/" + file + ".png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ImageIcon loadIcon(String file, float scale) {
        BufferedImage img = loadImage(file);
        return new ImageIcon(img.getScaledInstance(
                (int)(img.getWidth()*scale),
                (int)(img.getHeight()*scale),
                BufferedImage.SCALE_SMOOTH));
    }

}
