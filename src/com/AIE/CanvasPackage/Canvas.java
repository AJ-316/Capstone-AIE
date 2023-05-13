package com.AIE.CanvasPackage;

import com.AIE.ImageLoader;
import com.AIE.StackPackage.SavedData;
import com.AIE.StackPackage.SavedDataButton;
import com.AIE.StackPackage.UndoManager;
import com.AIE.WindowPackage.ColorPackage.ColorPaletteWindow;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;
import com.AIE.WindowPackage.ToolPackage.SmoothIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Canvas extends JPanel {

    private static final SmoothIcon imgAddedIcon = ImageLoader.loadIcon("ImageAdded", SavedDataButton.ICON_SIZE);
    public static final int DEF_WIDTH = 1000;
    public static final int DEF_HEIGHT = 700;
    private BufferedImage image;
    private BufferedImage previewImage;
    private boolean isPreviewMode = false;
    private int[] pixels;

    private final CanvasBackground background = new CanvasBackground();
    private final PixelConnector connector;
    private int brushType;
    private int zoom, posX, posY;
    private boolean isReplaceable;
    public UndoManager undoManager;
    private boolean lastSaved;

    public Canvas() {
        super(null);
        connector = new PixelConnector(this);
        addListeners(new CanvasNavigation(), new CanvasToolInteraction(this));
    }

    public boolean isLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(boolean lastSaved) {
        this.lastSaved = lastSaved;
    }

    public Canvas(String name, BufferedImage newImage) {
        this();
        create(name, newImage, 0, 0);
    }

    public Canvas(int width, int height) {
        this();
        create("Untitled", null, width, height);
    }

    private void addListeners(CanvasNavigation navigationListener, CanvasToolInteraction canvasToolInteraction) {
        addMouseListener(navigationListener);
        addMouseMotionListener(navigationListener);
        addMouseWheelListener(navigationListener);

        addMouseListener(canvasToolInteraction);
        addMouseMotionListener(canvasToolInteraction);
    }

    public void confirmPreview() {
        setImage(previewImage, false);
        InfoPanel.GET.setSizeInfo(image.getWidth(), image.getHeight());
    }

    public void setImage(BufferedImage newImage, boolean resized) {
        int width = image.getWidth();
        int height = image.getHeight();
        if(resized) {
            width = newImage.getWidth();
            height = newImage.getHeight();
        }

        image = ImageLoader.createImage(newImage, width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        repaint();
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
            }
        }

        g.drawImage(isPreviewMode ? previewImage : image, posX, posY, getZoomedWidth(), getZoomedHeight(), null);
    }

    public void create(String name, BufferedImage newImage, int width, int height) {
        setName(name);
        String action = "New Image";
        if(newImage != null) {
            image = new BufferedImage(newImage.getWidth(), newImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.createGraphics();
            g.drawImage(newImage, 0, 0, null);
            g.dispose();
            pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            action = "Loaded Image";
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            Arrays.fill(pixels, 0xffffffff);
        }

        if(!isReplaceable) {
            CanvasManager.GET.addCanvas(this);
        } else isReplaceable = false;
        CanvasManager.GET.updateTabComponent(this);

        setZoom(100);
        setImageToCenter();
        initUndoManager(action);
    }

    private void initUndoManager(String action) {
        if(undoManager == null)
            undoManager = new UndoManager(this);
        undoManager.reuse();
        new SavedData(this, imgAddedIcon, action).saveNewImage();
        InfoPanel.GET.setActivityInfo(action);
        InfoPanel.GET.setSizeInfo(image.getWidth(), image.getHeight());
    }

    public void releasePixels() {
        connector.releasePixels();
    }

    private void setImageToCenter() {
        this.posX = MainFrame.WINDOW_CENTER_X - image.getWidth()/2;
        this.posY = MainFrame.WINDOW_CENTER_Y - image.getHeight()/2;
    }

    public void changePixelLinearly(int x, int y, boolean isFilled, int size, int outline) throws IndexOutOfBoundsException {
        changePixelLinearly(x, y, ColorPaletteWindow.getBrush(brushType).getColor(), isFilled, size, outline);
    }

    public void changePixelLinearly(int x, int y, Color color, boolean isFilled, int size, int outline) throws IndexOutOfBoundsException {
        x = getScaledX(x);
        y = getScaledY(y);

        connector.addPixel(x, y, color, isFilled, size, outline);

        if(color == null) {
            erase(x, y, size);
            return;
        }

        if(outline == -1) {
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

    public int getColor(int x, int y) {
        if(isOutsideCanvas(x, y))
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
        x = getScaledX(x);
        y = getScaledY(y);

        int targetRGB = getColor(x, y);
        int replacementRGB = ColorPaletteWindow.getBrush(brushType).getColor().getRGB();
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
        if (size == 1) {
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
        InfoPanel.GET.setZoomInfo(zoom);
        return true;
    }

    public boolean isOutsideCanvas(int x, int y) {
        x = getScaledX(x);
        y = getScaledY(y);
        return x >= image.getWidth() || x < 0 || y >= image.getHeight() || y < 0;
    }

    public Point getScaledPoint(Point point) {
        point.x = getScaledX(point.x);
        point.y = getScaledY(point.y);
        return point;
    }

    public int getScaledX(int x) {
        return (int) ((x - posX)/(zoom/100f));
    }

    public int getScaledY(int y) {
        return (int) ((y - posY)/(zoom/100f));
    }

    public MutableColor getColor() {
        return ColorPaletteWindow.getBrush(brushType).getColor();
    }

    public void addKeyListenerToRootPane(KeyListener listener) {
        getRootPane().addKeyListener(listener);
    }

    public boolean isReplaceable() {
        return undoManager.isEmpty() && isReplaceable;
    }

    public void setReplaceable() {
        this.isReplaceable = true;
    }

    public void setName(String name) {
        super.setName(name);
        CanvasManager.GET.updateTabComponent(this);
    }

    public int getIndex() {
        return CanvasManager.GET.indexOfComponent(this);
    }
}
