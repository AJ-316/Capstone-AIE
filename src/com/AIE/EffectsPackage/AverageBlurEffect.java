package com.AIE.EffectsPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ValueUpdateListener;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class AverageBlurEffect extends Effect {

    private final JSlider radiusSlider;
    private int progressVal;

    public AverageBlurEffect(MainFrame frame) {
        super("Average Blur", frame, 300, 155);

        radiusSlider = new JSlider(0, 50);
        JTextField radiusField = new JTextField(3);
        radiusField.addActionListener(effectListener);
        ValueUpdateListener.set(radiusField, radiusSlider);

        radiusSlider.setPaintTicks(true);
        radiusSlider.setMajorTickSpacing(radiusSlider.getMaximum()/2);
        radiusSlider.setMinorTickSpacing(radiusSlider.getMaximum()/10);
        radiusSlider.addMouseListener(effectListener);
        finalizeComponents(new HeadLabel("Radius", (int) (300/2.5f)), radiusSlider, radiusField);
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        progressVal = 0;
        int width = source.getWidth();
        int height = source.getHeight();

        int[] blurredPixels = applyHorizontalPass(source.getRGB(0, 0, width, height, null, 0, width),
                width, height, radiusSlider.getValue()); // Apply horizontal pass

        blurredPixels = applyVerticalPass(blurredPixels,
                width, height, radiusSlider.getValue()); // Apply vertical pass
        if(blurredPixels == null)
            return null;

        BufferedImage blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        blurredImage.setRGB(0, 0, width, height, blurredPixels, 0, width);
        return blurredImage;
    }

    private int[] applyHorizontalPass(int[] source, int width, int height, int radius) {
        if(source == null) return null;

        int[] dest = new int[source.length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int blurRed = 0;
                int blurGreen = 0;
                int blurBlue = 0;
                int blurAlpha = 0;
                int blurCount = 0;

                for (int k = j - radius; k <= j + radius; k++) {
                    if(checkForceStop())
                        return null;
                    if (k >= 0 && k < width) {
                        int pixel = source[i * width + k];
                        blurAlpha += (pixel >> 24) & 0xFF;
                        blurRed += (pixel >> 16) & 0xFF;
                        blurGreen += (pixel >> 8) & 0xFF;
                        blurBlue += pixel & 0xFF;
                        blurCount++;
                    }
                }

                if (blurCount > 0) {
                    blurAlpha /= blurCount;
                    blurRed /= blurCount;
                    blurGreen /= blurCount;
                    blurBlue /= blurCount;
                }

                dest[i * width + j] = (blurAlpha << 24) | (blurRed << 16) | (blurGreen << 8) | blurBlue;
                progressEffect(progressVal++/2, source.length);
            }
        }
        return dest;
    }

    private int[] applyVerticalPass(int[] source, int width, int height, int radius) {
        if(source == null) return null;

        int[] dest = new int[source.length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int blurRed = 0;
                int blurGreen = 0;
                int blurBlue = 0;
                int blurAlpha = 0;
                int blurCount = 0;

                for (int k = i - radius; k <= i + radius; k++) {
                    if(checkForceStop())
                        return null;
                    if (k >= 0 && k < height) {
                        int pixel = source[k * width + j];
                        blurAlpha += (pixel >> 24) & 0xFF;
                        blurRed += (pixel >> 16) & 0xFF;
                        blurGreen += (pixel >> 8) & 0xFF;
                        blurBlue += pixel & 0xFF;
                        blurCount++;
                    }
                }

                if (blurCount > 0) {
                    blurAlpha /= blurCount;
                    blurRed /= blurCount;
                    blurGreen /= blurCount;
                    blurBlue /= blurCount;
                }

                dest[i * width + j] = (blurAlpha << 24) | (blurRed << 16) | (blurGreen << 8) | blurBlue;
                progressEffect(progressVal++/2, source.length);
            }
        }
        return dest;
    }


}
