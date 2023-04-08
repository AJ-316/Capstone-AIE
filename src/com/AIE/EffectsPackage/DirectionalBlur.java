package com.AIE.EffectsPackage;

import com.AIE.SeparatorLabel;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ValueUpdateListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DirectionalBlur extends Effect{

    private final JSlider angleJSlider;
    private final JSlider radiusJSlider;

    public DirectionalBlur(MainFrame frame) {
        super("Directional Blur", frame, 300, 200);

        JTextField aTxtField = new JTextField();
        JTextField rTxtField = new JTextField();
        angleJSlider = getSlider(aTxtField, 90);
        radiusJSlider = getSlider(rTxtField, 10);
        finalizeComponents(angleJSlider, aTxtField, new SeparatorLabel(250, 3, 0, 0), radiusJSlider, rTxtField);
    }
    private JSlider getSlider(JTextField textField, int defaultVal) {
        JSlider slider = new JSlider(1, 360, defaultVal);

        textField.addActionListener(effectListener);
        textField.setText(String.valueOf(defaultVal));

        ValueUpdateListener.set(textField, slider);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.addMouseListener(effectListener);
        return slider;
    }
    @Override
    protected BufferedImage applyEffect(BufferedImage inputImage) {
        int angle = (int) angleJSlider.getValue();
        int radius = (int) radiusJSlider.getValue();
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // calculate blur vector
        double radianAngle = Math.toRadians(angle);
        double dx = Math.cos(radianAngle);
        double dy = Math.sin(radianAngle);

        // apply blur to each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = 0, g = 0, b = 0, count = 0;

                // sample pixels along blur vector
                for (int i = -radius; i <= radius; i++) {
                    int sampleX = (int) (x + i * dx);
                    int sampleY = (int) (y + i * dy);
                    if (sampleX >= 0 && sampleX < width && sampleY >= 0 && sampleY < height) {
                        int rgb = inputImage.getRGB(sampleX, sampleY);
                        r += (rgb >> 16) & 0xFF;
                        g += (rgb >> 8) & 0xFF;
                        b += rgb & 0xFF;
                        count++;
                    }
                }

                // set output pixel color to average of sampled colors
                if (count > 0) {
                    r /= count;
                    g /= count;
                    b /= count;
                }
                int rgb = (r << 16) | (g << 8) | b;
                outputImage.setRGB(x, y, rgb);
            }
        }

        return outputImage;
    }
}

