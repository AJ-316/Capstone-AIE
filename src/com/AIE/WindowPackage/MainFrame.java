package com.AIE.WindowPackage;

import com.AIE.AppLog;
import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.ImageLoader;
import com.AIE.WindowPackage.ColorPackage.Palette;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ComponentListener, WindowListener {

    // TODO: Will need to remove instance and clean up the code so singletons are not needed
    public static Container PANE;
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static int WINDOW_CENTER_X;
    public static int WINDOW_CENTER_Y;

    public MainFrame(int width, int height) {
        setLookAndFeel();
        ImageLoader.init(this);
        setIcons();

        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;

        CanvasManager.init(width, height);
        setComponentFont(CanvasManager.GET.getFont());
        PANE = getContentPane();
        PANE.setBackground(new Color(0x303031));

        this.setLayout(new BorderLayout());
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(CanvasManager.GET, BorderLayout.CENTER);
        this.pack();

        new FrameMenuBar(this);
        InfoPanel.init(this);

        WINDOW_CENTER_X = PANE.getWidth()/2;
        WINDOW_CENTER_Y = PANE.getHeight()/2;
        this.setLocationRelativeTo(null);
        addComponentListener(this);
        addWindowListener(this);

        new Canvas(Canvas.DEF_WIDTH, Canvas.DEF_HEIGHT).setReplaceable();

        setVisible(true);
    }

    private void setLookAndFeel() {
        FlatDarculaLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            UIManager.put("Slider.paintThumbArrowShape", Boolean.TRUE);
        } catch (UnsupportedLookAndFeelException e) {
            AppLog.error("MainFrame", "Could not setup look and feel (FlatDarculaLaf)", e);
            throw new RuntimeException(e);
        }
    }

    private void setComponentFont(Font componentFont) {
        Font font = componentFont.deriveFont(14f);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("Tree.font", font);
        UIManager.put("List.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("PopupMenu.font", font);
        UIManager.put("ToolTip.font", font);
        GradientPaint gradient = new GradientPaint(0, 0, Color.RED, 100, 0, Color.BLUE);

        // Customize the underline color with the gradient
        UIManager.put("TabbedPane.underlineColor", gradient);
        UIManager.put("TabbedPane.inactiveUnderlineColor", gradient);
    }

    private void setIcons() {
        ArrayList<Image> icons = new ArrayList<>();
        icons.add(ImageLoader.loadIcon("icon100x100", 100).getImage());
        icons.add(ImageLoader.loadIcon("icon96x96", 96).getImage());
        icons.add(ImageLoader.loadIcon("icon80x80", 80).getImage());
        icons.add(ImageLoader.loadIcon("icon64x64", 64).getImage());
        icons.add(ImageLoader.loadIcon("icon48x48", 48).getImage());
        icons.add(ImageLoader.loadIcon("icon40x40", 40).getImage());
        icons.add(ImageLoader.loadIcon("icon32x32", 32).getImage());
        icons.add(ImageLoader.loadIcon("icon24x24", 24).getImage());
        icons.add(ImageLoader.loadIcon("icon20x20", 20).getImage());
        icons.add(ImageLoader.loadIcon("icon16x16", 16).getImage());
        setIconImages(icons);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        WINDOW_WIDTH = getContentPane().getWidth();
        WINDOW_HEIGHT = getContentPane().getHeight();
        WINDOW_CENTER_X = WINDOW_WIDTH /2;
        WINDOW_CENTER_Y = WINDOW_HEIGHT /2;
        Canvas canvas = CanvasManager.getCurrentCanvas();
        if(canvas == null)
            return;

        canvas.setSize(getWidth(), getHeight());
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

    @Override
    public void windowClosing(WindowEvent e) {
        Palette.savePalette();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
