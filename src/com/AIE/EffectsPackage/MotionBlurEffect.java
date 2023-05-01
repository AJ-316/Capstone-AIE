package com.AIE.EffectsPackage;

import com.AIE.SeparatorLabel;
import com.AIE.WindowPackage.MainFrame;

import java.awt.image.BufferedImage;
import javax.swing.*;

public class MotionBlurEffect extends Effect {

    private final JSlider angleJSlider;
    private final JSlider radiusJSlider;

    public MotionBlurEffect(MainFrame frame) {
        super("MotionBlur", frame, 300, 210);
        JTextField aTxtField = new JTextField();
        JTextField rTxtField = new JTextField();
        angleJSlider = getSlider(aTxtField, 20, 0, 360, 90);
        radiusJSlider = getSlider(rTxtField, 10, 0, 50, 10);
        finalizeComponents(angleJSlider, aTxtField, new SeparatorLabel(250, 3, 0, 0), radiusJSlider, rTxtField);
    }


    @Override
    protected BufferedImage applyEffect(BufferedImage inputImage) {
        int angle = angleJSlider.getValue();
        int radius = radiusJSlider.getValue();
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int progressVal = 0;
        int totalSize = width * height;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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
                    if(checkForceStop()) return null;
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
                result.setRGB(x, y, rgb);
                progressEffect(progressVal++, totalSize);
            }
        }

        return result;
    }
}

