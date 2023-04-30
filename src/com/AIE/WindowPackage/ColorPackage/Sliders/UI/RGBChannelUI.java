package com.AIE.WindowPackage.ColorPackage.Sliders.UI;

import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders.RGBA;

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
                channelID == 0 ? value : RGBA.getRedUnit(),
                channelID == 1 ? value : RGBA.getGreenUnit(),
                channelID == 2 ? value : RGBA.getBlueUnit()
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

    public static class AlphaChannelUI extends ColorSliderUI {
        private static final float[] FRACTIONS = new float[]{0.0f, 1f};
        private final MutableColor[] gradient;
        private final MutableColor color;

        public AlphaChannelUI() {
            gradient = new MutableColor[] {new MutableColor(0,0,0), new MutableColor(0,0,0)};
            color = new MutableColor(0,0,0);
        }

        private void updateGradient(int index) {
            int value = index == 0 ? 0 : 255;
            color.setRGBA(
                    index == 0 ? 0 : RGBA.getRedUnit(),
                    index == 0 ? 0 : RGBA.getGreenUnit(),
                    index == 0 ? 0 : RGBA.getBlueUnit(),
                    value
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
}
