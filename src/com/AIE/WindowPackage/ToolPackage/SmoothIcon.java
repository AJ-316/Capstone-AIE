package com.AIE.WindowPackage.ToolPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SmoothIcon extends ImageIcon {

    public SmoothIcon(BufferedImage image, int size) {
        setImage(image.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(getImage(), x, y, c);
    }
}
