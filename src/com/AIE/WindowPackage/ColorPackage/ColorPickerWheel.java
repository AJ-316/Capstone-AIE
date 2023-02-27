package com.AIE.WindowPackage.ColorPackage;

import com.AIE.WindowPackage.ColorPackage.Sliders.HSVSlider;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ColorPickerWheel extends JPanel {

    private static final BasicStroke pickerStroke = new BasicStroke(2);
    private static final int IMAGE_SIZE = 1024;
    private static final int DRAW_SIZE = 175;
    private static final int PANEL_SIZE_OFFSET = 25;
    private static final double RADIUS = DRAW_SIZE/2.0;
    private static final double D_THETA = 6.283185307179586;
    private final int[] row;

    private final Picker picker;
    private final BufferedImage image;

    public ColorPickerWheel(int x, int y) {
        super(null);
        setBounds(x, y, DRAW_SIZE+PANEL_SIZE_OFFSET*2, DRAW_SIZE+PANEL_SIZE_OFFSET*2);
        setOpaque(false);

        picker = new Picker(10);
        image = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        row = new int[IMAGE_SIZE];

        generateColorWheel();
        updateColorPicker();
        applyGrayscaleMaskToAlpha(image, MainFrame.loadImage("color_palette/mask"));

        PickerUpdate pickerUpdate = new PickerUpdate();
        addMouseListener(pickerUpdate);
        addMouseMotionListener(pickerUpdate);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(pickerStroke);
        g2d.setColor(Color.white);
        g2d.drawOval(PANEL_SIZE_OFFSET, PANEL_SIZE_OFFSET, DRAW_SIZE, DRAW_SIZE);

        g2d.drawImage(this.image, PANEL_SIZE_OFFSET, PANEL_SIZE_OFFSET, DRAW_SIZE, DRAW_SIZE, null);

        g2d.setColor(HSVSlider.getHSVToRGB(picker.color, HSVSlider.hue.getValue(), HSVSlider.saturation.getValue(), 100));
        g2d.fillOval(picker.x, picker.y, picker.size, picker.size);

        g2d.setColor(Color.white);
        g2d.drawOval(picker.x, picker.y, picker.size, picker.size);

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(picker.x-1, picker.y-1, picker.size+2, picker.size+2);
    }

    public void updateColorPicker() {
        double theta = D_THETA;
        if(HSVSlider.hue != null)
            theta *= HSVSlider.hue.getUnitValue();

        if (theta < 0.0) theta += D_THETA;

        double rad = RADIUS;
        if(HSVSlider.saturation != null)
            rad *= HSVSlider.saturation.getUnitValue();

        picker.setPos((int)(rad * Math.cos(theta) + 0.5f + RADIUS), (int)(rad * Math.sin(theta) + 0.5f + RADIUS));
        repaint();
    }

    private void generateColorWheel() {
        final float radius = IMAGE_SIZE/2.0f;

        for(int y = 0; y < IMAGE_SIZE; ++y) {
            final float y2 = y - radius;

            for(int x = 0; x < IMAGE_SIZE; ++x) {
                final float x2 = x - radius;

                final double r = Math.sqrt(x2 * x2 + y2 * y2);
                if (r > radius) {
                    row[x] = 0xffffffff;
                    continue;
                }

                row[x] = Color.HSBtoRGB(
                        (float) (Math.atan2(y2, x2) / D_THETA),
                        (float) (r / radius), 1);
            }
            image.getRaster().setDataElements(0, y, IMAGE_SIZE, 1, row);
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
            if(SwingUtilities.isRightMouseButton(e)) Brush.selectBrush(1);
            else if(SwingUtilities.isLeftMouseButton(e)) Brush.selectBrush(0);

            final Point p = e.getPoint();
            p.translate((int) -(getWidth()/2 - RADIUS), (int) -(getHeight()/2 - RADIUS));

            final double x = p.getX() - RADIUS;
            final double y = p.getY() - RADIUS;
            double r = Math.min(1.0, Math.sqrt(x * x + y * y) / RADIUS);
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