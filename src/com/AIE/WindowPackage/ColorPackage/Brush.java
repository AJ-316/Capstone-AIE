package com.AIE.WindowPackage.ColorPackage;

import com.AIE.WindowPackage.ColorPackage.Sliders.ColorChannelSlider;

import javax.swing.*;
import java.awt.*;

public class Brush extends JPanel {

    public static Brush PRIMARY;
    public static Brush SECONDARY;
    public static JPanel brushContainer;
    public static final int PANEL_SIZE = 45;
    public static final int DRAW_SIZE = PANEL_SIZE-6;
    public static final int OFFSET = (PANEL_SIZE-DRAW_SIZE)/2;

    private final MutableColor color;
    private boolean isSelected;

    private static JComboBox<String> brushSelection;

    private Brush(MutableColor color, int offsetX, int offsetY) {
        this.color = color;
        setBounds(offsetX, offsetY, PANEL_SIZE, PANEL_SIZE);
        setOpaque(false);
    }

    public static void createBrushes(int x, int y) {
        PRIMARY = new Brush(new MutableColor(255, 0, 0), 10, 40);
        SECONDARY = new Brush(new MutableColor(255, 255, 255), (int) (PANEL_SIZE/1.2f), (int) (PANEL_SIZE/1.2f) + 30);

        brushSelection = new JComboBox<>(new String[] {"Primary", "Secondary"});
        brushSelection.setBounds(0, 0, 100, 25);
        brushSelection.addItemListener(e -> {
            int selected = brushSelection.getSelectedIndex();
            PRIMARY.isSelected = selected == 0;
            SECONDARY.isSelected = selected == 1;

            ColorChannelSlider.setColor(getSelected().getColor());
        });
        brushContainer = new JPanel(null);
        brushContainer.setBounds(x, y, (int) (PANEL_SIZE*2.2f), 150);
        brushContainer.add(brushSelection);
        brushContainer.add(PRIMARY);
        PRIMARY.isSelected = true;
        brushContainer.add(SECONDARY);
    }

    public static void selectBrush(int i) {
        brushSelection.setSelectedIndex(i);
    }

    public static Brush getBrush(int i) {
        return i == 0 ? PRIMARY : SECONDARY;
    }

    public void setColor(MutableColor color) {
        this.color.set(color);
        repaint();
    }

    public static Brush getSelected() {
        return PRIMARY.isSelected ? PRIMARY : SECONDARY;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(color);
        g.fillRoundRect(OFFSET, OFFSET, DRAW_SIZE, DRAW_SIZE, 10, 10);

//        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.setColor(Color.white);
        g.drawRoundRect(OFFSET, OFFSET, DRAW_SIZE, DRAW_SIZE, 10, 10);

//        ((Graphics2D) g).setStroke(new BasicStroke(1));
        g.setColor(Color.black);
        g.drawRoundRect(OFFSET-1, OFFSET-1, DRAW_SIZE+2, DRAW_SIZE+2, 10, 10);
    }

    public MutableColor getColor() {
        return color;
    }
}
