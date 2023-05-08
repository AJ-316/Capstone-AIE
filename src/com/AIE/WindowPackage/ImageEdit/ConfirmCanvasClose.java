package com.AIE.WindowPackage.ImageEdit;

import com.AIE.CanvasPackage.Canvas;
import com.AIE.CanvasPackage.CanvasManager;
import com.AIE.HeadLabel;
import com.AIE.ImageLoader;
import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.PanelsPackage.InfoPanel;
import com.AIE.WindowPackage.ToolPackage.SmoothIcon;

import javax.swing.*;
import java.awt.*;

public class ConfirmCanvasClose {

    private final Canvas canvas;
    private final MainFrame frame;
    private static final Dimension ROW_DIMENSION = new Dimension(200, 35);
    private static final SmoothIcon icon = ImageLoader.loadIcon("Close", ImageLoader.MENU_ICON_SIZE);

    public ConfirmCanvasClose(MainFrame frame, Canvas canvas) {
        this.canvas = canvas;
        this.frame = frame;
        closeImage(false);
    }

    private class ConfirmDialog extends JDialog {
        public ConfirmDialog() {
            super(frame);
            setSize(225, 160);
            setTitle("Close Canvas");
            setIconImage(icon.getImage());
            setModal(true);
            setUndecorated(false);
            setResizable(false);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new FlowLayout(FlowLayout.CENTER));

            JButton okBtn = new JButton("OK");
            JButton cancelBtn = new JButton("Cancel");
            okBtn.addActionListener(e -> { closeImage(true); dispose(); });
            cancelBtn.addActionListener(e -> dispose());

            add(new HeadLabel("Confirm", 100));
            add(new JLabel("Image is not Saved"));
            add(new JLabel("Are you sure?"));
            addRow(okBtn, cancelBtn);

            setResizable(true);
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
    }

    private void closeImage(boolean force) {
        if(!canvas.isLastSaved() && !force) {
            new ConfirmDialog();
            return;
        }
        CanvasManager.GET.remove(canvas);

        InfoPanel.GET.setSizeInfo(canvas.getImage().getWidth(), canvas.getImage().getHeight());
        InfoPanel.GET.setActivityInfo("Canvas Closed: " + canvas.getName());
    }
}
