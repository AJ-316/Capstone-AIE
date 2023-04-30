package com.AIE.EffectsPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class BrightnessContrastEffect extends Effect {

    private final MutableColor color;
    private final JSlider contrast;
    private final JSlider brightness;

    public BrightnessContrastEffect(MainFrame frame) {
        super("Brightness & Contrast", frame, 300, 285);
        color = new MutableColor(0);
        JTextField contrastField = new JTextField(3);
        JTextField brightnessField = new JTextField(3);
        contrast = getSlider(contrastField, 40, -100, 100, 0);
        brightness = getSlider(brightnessField, 40, -100, 100, 0);
        finalizeComponents(new HeadLabel("Contrast", (int) (300/2.5f)), contrast, contrastField,
                new HeadLabel("Brightness", (int) (300/2.5f)), brightness, brightnessField);
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int progressVal = 0;
        int totalPixels = width * height;
        BufferedImage result = new BufferedImage(width, height, source.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(checkForceStop())
                    return null;

                color.setRGBA(source.getRGB(x,y));

                float contrastFactor = normalize(contrast.getValue() + 100, 0, 100);
                float brightnessFactor = normalize(brightness.getValue() + 100, 0, 100);

                int red = (int) (color.getRed() * brightnessFactor + (1 - brightnessFactor) * 128);
                int green = (int) (color.getGreen() * brightnessFactor + (1 - brightnessFactor) * 128);
                int blue = (int) (color.getBlue() * brightnessFactor + (1 - brightnessFactor) * 128);
                red = (int) ((red - 128) * contrastFactor + 128);
                green = (int) ((green - 128) * contrastFactor + 128);
                blue = (int) ((blue - 128) * contrastFactor + 128);
                red = Math.min(Math.max(red, 0), 255); // Clamp values to the range 0-255
                green = Math.min(Math.max(green, 0), 255);
                blue = Math.min(Math.max(blue, 0), 255);
                color.setRGB(red, green, blue);
                result.setRGB(x, y, color.getRGB());
                progressEffect(progressVal++, totalPixels);
            }
        }
        return result;
    }
}
