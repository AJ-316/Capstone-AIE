package com.AIE.EffectsPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ValueUpdateListener;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class AverageBlur extends Effect {

    private final JSlider radiusSlider;

    public AverageBlur(MainFrame frame) {
        super("Average Blur", frame, 300, 155);

        radiusSlider = new JSlider(1, 20);
        JTextField radiusField = new JTextField(3);
        radiusField.addActionListener(effectListener);
        ValueUpdateListener.set(radiusField, radiusSlider);

        radiusSlider.setPaintTicks(true);
        radiusSlider.setMajorTickSpacing(10);
        radiusSlider.setMinorTickSpacing(2);
        radiusSlider.addMouseListener(effectListener);
        finalizeComponents(new HeadLabel("Radius", (int) (300/2.5f)), radiusSlider, radiusField);
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = source.getRGB(0, 0, width, height, null, 0, width);
        int[] blurredPixels = new int[width * height];
        int progress = 0, totalSize = width*height;
        int radius = radiusSlider.getValue();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int blurRed = 0;
                int blurGreen = 0;
                int blurBlue = 0;
                int blurAlpha = 0;
                int blurCount = 0;

                for (int k = i - radius; k <= i + radius; k++) {
                    for (int l = j - radius; l <= j + radius; l++) {
                        if(checkForceStop()) return null;

                        if (k >= 0 && k < height && l >= 0 && l < width) {
                            int pixel = pixels[k * width + l];
                            blurAlpha += (pixel >> 24) & 0xFF;
                            blurRed += (pixel >> 16) & 0xFF;
                            blurGreen += (pixel >> 8) & 0xFF;
                            blurBlue += pixel & 0xFF;
                            blurCount++;
                        }
                    }
                }

                if (blurCount > 0) {
                    blurAlpha /= blurCount;
                    blurRed /= blurCount;
                    blurGreen /= blurCount;
                    blurBlue /= blurCount;
                }

                blurredPixels[i * width + j] = (blurAlpha << 24) | (blurRed << 16) | (blurGreen << 8) | blurBlue;

                progressEffect(progress++, totalSize);
            }
        }

        BufferedImage blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        blurredImage.setRGB(0, 0, width, height, blurredPixels, 0, width);
        return blurredImage;
    }

    /*@Override
    protected BufferedImage applyEffect(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, source.getType());

        // define the blur radius (the size of the square kernel)
        int radius = radiusSlider.getValue();
        int totalPixels = width * height;
        float progressValue = 0;

        // iterate over each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(forceStop) {
                    setProgress(0);
                    return null;
                }
                // compute the average color of the pixels within the kernel
                int sumR = 0, sumG = 0, sumB = 0, sumA = 0, count = 0;
                for (int ky = -radius; ky <= radius; ky++) {
                    int ny = y + ky;
                    if (ny >= 0 && ny < height) {
                        for (int kx = -radius; kx <= radius; kx++) {
                            int nx = x + kx;
                            if (nx >= 0 && nx < width) {
                                int color = source.getRGB(nx, ny);
                                sumR += (color >> 16) & 0xFF;
                                sumG += (color >> 8) & 0xFF;
                                sumB += color & 0xFF;
                                sumA += (color >> 24) & 0xFF;
                                count++;
                            }
                        }
                    }
                }
                int avgR = (int) ((float)sumR / count);
                int avgG = (int) ((float)sumG / count);
                int avgB = (int) ((float)sumB / count);
                int avgA = (int) ((float)sumA / count);

                // set the color of the current pixel in the result image
                int pixel = (avgA << 24) | (avgR << 16) | (avgG << 8) | avgB;
                resultImage.setRGB(x, y, pixel);
                progressValue++;
                setProgress((int) (100.0 * progressValue/totalPixels));
            }
        }
        return resultImage;
    }*/
}
