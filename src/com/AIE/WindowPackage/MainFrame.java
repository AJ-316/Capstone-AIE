package com.AIE.WindowPackage;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.ImageLoader;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainFrame extends JFrame implements ComponentListener {

    // TODO: Will need to remove instance and clean up the code so singletons are not needed
    public static CanvasManager CANVAS_MANAGER;
    public static Container PANE;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_CENTER_X;
    public static int SCREEN_CENTER_Y;
    public MainFrame(int width, int height, int defaultCanvasWidth, int defaultCanvasHeight) {
        setLookAndFeel();
        ImageLoader.init(this);

        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

        CANVAS_MANAGER = new CanvasManager(width, height);
        PANE = getContentPane();
        PANE.setBackground(new Color(0x303031));

        this.setLayout(new BorderLayout());
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(CANVAS_MANAGER, BorderLayout.CENTER);
        this.pack();

        new FrameMenuBar(this);

        SCREEN_CENTER_X = PANE.getWidth()/2;
        SCREEN_CENTER_Y = PANE.getHeight()/2;
        this.setLocationRelativeTo(null);
        addComponentListener(this);

        Canvas canvas = new Canvas();
        canvas.createNewImage(defaultCanvasWidth, defaultCanvasHeight, 0);
        CANVAS_MANAGER.addCanvas(canvas);
    }
    private void setLookAndFeel() {
        FlatDarculaLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            UIManager.put("Slider.paintThumbArrowShape", Boolean.TRUE);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    public void createWindow() {
        //Finalizing JFrame
        this.setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        SCREEN_WIDTH = getContentPane().getWidth();
        SCREEN_HEIGHT = getContentPane().getHeight();
        SCREEN_CENTER_X = SCREEN_WIDTH/2;
        SCREEN_CENTER_Y = SCREEN_HEIGHT/2;
        Canvas canvas = CanvasManager.getCurrentCanvas();
        if(canvas == null)
            return;

        canvas.setSize(getWidth(), getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public static JSlider getSlider(JTextField textField, int tickSpacing, int min, int max, int defaultVal) {
        JSlider slider = new JSlider(min, max, defaultVal);

        textField.setText(String.valueOf(defaultVal));

        ValueUpdateListener.set(textField, slider);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setMinorTickSpacing(tickSpacing/4);
        return slider;
    }
}
