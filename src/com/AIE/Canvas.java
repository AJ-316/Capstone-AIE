package com.AIE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Canvas extends JPanel {

    private BufferedImage image;
    private int[] pixels;
    private float scale;

    public Canvas() {
        super(new BorderLayout());
        this.scale = 1;
        setBackground(Color.yellow);
        addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("x: " + e.getX() + ", y:" + e.getY());
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                changePixelColor(e.getX(), e.getY(), 0xffff0000);
            }
        });
    }

    public void createNewImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        setSize(width, height);
        Arrays.fill(pixels, 0xffffffff);
    }

    public void changePixelColor(int x, int y, int color) {
        x = (int)(x/scale);
        y = (int)(y/scale);

        if(x >= image.getWidth() || x < 0 || y >= image.getHeight() || y < 0)
            return;

        try {
            pixels[x + y*image.getWidth()] = color;
            repaint(); //TODO: inefficient if called this method in loop
        } catch (IndexOutOfBoundsException e) {
            //TODO: "throws"
            System.err.println("x: " + x + ", y: " + y + " = " + (x + y*image.getWidth()) + "/" + pixels.length);
        }
    }

    public void setScale(int scale) {
        this.scale = scale;
        setSize(getWidth()*scale, getHeight()*scale);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
