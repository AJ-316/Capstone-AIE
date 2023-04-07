package com.AIE.EffectsPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ValueUpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GaussianBlur extends Effect {

    private final JSlider radiusSlider;
    private float[] kernel;

    public GaussianBlur(MainFrame frame) {
        super("Gaussian Blur", frame, 300, 155);

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

    private int applyKernel(BufferedImage source, BufferedImage dest, int radius, boolean isHorizontalPass, int progressVal, int totalSize) {
        int length = isHorizontalPass ? source.getHeight() : source.getWidth();
        int lengthToCheck = isHorizontalPass ? source.getWidth() : source.getHeight();

        for (int axis = 0; axis < length; axis++) {
            for (int axisToCheck = 0; axisToCheck < lengthToCheck; axisToCheck++) {
                float sumR = 0.0f;
                float sumG = 0.0f;
                float sumB = 0.0f;
                float sumA = 0.0f;

                for (int i = -radius; i <= radius; i++) {
                    if(checkForceStop())
                        return 0;
                    int index = axisToCheck + i;
                    if (index < 0 || index >= lengthToCheck)
                        continue;

                    Color color = new Color(isHorizontalPass ? source.getRGB(index, axis) : source.getRGB(axis, index));
                    float kernelValue = kernel[i + radius];
                    sumR += color.getRed() * kernelValue;
                    sumG += color.getGreen() * kernelValue;
                    sumB += color.getBlue() * kernelValue;
                    sumA += color.getAlpha() * kernelValue;
                }

                Color blurredColor = new Color((int) sumR, (int) sumG, (int) sumB, (int) sumA);
                if(isHorizontalPass)
                    dest.setRGB(axisToCheck, axis, blurredColor.getRGB());
                else dest.setRGB(axis, axisToCheck, blurredColor.getRGB());
                progressEffect(progressVal++/2, totalSize);
            }
        }
        return progressVal;
    }

    private void calculateGaussianKernel(int radius) {
        // Calculate the size of the kernel based on the given radius
        int size = radius * 2 + 1;
        if(kernel != null && kernel.length == size)
            return;

        // Create the array to store the kernel values
        kernel = new float[size];

        // Calculate the normalization factor for the Gaussian function
        float sigma = radius / 3.0f;
        float sum = 0.0f;
        for (int i = 0; i < size; i++) {
            float x = i - radius;
            kernel[i] = (float) Math.exp(-(x * x) / (2 * sigma * sigma));
            sum += kernel[i];
        }

        // Normalize the kernel values
        for (int i = 0; i < size; i++) {
            kernel[i] /= sum;
        }
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        int radius = Math.max(1, radiusSlider.getValue());
        // Generate the Gaussian kernel
        calculateGaussianKernel(radius);
        BufferedImage blurredImage = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        int progressVal = 0;
        int totalSize = source.getWidth()*source.getHeight();
        // Apply horizontal blur to the input image
        progressVal = applyKernel(source, blurredImage, radius, true, progressVal, totalSize);

        // Apply vertical blur to the result of the horizontal blur
        progressVal = applyKernel(blurredImage, blurredImage, radius, false, progressVal, totalSize);
        if(progressVal == 0) return null;

        return blurredImage;
    }

}
