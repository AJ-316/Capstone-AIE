package com.AIE;

import javax.swing.*;
import java.awt.*;

public class SeparatorLabel extends JLabel {

    private final int width;
    private final BasicStroke stroke;

    public SeparatorLabel(int width, int thickness, int top, int bottom) {
        super(" ", CENTER);
        stroke = new BasicStroke(thickness);
        setBorder(BorderFactory.createEmptyBorder(top, 0, bottom, 0));
        this.width = width + getPreferredSize().width;
        setPreferredSize(new Dimension(this.width, getPreferredSize().height));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ((Graphics2D) g).setPaint(new LinearGradientPaint(0, 0, width, 0, new float[]{0, 0.5f, 1},
                new Color[]{getParent().getBackground(), getForeground(), getParent().getBackground()}));
        int height = getHeight()-getBorder().getBorderInsets(this).bottom-1;

        ((Graphics2D) g).setStroke(stroke);
        g.drawLine(-width, height, width, height);
    }

}
