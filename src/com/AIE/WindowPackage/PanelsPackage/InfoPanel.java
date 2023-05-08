package com.AIE.WindowPackage.PanelsPackage;

import com.AIE.ImageLoader;
import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class InfoPanel extends JPanel {

    public static InfoPanel GET;

    private final JLabel activityInfo = new JLabel();
    private final JLabel sizeInfo = new JLabel();
    private final JLabel pixelCoordinateInfo = new JLabel("0, 0");
    private final JLabel zoomInfo = new JLabel();
    private final JLabel errorInfo = new JLabel();

    public InfoPanel() {
        super(new GridBagLayout());
        setPreferredSize(new Dimension(0, 35));
        setBorder(new MatteBorder(1, 0, 0, 0, new Color(0x505254)));
        setBackground(new Color(0x303234));

        errorInfo.setForeground(Color.red);
        errorInfo.setFont(errorInfo.getFont().deriveFont(Font.BOLD));
        activityInfo.setIcon(ImageLoader.loadIcon("Activity", 20));
        sizeInfo.setIcon(ImageLoader.loadIcon("Size", 20));
        zoomInfo.setIcon(ImageLoader.loadIcon("Zoom In", 20));
        pixelCoordinateInfo.setIcon(ImageLoader.loadIcon("Coordinate", 20));
        addAll(errorInfo, activityInfo, sizeInfo, pixelCoordinateInfo, zoomInfo);
    }

    public void setErrorInfo(String error) {
        errorInfo.setText(error);
    }

    public void setActivityInfo(String title) {
        activityInfo.setText(title);
    }

    public void setSizeInfo(int width, int height) {
        sizeInfo.setText(width+" x "+height);
    }

    public void setPixelCoordinateInfo(int x, int y) {
        pixelCoordinateInfo.setText(x+", "+y);
    }

    public void setZoomInfo(int zoom) {
        zoomInfo.setText(zoom + "%");
    }

    public static void init(MainFrame frame) {
        GET = new InfoPanel();
        frame.add(GET, BorderLayout.SOUTH);
    }

    public void addAll(Component... components) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        for(Component component : components) {
            constraints.gridx += 1;
            add(component, constraints);
        }
    }
}
