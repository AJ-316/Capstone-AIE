package com.AIE.WindowPackage.ColorPackage.Sliders.UI;

import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders.RGB;

import java.awt.*;

public class RGBChannelUI extends ColorSliderUI {
    private static final float[] FRACTIONS = new float[]{0.0f, 1f};
    private final MutableColor[] gradient;
    private final MutableColor color;
    private final int channelID;

    public RGBChannelUI(int channelID) {
        this.channelID = channelID;
        gradient = new MutableColor[] {new MutableColor(0,0,0), new MutableColor(0,0,0)};
        color = new MutableColor(0,0,0);
    }

    private void updateGradient(int index) {
        int value = index == 0 ? 0 : 255;
        color.setRGB(
                channelID == 0 ? value : RGB.getRedUnit(),
                channelID == 1 ? value : RGB.getGreenUnit(),
                channelID == 2 ? value : RGB.getBlueUnit()
        );
        gradient[index].set(color);
    }

    @Override
    public void paintTrack(Graphics g) {
        updateGradient(0);
        updateGradient(1);

        LinearGradientPaint paint = new LinearGradientPaint(
                trackRect.x, 0, trackRect.width+trackRect.x, 0,
                FRACTIONS, gradient);
        ((Graphics2D) g).setPaint(paint);

        super.paintTrack(g);
    }
}
