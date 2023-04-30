package com.AIE.WindowPackage.ColorPackage.Sliders.UI;

import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders.RGBA;

import java.awt.*;

public class ValueChannelUI extends ColorSliderUI {
    private static final float[] FRACTIONS = new float[]{0.0f, 1f};
    private final MutableColor[] gradient = new MutableColor[]{new MutableColor(0,0,0), new MutableColor(0,0,0)};

    @Override
    public void paintTrack(Graphics g) {
        gradient[1].set(RGBA.getColor());

        LinearGradientPaint paint = new LinearGradientPaint(
                trackRect.x, 0, trackRect.width+trackRect.x, 0, FRACTIONS, gradient);

        ((Graphics2D) g).setPaint(paint);
        super.paintTrack(g);
    }
}
