package com.AIE.WindowPackage.ImageEdit;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.HeadLabel;
import com.AIE.ImageLoader;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;
import com.AIE.WindowPackage.ToolPackage.SmoothIcon;

import javax.swing.*;
import java.awt.*;

public class NewImage extends JDialog {

    private final JTextField name;
    private final JTextField width;
    private final JTextField height;
    private Canvas canvas;
    private static final Dimension ROW_DIMENSION = new Dimension(200, 35);
    private static final SmoothIcon icon = ImageLoader.loadIcon("New", ImageLoader.MENU_ICON_SIZE);

    public NewImage(MainFrame frame, Canvas canvas) {
        super(frame);
        this.canvas = canvas;
        setSize(300, 230);
        setTitle("New Image");
        setIconImage(icon.getImage());
        setModal(true);
        setUndecorated(false);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        okBtn.addActionListener(e -> { createImage(); dispose(); });
        cancelBtn.addActionListener(e -> dispose());
        name = new JTextField("Untitled", 8);
        width = new JTextField("1000", 5);
        height = new JTextField("700", 5);

        add(new HeadLabel("Create New Image", 100));
        addRow(new JLabel("File Name:"), name);
        addRow(new JLabel("Width:"), width);
        addRow(new JLabel("Height:"), height);
        addRow(okBtn, cancelBtn);

        setResizable(false);
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void addRow(Component... components) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for(Component component : components) {
            row.add(component);
        }
        row.setPreferredSize(ROW_DIMENSION);
        add(row);
    }

    private void createImage() {
        if (width.getText().isEmpty() || height.getText().isEmpty())
            return;
        int w = Integer.parseInt(width.getText());
        int h = Integer.parseInt(height.getText());

        if(w == 0 && h == 0) {
            return;
        }
        String fileName = "Untitled";
        if(!name.getText().isEmpty())
            fileName = name.getText();

        if(canvas == null) {
            canvas = new Canvas(w, h);
            setName(fileName);
        } else canvas.create(fileName, null, w, h);

        InfoPanel.GET.setSizeInfo(canvas.getImage().getWidth(), canvas.getImage().getHeight());
        InfoPanel.GET.setActivityInfo("New Image");
    }
}
