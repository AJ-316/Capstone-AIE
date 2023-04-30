package com.AIE.CanvasPackage;

import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.ToolPackage.PixelConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Canvas extends JPanel {

    private BufferedImage image;
    private BufferedImage previewImage;
    private boolean isPreviewMode = false;
    private int[] pixels;

    private final CanvasBackground background = new CanvasBackground();
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
        BufferedImage[][] bkgTiles = background.getTiledImages(getZoomedWidth(), getZoomedHeight());
        for (int x = 0; x < bkgTiles.length; x++) {
            for (int y = 0; y < bkgTiles[x].length; y++) {
                g.drawImage(bkgTiles[x][y],
                        x * bkgTiles[x][y].getWidth() + posX,
                        y * bkgTiles[x][y].getHeight() + posY,
                        bkgTiles[x][y].getWidth(), bkgTiles[x][y].getHeight(), null);
                System.out.println("l: " + x + ", " + y);
            }
        }

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

    public void changePixelLinearly(int x, int y, boolean isFilled, int size, int outline) throws IndexOutOfBoundsException {
        changePixelLinearly(x, y, ColorPalette.getBrush(brushType).getColor(), isFilled, size, outline);
    }

    public void changePixelLinearly(int x, int y, Color color, boolean isFilled, int size, int outline) throws IndexOutOfBoundsException {
        float scale = zoom/100f;
        x = (int) ((x - posX)/scale);
        y = (int) ((y - posY)/scale);

        if(color == null) {
            connector.addPixel(x, y, color, isFilled, size, outline);
            erase(x, y, size);
            return;
        }

        connector.addPixel(x, y, color, isFilled, size, outline);
        if(size == -1) {
            changeRawPixel(x, y, color.getRGB());
            return;
        }
        drawCircle(x, y, color, isFilled, size, outline);
    }

    public void changeRawPixel(int x, int y, int color) throws IndexOutOfBoundsException  {
        if(x >= image.getWidth() || x < 0 || y >= image.getHeight() || y < 0)
            return;

        pixels[x + y*image.getWidth()] = color;
    }

    private int getColor(int x, int y) {
        if(x >= image.getWidth() || x < 0 || y >= image.getHeight() || y < 0)
            return 0;

        return pixels[x + y*image.getWidth()];
    }

    public void drawCircle(int x, int y, Color color, boolean isFilled, int radius, int outline) {
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);

        Ellipse2D.Double circle = new Ellipse2D.Double(x-radius/2f, y-radius/2f, radius, radius);
        if(isFilled) {
            g2d.fill(circle);
            return;
        }

        g2d.setStroke(new BasicStroke(outline, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(circle);
    }

    public void floodFill(int x, int y, int tolerance) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scale = zoom/100f;
        x = (int) ((x - posX)/scale);
        y = (int) ((y - posY)/scale);

        int targetRGB = getColor(x, y);
        int replacementRGB = ColorPalette.getBrush(brushType).getColor().getRGB();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        boolean[][] processed = new boolean[height][width];

        Deque<Point> stack = new ArrayDeque<>();
        stack.push(new Point(x, y));

        while (!stack.isEmpty()) {
            Point p = stack.pop();
            int px = p.x;
            int py = p.y;
            if (px < 0 || px >= width || py < 0 || py >= height || processed[py][px]) {
                continue;
            }
            int pixelRGB = pixels[py * width + px];
            if (colorWithinTolerance(pixelRGB, targetRGB, tolerance)) {
                image.setRGB(px, py, replacementRGB);
                pixels[py * width + px] = replacementRGB;
                processed[py][px] = true;
                stack.push(new Point(px + 1, py));
                stack.push(new Point(px - 1, py));
                stack.push(new Point(px, py + 1));
                stack.push(new Point(px, py - 1));
            }
        }
        repaint();
    }


    private static boolean colorWithinTolerance(int rgb1, int rgb2, int tolerance) {
        int[] color1 = MutableColor.getRGBComponents(rgb1);
        int[] color2 = MutableColor.getRGBComponents(rgb2);
        int deltaR = Math.abs(color1[0] - color2[0]);
        int deltaG = Math.abs(color1[1] - color2[1]);
        int deltaB = Math.abs(color1[2] - color2[2]);
        return deltaR <= tolerance && deltaG <= tolerance && deltaB <= tolerance;
    }

    public void erase(int x, int y, int size) {
        if (size == -1) {
            changeRawPixel(x, y, 0);
            return;
        }

        for (int x1 = x - size / 2; x1 < x + size / 2; x1++) {
            for (int y1 = y - size / 2; y1 < y + size / 2; y1++) {
                changeRawPixel(x1, y1, 0);
            }
        }
        repaint();
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
