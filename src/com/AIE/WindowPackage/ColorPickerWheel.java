package com.AIE.WindowPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColorPickerWheel extends JPanel {

    private static final BasicStroke bs = new BasicStroke(2);
    private static final int IMAGE_SIZE = 1024;
    private static final int DRAW_SIZE = 175;
    private static final int PANEL_SIZE_OFFSET = 25;
    private final int[] row;

    private final Picker picker;
    private final BufferedImage image;

    public ColorPickerWheel() {
        super(null);
        setPreferredSize(new Dimension(DRAW_SIZE+PANEL_SIZE_OFFSET*2, DRAW_SIZE+PANEL_SIZE_OFFSET*2));
        setOpaque(false);

        picker = new Picker(10);
        image = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        row = new int[IMAGE_SIZE];

        generateColorWheel();
        updateColorPicker();

        try {
            applyGrayscaleMaskToAlpha(image, ImageIO.read(new File("mask.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PickerUpdate pickerUpdate = new PickerUpdate();
        addMouseListener(pickerUpdate);
        addMouseMotionListener(pickerUpdate);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.white);
        g.drawOval(PANEL_SIZE_OFFSET, PANEL_SIZE_OFFSET, DRAW_SIZE, DRAW_SIZE);

        g.drawImage(this.image, PANEL_SIZE_OFFSET, PANEL_SIZE_OFFSET, DRAW_SIZE, DRAW_SIZE, null);

        g.setColor(HSVSlider.getHSVToRGB(picker.color, HSVSlider.hue.getValue(), HSVSlider.saturation.getValue(), 100));

        g.fillOval(picker.x, picker.y, picker.size, picker.size);
        ((Graphics2D) g).setStroke(bs);

        g.setColor(Color.white);
        g.drawOval(picker.x, picker.y, picker.size, picker.size);
    }

    public void updateColorPicker() {
        double theta = HSVSlider.hue.getValue()/360f * 2.0f * 3.141592653589793;
        if (theta < 0.0) theta += 6.283185307179586;
        final double r = HSVSlider.saturation.getValue()/100f * DRAW_SIZE /2.0;
        picker.setPos((int)(r * Math.cos(theta) + 0.5 + DRAW_SIZE /2.0), (int)(r * Math.sin(theta) + 0.5 + DRAW_SIZE /2.0f));
        repaint();
    }

    private void generateColorWheel() {
        final double dTheta = 6.283185307179586;
        final float radius = row.length / 2.0f;
        for(int y = 0; y < row.length; ++y) {
            final float y2 = y - row.length / 2.0f;

            for(int x = 0; x < row.length; ++x) {
                final float x2 = x - row.length / 2.0f;

                final double r = Math.sqrt(x2 * x2 + y2 * y2);
                if (r > radius) {
                    row[x] = 0xffff0000;
                    continue;
                }

                row[x] = Color.HSBtoRGB(
                        (float) (Math.atan2(y2, x2) / dTheta),
                        (float) (r / radius), 1);
            }
            image.getRaster().setDataElements(0, y, row.length, 1, this.row);
        }
    }

    public void applyGrayscaleMaskToAlpha(BufferedImage image, BufferedImage mask) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] imagePixels = image.getRGB(0, 0, width, height, null, 0, width);
        int[] maskPixels = mask.getRGB(0, 0, width, height, null, 0, width);

        for (int i = 0; i < imagePixels.length; i++) {
            int color = imagePixels[i] & 0x00ffffff;
            int alpha = maskPixels[i] << 24;
            imagePixels[i] = color | alpha;
        }

        image.setRGB(0, 0, width, height, imagePixels, 0, width);
    }

    private static class Picker {
        private int x, y;
        private final int size;
        private final MutableColor color = new MutableColor(0, 0, 0);

        protected Picker(int size) {
            this.size = size;
        }

        private void setPos(int x, int y) {
            this.x = x - size/2 + PANEL_SIZE_OFFSET;
            this.y = y - size/2 + PANEL_SIZE_OFFSET;
        }
    }

    private class PickerUpdate extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            final Point p = e.getPoint();
            p.translate(-(getWidth()/2 - DRAW_SIZE/2), -(getHeight()/2 - DRAW_SIZE/2));

            final double radius = DRAW_SIZE/2.0;
            final double x = (p.getX()) - DRAW_SIZE/2.0;
            final double y = (p.getY()) - DRAW_SIZE/2.0;
            double r = Math.min(1.0, Math.sqrt(x * x + y * y) / radius);
            double theta = (Math.atan2(1-x, y) / 6.283185307179586 + 0.25) * 360;
            if(theta < 0) theta += 360;
            HSVSlider.setHSV((int)(theta), (int)(r*100), HSVSlider.value.getValue());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            this.mousePressed(e);
        }
    }
}