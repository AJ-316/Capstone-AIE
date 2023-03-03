package com.AIE;

import com.AIE.WindowPackage.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
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
        chooser.setAcceptAllFileFilterUsed(false);
        ExtensionFileFilter extensionFileFilter = new ExtensionFileFilter(".png", "PNG Image");
        chooser.addChoosableFileFilter(extensionFileFilter);
        chooser.setFileFilter(extensionFileFilter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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

            // Will add more cases later
            try {
                switch (ext) {
                    case ".png" -> saveImageAsPNG(image, path);
                    default -> System.err.println("File did not save: " + path);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void saveImageAsPNG(BufferedImage image, String path) throws IOException {
        ImageIO.write(image, "PNG", new File(path));
        System.out.println("File Saved: " + path);
    }

    private static class ExtensionFileFilter extends FileFilter {
        private final String ext;
        private final String desc;

        public ExtensionFileFilter(String ext, String desc) {
            this.ext = ext;
            this.desc = desc;
        }

        @Override
        public boolean accept(File f) {
            if(f.isDirectory())
                return true;
            return f.getName().toLowerCase().endsWith(ext);
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
