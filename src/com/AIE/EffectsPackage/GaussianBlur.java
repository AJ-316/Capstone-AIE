/* Old Gaussian File
package com.AIE.EffectsPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.HeadLabel;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ValueUpdateListener;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class GaussianBlur extends EffectOld {

    private final JSlider radiusSlider;
    private final JTextField field;

    private BufferedImage cal2(Canvas canvas) {
        if(radiusSlider.getValue() == 0) return null;
        BufferedImage image = canvas.getImage();
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        // Apply the Gaussian blur effect to the image in smaller regions
        float sigma = radiusSlider.getValue()/10f;
        int radius = 2 * (int) Math.ceil(sigma) + 1;
        int numRegionsX = 1;
        int numRegionsY = 1;
        int regionWidth = newImage.getWidth() / numRegionsX;
        int regionHeight = newImage.getHeight() / numRegionsY;
        int numRegions = numRegionsX * numRegionsY;

        for (int y = 0; y < newImage.getHeight(); y += regionHeight) {
            for (int x = 0; x < newImage.getWidth(); x += regionWidth) {
                int regionWidthActual = Math.min(regionWidth, newImage.getWidth() - x);
                int regionHeightActual = Math.min(regionHeight, newImage.getHeight() - y);
                BufferedImage region = newImage.getSubimage(x, y, regionWidthActual, regionHeightActual);
                Kernel kernel = makeGaussianKernel(sigma, radius);
                ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
                BufferedImage blurredRegion = op.filter(region, null);
                newImage.setRGB(x, y, blurredRegion.getWidth(), blurredRegion.getHeight(), blurredRegion.getRGB(0, 0, blurredRegion.getWidth(), blurredRegion.getHeight(), null, 0, blurredRegion.getWidth()), 0, blurredRegion.getWidth());
                progress.progress(100 / numRegions);
            }
        }

        return newImage;
    }

    private static Kernel makeGaussianKernel(float sigma, int radius) {
        int size = radius * 2 + 1;
        float[] data = new float[size];
        float sum = 0.0f;

        for (int i = -radius; i <= radius; i++) {
            float x = i / sigma;
            float value = (float) Math.exp(-0.5 * x * x);
            data[i + radius] = value;
            sum += value;
        }

        for (int i = 0; i < size; i++) {
            data[i] /= sum;
        }

        return new Kernel(size, 1, data);
    }

    private BufferedImage cal(Canvas canvas) {
        if(radiusSlider.getValue() == 0) return null;
        progress.progress(10);
        Kernel kernel = createKernel(radiusSlider.getValue());
        progress.progress(70);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null); //EDGE_NO_OP
        progress.progress(100);
        return op.filter(canvas.getImage(), null);
    }

    public GaussianBlur(MainFrame frame) {
        super(frame, "Gaussian Blur", 320, 150);

        radiusSlider = new JSlider(0, 200);
        field = new JTextField(3);
        ValueUpdateListener.set(field, radiusSlider);

        ChangeListener changeListener = e ->
            CanvasManager.getCurrentCanvas().setPreviewImage(cal(CanvasManager.getCurrentCanvas()));
        radiusSlider.setMajorTickSpacing(50);
        radiusSlider.setMinorTickSpacing(5);
        radiusSlider.setPaintTicks(true);
        radiusSlider.setPaintLabels(true);
        radiusSlider.addChangeListener(changeListener);

        add(new HeadLabel("Radius", (int) (getWidth()/2.5f)));
        add(radiusSlider);
        add(field);
        setEffectProgress(frame, () -> {
            CanvasManager.getCurrentCanvas().confirmPreview();
//            if(radiusSlider.getValue() == 0) return;
//            Canvas canvas = CanvasManager.getCurrentCanvas();
//            if(canvas == null)
//                return;
//            progress.progress(10);
//            Kernel kernel = createKernel(radiusSlider.getValue());
//            progress.progress(50);
//            ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null); //EDGE_NO_OP
//            progress.progress(60);
//            BufferedImage blurredImage = op.filter(canvas.getImage(), null);
//            progress.progress(80);
//            CanvasManager.getCurrentCanvas().setImage(blurredImage);
//            progress.progress(100);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        progress.start();
    }

    private Kernel createKernel(int radius) {
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

        for (int i = 0; i < data.length; i++)
            data[i] /= total;

        return new Kernel(size, size, data);
    }

}
*/
