package com.AIE.WindowPackage;

import com.AIE.CanvasManager;
import com.AIE.Main;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainFrame extends JFrame implements ComponentListener {

    // TODO: Will need to remove instance and clean up the code so singletons are not needed
    public static CanvasManager CANVAS_MANAGER;
    public static Container PANE;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public MainFrame(int width, int height) {
        setLookAndFeel();

        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

        CANVAS_MANAGER = new CanvasManager();
        PANE = getContentPane();
        PANE.setBackground(new Color(0x303031));
        new WindowMenuBar(this);

        addComponentListener(this);
        this.setSize(width, height);
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(CANVAS_MANAGER, BorderLayout.CENTER);
    }
    private void setLookAndFeel() {
        FlatDarculaLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    public void createWindow() {
        //Finalizing JFrame
        this.setVisible(true);
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

    public static ImageIcon loadImage(String file, float scale) {
        BufferedImage img = loadImage(file);
        return new ImageIcon(img.getScaledInstance(
                (int)(img.getWidth()*scale),
                (int)(img.getHeight()*scale),
                BufferedImage.SCALE_SMOOTH));
    }

    @Override
    public void componentResized(ComponentEvent e) {
        CanvasManager.getCurrentCanvas().setSize(getWidth(), getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
