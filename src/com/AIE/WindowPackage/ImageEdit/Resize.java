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

public class Resize extends JDialog {

    private final JTextField percentage;
    private final JTextField width;
    private final JTextField height;
    private final Canvas canvas;

    public Resize(MainFrame frame, Canvas canvas) {
        super(frame);
        this.canvas = canvas;
        setTitle("Resize");
        setModal(true);
        setUndecorated(false);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 30, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        ButtonGroup group = new ButtonGroup();

        // Add components
        JLabel header = new HeadLabel("New Size", 100);
        add(header, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 26, 5, 5);
        JRadioButton byPercentage = new JRadioButton("By Percentage");
        percentage = new JTextField(3);
        byPercentage.addChangeListener(e -> {
            if(((JRadioButton)e.getSource()).isSelected())
                switchToPercentage();
        });
        group.add(byPercentage);
        JPanel percentagePanel = new JPanel();
        percentagePanel.add(byPercentage);
        percentagePanel.add(percentage);
        add(percentagePanel, gbc);

        gbc.insets = new Insets(5, 30, 5, 5);
        gbc.gridy++;
        gbc.gridx = 0;
        JRadioButton byAbsoluteSize = new JRadioButton("By Absolute Size");
        byAbsoluteSize.addChangeListener(e -> {
            if(((JRadioButton)e.getSource()).isSelected())
                switchToAbsolute();
        });
        group.add(byAbsoluteSize);
        add(byAbsoluteSize, gbc);

        gbc.insets = new Insets(5, 30, 5, 5);
        gbc.gridy++;
        JPanel widthPanel = new JPanel();
        width = new JTextField(5);
        widthPanel.add(new JLabel("Width:"));
        widthPanel.add(width);
        add(widthPanel, gbc);

        gbc.insets = new Insets(5, 30, 5, 5);
        gbc.gridy++;
        gbc.gridx = 0;
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
        okButton.addActionListener(e -> resizeImage());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);
        byPercentage.setSelected(true);

        // Pack and set location
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);

    }

    private void resizeImage() {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        if(!percentage.isEnabled()) {
            if(width.getText().isEmpty() || height.getText().isEmpty()) return;
            w = Integer.parseInt(width.getText());
            h = Integer.parseInt(height.getText());
        } else {
            if(percentage.getText().isEmpty()) return;
            int perc = Integer.parseInt(percentage.getText());
            w = (int) (w*perc/100f);
            h = (int) (h*perc/100f);
        }

        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = resizedImg.createGraphics();
        g.drawImage(canvas.getImage(), 0, 0, w, h, null);
        g.dispose();

        SavedData data = new SavedData(canvas,
                ImageLoader.loadIcon("resize", SavedDataButton.ICON_SIZE), "Resized");
        canvas.setImage(resizedImg, true);
        InfoPanel.GET.setSizeInfo(canvas.getImage().getWidth(), canvas.getImage().getHeight());
        InfoPanel.GET.setActivityInfo("Resized Image");
        data.saveNewImage();
    }

    private void switchToPercentage() {
        percentage.setEnabled(true);
        width.setEnabled(false);
        height.setEnabled(false);
    }

    private void switchToAbsolute() {
        percentage.setEnabled(false);
        width.setEnabled(true);
        height.setEnabled(true);
    }

}
