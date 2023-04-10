package com.AIE.WindowPackage.ColorPackage.Sliders.UI;

import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.HSVSliders.HSV;

import java.awt.*;

public class SaturationChannelUI extends ColorSliderUI {
    private static final float[] FRACTIONS = new float[]{0.0f, 1f};
    private final MutableColor[] gradient = new MutableColor[]{
            new MutableColor(0,0,0), new MutableColor(0,0,0)};

    @Override
    public void paintTrack(Graphics g) {
        float fraction = HSV.getValue();
        gradient[0].setRGB(fraction, fraction, fraction);
        gradient[1].set(HSV.toRGB(HSV.getHueUnit(), 1, HSV.getValue()));

        LinearGradientPaint paint = new LinearGradientPaint(
                trackRect.x, 0, trackRect.width+trackRect.x, 0, FRACTIONS, gradient);

        ((Graphics2D) g).setPaint(paint);
        super.paintTrack(g);
    }
}
