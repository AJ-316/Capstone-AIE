package com.AIE.WindowPackage;

import com.AIE.StackPackage.SavedDataButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class History extends AbstractWindow {

    public static History GET;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final ArrayList<JPanel> historyPanels;
    private final CardLayout layout;
    private final JPanel pane;

    public History(MainFrame mainFrame) {
        super("History", mainFrame, 200, 180, MainFrame.SCREEN_WIDTH - 84, 135);
        layout = new CardLayout();
        historyPanels = new ArrayList<>();
        pane = new JPanel(layout);
        setContentPane(pane);

        addHistoryPanel();
        pack();
        setVisible(true);
        GET = this;
    }

    private JPanel addHistoryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setSize(getWidth(), getHeight());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.add(scrollPane, Integer.toString(historyPanels.size()));
        historyPanels.add(panel);
        return panel;
    }

    public void addButton(SavedDataButton button, int index) {
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets.bottom = 2;
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(getWidth(), 25));

        JPanel panel = getHistoryPanel(index);
        panel.add(button, gbc);
        panel.setVisible(true);
        panel.revalidate();
        panel.repaint();
    }

    public void removeButtons(int length, int index) {
        JPanel panel = getHistoryPanel(index);
        if(panel == null) return;
        for(int i = 0; i < length; i++) {
            panel.remove(panel.getComponents().length - 1);
        }
    }

    public SavedDataButton getButton(int id, int index) {
        JPanel panel = getHistoryPanel(index);
        return (SavedDataButton) panel.getComponent(id-1);
    }

    public void clear(int index) {
        JPanel panel = getHistoryPanel(index);
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    private JPanel getHistoryPanel(int index) {
        if(index >= historyPanels.size())
            return addHistoryPanel();
        return historyPanels.get(index);
    }

    public void selectHistoryPanel(int index) {
        layout.show(pane, Integer.toString(index));
        pane.revalidate();
        pane.repaint();
        pane.getComponent(index).setBackground(Color.red);
    }
}
