package com.AIE.WindowPackage.ToolPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SmoothIcon extends ImageIcon {

    private int rectSize;
    private Color color;

    public SmoothIcon(BufferedImage image, int size) {
        setImage(image.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    public SmoothIcon(int size, Color color) {
        this.rectSize = size;
        this.color = color;
    }

    public SmoothIcon() {}

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        if(getImage() == null) {
            g2d.setColor(color);
            int arc = 5;
            g2d.fillRoundRect(x+rectSize/4, y-rectSize/2, rectSize, rectSize, arc, arc);
            g2d.setColor(Color.white);
            g2d.drawRoundRect(x+rectSize/4, y-rectSize/2, rectSize, rectSize, arc, arc);
            g2d.setColor(Color.black);
            g2d.drawRoundRect(x+rectSize/4-1, y-rectSize/2-1, rectSize+2, rectSize+2, arc, arc);
            return;
        }
        g2d.drawImage(getImage(), x, y, c);
    }

    public SmoothIcon updateImage(BufferedImage image, int size) {
        double ratio = (double) image.getWidth() / (double) image.getHeight();

        if(image.getWidth() < image.getHeight()) {
            setImage(image.getScaledInstance((int) (size * ratio),
                    size, Image.SCALE_SMOOTH));
        } else
            setImage(image.getScaledInstance(size,
                (int) (size / ratio), Image.SCALE_SMOOTH));
        return this;
    }
}
