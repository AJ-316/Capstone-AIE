package com.AIE.EffectsPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HSVEffect extends Effect {

    private final MutableColor color;
    private final JSlider hueSlider;
    private final JSlider satSlider;
    private final JSlider valSlider;

    public HSVEffect(MainFrame frame) {
        super("HSV", frame, 300, 300);

        color = new MutableColor(0);

        JTextField hueField = new JTextField(3);
        JTextField satField = new JTextField(3);
        JTextField valField = new JTextField(3);
        hueSlider = getSlider(hueField, 40, -180, 180, 0);
        satSlider = getSlider(satField, 40, 0, 200, 100);
        valSlider = getSlider(valField, 40, -100, 100, 0);
        finalizeComponents(
                new HeadLabel("Hue", (int) (300/2.5f)), hueSlider, hueField,
                new HeadLabel("Saturation", (int) (300/2.5f)), satSlider, satField,
                new HeadLabel("Value", (int) (300/2.5f)), valSlider, valField);
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int progressVal = 0;
        int totalPixels = width * height;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        float[] hsv = new float[3];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(checkForceStop())
                    return null;

                int pixel = source.getRGB(x,y);

                color.setRGBA(pixel);
                Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
                hsv[0] = (hsv[0] + normalize(hueSlider.getValue(), 0, 360)) % 1.0f;
                hsv[1] = Math.min(hsv[1] * normalize(satSlider.getValue(), 0, 100), 1.0f); // Scale the saturation by the specified amount
                hsv[2] = Math.min(hsv[2] * normalize(valSlider.getValue() + 100, 0, 100), 1.0f);
                int rgbResult = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);

                result.setRGB(x, y, rgbResult);

                progressEffect(progressVal++, totalPixels);
            }
        }
        return result;
    }
}
