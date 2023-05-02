package com.AIE.WindowPackage.ImageEdit;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.HeadLabel;
import com.AIE.ImageLoader;
import com.AIE.StackPackage.SavedData;
import com.AIE.StackPackage.SavedDataButton;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Crop extends JDialog {

    private final JTextField x;
    private final JTextField y;
    private final JTextField width;
    private final JTextField height;
    private final Canvas canvas;

    public Crop(MainFrame frame, Canvas canvas) {
        super(frame);
        this.canvas = canvas;
        setTitle("Crop");
        setModal(true);
        setResizable(false);
        setUndecorated(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add components
        JLabel header = new HeadLabel("Crop Image", 100);
        add(header, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 30, 5, 5);
        JPanel xPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        x = new JTextField(5);
        xPanel.add(new JLabel("Start X:"));
        xPanel.add(x);
        add(xPanel, gbc);

        gbc.insets = new Insets(5, 30, 5, 5);
        gbc.gridy++;
        JPanel yPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        y = new JTextField(5);
        yPanel.add(new JLabel("Start Y:"));
        yPanel.add(y);
        add(yPanel, gbc);

        gbc.insets = new Insets(5, 30, 5, 5);
        gbc.gridy++;
        JPanel widthPanel = new JPanel();
        width = new JTextField(5);
        widthPanel.add(new JLabel("Width: "));
        widthPanel.add(width);
        add(widthPanel, gbc);

        gbc.insets = new Insets(5, 30, 5, 5);
        gbc.gridy++;
        JPanel heightPanel = new JPanel();
        height = new JTextField(5);
        heightPanel.add(new JLabel("Height:"));
        heightPanel.add(height);
        add(heightPanel, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> dispose());
        okButton.addActionListener(e -> cropImage());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);

        // Pack and set location
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void cropImage() {
        if (x.getText().isEmpty() || y.getText().isEmpty() || width.getText().isEmpty() || height.getText().isEmpty())
            return;
        int xCoord = Integer.parseInt(x.getText());
        int yCoord = Integer.parseInt(y.getText());
        int w = Integer.parseInt(width.getText());
        int h = Integer.parseInt(height.getText());
        BufferedImage croppedImg = canvas.getImage().getSubimage(xCoord, yCoord, w, h);

        SavedData data = new SavedData(canvas,
                ImageLoader.loadIcon("crop", SavedDataButton.ICON_SIZE), "Cropped");
        canvas.setImage(croppedImg, true);
        InfoPanel.GET.setSizeInfo(canvas.getImage().getWidth(), canvas.getImage().getHeight());
        InfoPanel.GET.setActivityInfo("Resized Image");
        data.saveNewImage();
    }
}
