package com.AIE.WindowPackage.ColorPackage.Sliders.UI;

import java.awt.*;

public class HueChannelUI extends ColorSliderUI {

    private static final float[] FRACTIONS = new float[]{
            0,60/360f,120/360f,180/360f,240/360f,300/360f,1f};

    private static final Color[] GRADIENT = new Color[]{
            Color.red,Color.yellow,Color.green,Color.cyan,Color.blue,Color.magenta,Color.red};

    @Override
    public void paintTrack(Graphics g) {
        LinearGradientPaint paint = new LinearGradientPaint(
                trackRect.x, 0, trackRect.width+trackRect.x, 0, FRACTIONS, GRADIENT);
        ((Graphics2D) g).setPaint(paint);

        super.paintTrack(g);
    }
}
