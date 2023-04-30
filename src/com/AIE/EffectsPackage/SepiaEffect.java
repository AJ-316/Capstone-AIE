package com.AIE.EffectsPackage;

import com.AIE.SeparatorLabel;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class SepiaEffect extends Effect {

    private final JSlider depth;
    private final JSlider intensity;
    private final MutableColor color;

    public SepiaEffect(MainFrame frame) {
        super("Sepia", frame, 300, 200);

        color = new MutableColor(0);

        JTextField dTxtField = new JTextField();
        JTextField iTxtField = new JTextField();
        depth = getSlider(dTxtField, 20, 0, 100, 20);
        intensity = getSlider(iTxtField, 20, 0, 100, 30);
        finalizeComponents(depth, dTxtField, new SeparatorLabel(250, 3, 0, 0), intensity, iTxtField);
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int progressVal = 0;
        int totalPixels = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(checkForceStop())
                    return null;

                int pixel = source.getRGB(x,y);

                color.setRGBA(pixel);
                int avg = (color.getRed()+color.getGreen()+color.getBlue())/3;

                color.setRGB(
                        Math.min(255, avg + depth.getValue() * 2),
                        Math.min(255, avg + depth.getValue()),
                        Math.max(0, Math.min(255, avg - intensity.getValue())));
                sepiaImage.setRGB(x, y, color.getRGB());

                progressEffect(progressVal++, totalPixels);
            }
        }
        return sepiaImage;
    }
}
