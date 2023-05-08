package com.AIE.WindowPackage.ImageEdit;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.HeadLabel;
import com.AIE.ImageLoader;
import com.AIE.StackPackage.SavedData;
import com.AIE.StackPackage.SavedDataButton;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;
import com.AIE.WindowPackage.ToolPackage.SmoothIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ResizeImage extends JDialog {

    private final JTextField percentage;
    private final JTextField width;
    private final JTextField height;
    private final Canvas canvas;
    private static final Dimension ROW_DIMENSION = new Dimension(200, 35);
    private static final SmoothIcon icon = ImageLoader.loadIcon("IResize", ImageLoader.MENU_ICON_SIZE);

    public ResizeImage(MainFrame frame, Canvas canvas) {
        super(frame);
        this.canvas = canvas;
        setSize(300, 300);
        setTitle("Resize");
        setIconImage(icon.getImage());
        setModal(true);
        setUndecorated(false);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        ButtonGroup group = new ButtonGroup();
        JRadioButton byPercentage = new JRadioButton("By Percentage: ");
        JRadioButton byAbsolute = new JRadioButton("By Absolute Size: ");
        byPercentage.addChangeListener(e -> {
            if(((JRadioButton)e.getSource()).isSelected())
                switchToPercentage();
        });
        byAbsolute.addChangeListener(e -> {
            if(((JRadioButton)e.getSource()).isSelected())
                switchToAbsolute();
        });
        group.add(byPercentage);
        group.add(byAbsolute);

        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        okBtn.addActionListener(e -> { resizeImage(); dispose(); });
        cancelBtn.addActionListener(e -> dispose());
        percentage = new JTextField("100", 3);
        width = new JTextField(Integer.toString(canvas.getImage().getWidth()), 5);
        height = new JTextField(Integer.toString(canvas.getImage().getHeight()), 5);

        add(new HeadLabel("New Size", 100));
        addRow(FlowLayout.LEFT, byPercentage, percentage, new JLabel("%"));
        addRow(FlowLayout.LEFT, byAbsolute);
        addRow(FlowLayout.CENTER, new JLabel("Width:"), width);
        addRow(FlowLayout.CENTER, new JLabel("Height:"), height);
        addRow(FlowLayout.CENTER, okBtn, cancelBtn);

        setResizable(false);
        byPercentage.setSelected(true);
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void addRow(int align, Component... components) {
        JPanel row = new JPanel(new FlowLayout(align));
        for(Component component : components) {
            row.add(component);
        }
        row.setPreferredSize(ROW_DIMENSION);
        add(row);
    }

    /*public ResizeImage(MainFrame frame, Canvas canvas) {
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
        percentage = new JTextField("100", 3);
        byPercentage.addChangeListener(e -> {
            if(((JRadioButton)e.getSource()).isSelected())
                switchToPercentage();
        });
        group.add(byPercentage);
        JPanel percentagePanel = new JPanel();
        percentagePanel.add(byPercentage);
        percentagePanel.add(percentage);
        percentagePanel.add(new JLabel("%"));
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
        okButton.addActionListener(e -> { resizeImage(); dispose(); });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);
        byPercentage.setSelected(true);

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }*/

    private void resizeImage() {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        if(!percentage.isEnabled()) {
            if(width.getText().isEmpty() || height.getText().isEmpty()) return;
            w = Integer.parseInt(width.getText());
            h = Integer.parseInt(height.getText());
            if(canvas.getImage().getWidth() == w && canvas.getImage().getHeight() == h)
                return;
        } else {
            if(percentage.getText().isEmpty()) return;
            int perc = Integer.parseInt(percentage.getText());
            if(perc == 100) return;
            w = (int) (w*perc/100f);
            h = (int) (h*perc/100f);
        }

        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = resizedImg.createGraphics();
        g.drawImage(canvas.getImage(), 0, 0, w, h, null);
        g.dispose();

        SavedData data = new SavedData(canvas,
                ImageLoader.loadIcon("IResize", SavedDataButton.ICON_SIZE), "Resized");
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
