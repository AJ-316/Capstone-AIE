package com.AIE;

import com.AIE.WindowPackage.ColorChannelSlider;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Canvas extends JPanel {

    private BufferedImage image;
    private int[] pixels;
    private float scale;
    private float width, height;

    public Canvas() {
        super();
        this.scale = 100;
        setBackground(Color.yellow);
        addListeners(new CanvasNavigation(), new CanvasToolInteraction(this));
    }

    private void addListeners(CanvasNavigation navigationListener, CanvasToolInteraction canvasToolInteraction) {
        addMouseListener(navigationListener);
        addMouseMotionListener(navigationListener);
        addMouseWheelListener(navigationListener);

        addMouseListener(canvasToolInteraction);
        addMouseMotionListener(canvasToolInteraction);
    }

    public void createNewImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        this.width = width;
        this.height = height;
        setScale(1);
        setLocation(MainFrame.SCREEN_WIDTH/2 - width, MainFrame.SCREEN_HEIGHT/2 - height);
        Arrays.fill(pixels, 0xffffffff);
    }

    public void changePixelColor(int x, int y) throws IndexOutOfBoundsException  {
        System.out.print("xB:"+x+", yB:"+y+", ");
        x = (int)(x/scale);
        y = (int)(y/scale);
        System.out.println("x:"+x+", y:"+y+", scale:"+scale+", multiplier: x["+scale+"],y["+scale+"]");
        if(x >= image.getWidth() || x < 0 || y >= image.getHeight() || y < 0)
            return;
        System.out.println(x + y*image.getWidth());
        pixels[x + y*image.getWidth()] = ColorChannelSlider.getColor().getRGB(); //TODO: Black Color
    }
    /*
    xB:0, yB:0, x:0, y:0, scale:1.0, multiplier: x[1.0],y[1.0]
    index = 0

    xB:5, yB:1, x:5, y:1, scale:1.0, multiplier: x[1.0],y[1.0]
    index = 4005

    xB:6, yB:4, x:6, y:4, scale:1.0, multiplier: x[1.0],y[1.0]
    index = 16006

    xB:1, yB:2, x:1, y:2, scale:1.0, multiplier: x[1.0],y[1.0]
    index = 8001
    * */
    public void updateCanvas() {
        repaint();
    }

    public void setScale(float scale) {
        this.scale = Math.max(1, scale);
        setSize((int) (width*this.scale), (int) (height*this.scale));
        updateCanvas();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    public float getScale() {
        return scale;
    }
}
