package com.AIE.WindowPackage;

import com.AIE.StackPackage.SavedDataButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class History extends AbstractWindow {

    private final JPanel panel;

    public static History GET;
    private final GridBagConstraints gbc = new GridBagConstraints();

    public History(MainFrame mainFrame) {
        super("History", mainFrame, 200, 180, MainFrame.SCREEN_WIDTH - 50, 150);
        setLayout(new FlowLayout());
        setVisible(true);
        JButton[] buttons = new JButton[20];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Button " + (i + 1));
            buttons[i].setBorder(new EmptyBorder(buttons[i].getBorder().getBorderInsets(buttons[i])));
        }

        panel = new JPanel(new GridBagLayout());
        panel.setSize(getWidth(), getHeight());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();

        GET = this;
    }

    public void addButton(SavedDataButton button) {
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets.bottom = 2;
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(getWidth(), 25));
        panel.add(button, gbc);
        panel.setVisible(true);
        panel.revalidate();
        panel.repaint();
    }

    public void removeButtons(int length) {
        for(int i = 0; i < length; i++) {
            panel.remove(panel.getComponents().length - 1);
        }
    }

    public SavedDataButton getButton(int id) {
        return (SavedDataButton) panel.getComponent(id-1);
    }

    public void dispose() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
}
