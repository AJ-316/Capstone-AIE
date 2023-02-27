package com.AIE.WindowPackage.ColorPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class Brush extends JRadioButton implements PaletteElement {
    private final MutableColor color;
    public static final int SIZE = 45;
    public static final int DRAW_SIZE = SIZE-6;
    public static final int OFFSET = (SIZE-DRAW_SIZE)/2;
    private final int id;

    public Brush(int id, MutableColor color) {
        this.color = color;
        this.id = id;
        setSize(new Dimension(SIZE, SIZE));
        addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED)
                ColorPalette.selectBrush(((Brush)e.getItem()).id);
        });
        ColorPalette.ELEMENTS.add(this);
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(color);
        g.fillRoundRect(OFFSET, OFFSET, DRAW_SIZE, DRAW_SIZE, 10, 10);

        g.setColor(Color.white);
        g.drawRoundRect(OFFSET, OFFSET, DRAW_SIZE, DRAW_SIZE, 10, 10);

        g.setColor(Color.black);
        g.drawRoundRect(OFFSET - 1, OFFSET - 1, DRAW_SIZE + 2, DRAW_SIZE + 2, 10, 10);
    }

    public MutableColor getColor() {
        return color;
    }

    @Override
    public void updateElement(MutableColor color, String invoker) {
        if(isSelected()) {
            this.color.set(color);
            repaint();
        }
    }
}
