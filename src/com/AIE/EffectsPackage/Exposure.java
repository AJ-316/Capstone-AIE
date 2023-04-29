package com.AIE.EffectsPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Exposure extends Effect {

    private final MutableColor color;
    private final JSlider expSlider;

    public Exposure(MainFrame frame) {
        super("Exposure", frame, 300, 160);

        color = new MutableColor(0);

        JTextField expField = new JTextField(3);
        expSlider = getSlider(expField, 40, -100, 200, 0);
        finalizeComponents(new HeadLabel("Exposure", (int) (300/2.5f)), expSlider, expField);
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int progressVal = 0;
        int totalPixels = width * height;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(checkForceStop())
                    return null;

                int pixel = source.getRGB(x,y);

                color.setRGBA(pixel);
                float exposure = expSlider.getValue()/100f + 1;
                int r = (int) Math.max(Math.min(color.getRed() * exposure, 255), 0);
                int g = (int) Math.max(Math.min(color.getGreen() * exposure, 255), 0);
                int b = (int) Math.max(Math.min(color.getBlue() * exposure, 255), 0);
                color.setRGB(r,g,b);
                result.setRGB(x, y, color.getRGB());

                progressEffect(progressVal++, totalPixels);
            }
        }
        return result;
    }
}
