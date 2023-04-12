package com.AIE.EffectsPackage;

import com.AIE.SeparatorLabel;
import com.AIE.WindowPackage.MainFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NegativeFilter extends Effect{
    public NegativeFilter(MainFrame frame){
        super("Negative Filter", frame, 300, 200);
        finalizeComponents();
    }
    @Override
    protected BufferedImage applyEffect(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = inputImage.getRGB(x, y);
                Color color = new Color(pixel, true);
                color = new Color(255 - color.getRed(),
                        255 - color.getGreen(),
                        255 - color.getBlue());
                outputImage.setRGB(x, y, color.getRGB());
            }
        }
        return outputImage;
    }
}
