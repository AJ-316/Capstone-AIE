package com.AIE;

import javax.swing.*;
import java.awt.*;

public class HeadLabel extends JLabel {

    private final int width;
    public HeadLabel(String text, int width) {
        super(text, CENTER);
        this.width = width + getPreferredSize().width;
        setPreferredSize(new Dimension(this.width, getPreferredSize().height));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ((Graphics2D) g).setPaint(new LinearGradientPaint(0, 0, width, 0, new float[]{0, 0.5f, 1},
                new Color[]{getParent().getBackground(), getForeground(), getParent().getBackground()}));
        g.drawLine(-width, getHeight()-1, width, getHeight()-1);
    }
}