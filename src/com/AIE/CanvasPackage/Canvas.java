package com.AIE.CanvasPackage;

import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ToolPackage.PixelConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Canvas extends JPanel {

    private BufferedImage image;
    private BufferedImage previewImage;
    private boolean isPreviewMode = false;
    private int[] pixels;

    private final PixelConnector connector;
    private int brushType;
    private int zoom, posX, posY;

    public Canvas() {
        super();
        connector = new PixelConnector(this);
        addListeners(new CanvasNavigation(), new CanvasToolInteraction(this));
    }

    private void addListeners(CanvasNavigation navigationListener, CanvasToolInteraction canvasToolInteraction) {
        addMouseListener(navigationListener);
        addMouseMotionListener(navigationListener);
        addMouseWheelListener(navigationListener);

        addMouseListener(canvasToolInteraction);
        addMouseMotionListener(canvasToolInteraction);
    }

    public void confirmPreview() {
        setImage(previewImage);
    }

    public void disablePreviewMode() {
        isPreviewMode = false;
        repaint();
    }

    public void setPreviewImage(BufferedImage image) {
        if(!isPreviewMode) isPreviewMode = true;
        this.previewImage = image;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(isPreviewMode ? previewImage : image, posX, posY, getZoomedWidth(), getZoomedHeight(), null);
    }

    public void createNewImage(int width, int height, int type) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        if(type == 0)
            Arrays.fill(pixels, 0xffffffff);
        setZoom(100);
        setImageToCenter();
    }

    public void setImage(BufferedImage newImage) {
        image = ImageLoader.createImage(newImage, image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        repaint();
    }

    public void setNewImage(BufferedImage newImage) {
        image = ImageLoader.createImage(newImage, BufferedImage.TYPE_INT_ARGB);

        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        setZoom(100);
        setImageToCenter();
    }

    public void releasePixels() {
        connector.releasePixels();
    }

    private void setImageToCenter() {
        this.posX = MainFrame.SCREEN_CENTER_X - image.getWidth()/2;
        this.posY = MainFrame.SCREEN_CENTER_Y - image.getHeight()/2;
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

    // might use later
    // public void changeScaledPixel(int x, int y) throws IndexOutOfBoundsException  {
    //     float scale = zoom/100f;
    //     changeRawPixel((int) (x/scale), (int) (y/scale));
    // }

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

    public int getZoomedWidth() {
        return (int) (image.getWidth()/100f*zoom);
    }

    public int getZoomedHeight() {
        return (int) (image.getHeight()/100f*zoom);
    }

    public int getZoom() {
        return zoom;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean setZoom(int zoom) {
        if(zoom <= 0 || zoom > 64000)
            return false;
        this.zoom = zoom;
        updateCanvas();
        return true;
    }
}
