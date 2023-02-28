package com.AIE;

import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ToolPackage.PixelConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Canvas extends JPanel {

    private BufferedImage image;
    private int[] pixels;

    private final PixelConnector connector;
    private int brushType;
    private int zoom, posX, posY;

    public Canvas() {
        super();
        setLocation(0, 0);
        connector = new PixelConnector(this);
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, posX, posY, (int) (image.getWidth()/100f*zoom), (int) (image.getHeight()/100f*zoom), null);
    }

    public void createNewImage(int width, int height, int type) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        if(type == 0)
            Arrays.fill(pixels, 0xffffffff);
        setZoom(100);
    }

    public void releasePixels() {
        connector.releasePixels();
    }

    public void changePixelLinearly(int x, int y) throws IndexOutOfBoundsException {
        float scale = zoom/100f;
        x = (int) ((x - posX)/scale);
        y = (int) ((y - posY)/scale);

        connector.addPixel(x, y);
        changeRawPixel(x, y);
    }

    public void changeRawPixel(int x, int y) throws IndexOutOfBoundsException  {
        if(x >= image.getWidth() || x < 0 || y >= image.getHeight() || y < 0)
            return;

        pixels[x + y*image.getWidth()] = ColorPalette.getBrush(brushType).getColor().getRGB();
    }

    // may use later
    public void changeScaledPixel(int x, int y) throws IndexOutOfBoundsException  {
        float scale = zoom/100f;
        changeRawPixel((int) (x/scale), (int) (y/scale));
    }

    public void setBrushType(int brushType) {
        this.brushType = brushType;
    }

    public void updateCanvas() {
        repaint();
    }

    public void setPosXY(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        updateCanvas();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        if(zoom < 0 || zoom > 64000)
            return;
        this.zoom = zoom;
        updateCanvas();
    }
}
