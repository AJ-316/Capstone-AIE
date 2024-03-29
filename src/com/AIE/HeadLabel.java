package com.AIE;

import javax.swing.*;
import java.awt.*;

public class HeadLabel extends JLabel {

    private final int width;

    public HeadLabel(String text, int width) {
        this(text, width, 0, 0);
    }

    public HeadLabel(String text, int width, int top, int bottom) {
        super(text, CENTER);
        setBorder(BorderFactory.createEmptyBorder(top, 0, bottom, 0));
        this.width = width + getPreferredSize().width;
        setPreferredSize(new Dimension(this.width, getPreferredSize().height));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(width == 0) return;

        ((Graphics2D) g).setPaint(new LinearGradientPaint(0, 0, width, 0, new float[]{0, 0.5f, 1},
                new Color[]{getParent().getBackground(), getForeground(), getParent().getBackground()}));
        int height = getHeight()-getBorder().getBorderInsets(this).bottom-1;

        g.drawLine(-width, height, width, height);
    }
}