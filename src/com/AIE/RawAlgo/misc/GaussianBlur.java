package com.AIE.RawAlgo.misc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

public class GaussianBlur {
    public BufferedImage blurEffect(File inputFile, int radius) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        int size = radius * 2 + 1; // size of Gaussian kernel
        float[] data = new float[size * size];
        float sigma = radius / 3.0f; // standard deviation
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int index = (i + radius) * size + (j + radius);
                float distance = i * i + j * j;
                data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
                total += data[index];
            }
        }
        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }
        Kernel kernel = new Kernel(size, size, data);

        // Apply Gaussian blur filter to image
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }
    public static void main(String[] args) throws IOException {
        GaussianBlur gb = new GaussianBlur();
        File input = new File("C:\\Users\\Mizab\\Desktop\\GPT\\Capstone\\image.png");

        BufferedImage blurredImage = gb.blurEffect(input, 10);

        File output = new File("blurred-image.png");
        ImageIO.write(blurredImage, "png", output);

    }
}
