package com.AIE.EffectsPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.HeadLabel;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class GaussianBlur extends Effect {

    private final JSlider radiusSlider;
    private final JTextField field;

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

        radiusSlider = new JSlider(0, 40);
        field = new JTextField(3);

        MouseAdapter sliderListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                field.setText(String.valueOf(radiusSlider.getValue()));
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                this.mousePressed(e);
            }
        };
        ChangeListener changeListener = e ->
            CanvasManager.getCurrentCanvas().setPreviewImage(cal(CanvasManager.getCurrentCanvas()));
        DocumentListener fieldListener = new DocumentListener() {
            private void setSliderValue() {
                if(field.getText().matches("\\d+")) {
                    int value = Integer.parseInt(field.getText());
                    radiusSlider.setValue(Math.max(0, Math.min(radiusSlider.getMaximum(), value)));
                }
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                setSliderValue();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                setSliderValue();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
        radiusSlider.setMajorTickSpacing(10);
        radiusSlider.setMinorTickSpacing(2);
        radiusSlider.setPaintTicks(true);
        radiusSlider.setPaintLabels(true);
        radiusSlider.addMouseListener(sliderListener);
        radiusSlider.addMouseMotionListener(sliderListener);
        radiusSlider.addChangeListener(changeListener);
        field.getDocument().addDocumentListener(fieldListener);

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
