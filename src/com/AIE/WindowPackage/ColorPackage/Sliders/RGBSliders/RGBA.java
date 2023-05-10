package com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders;

import com.AIE.WindowPackage.ColorPackage.MutableColor;

import java.awt.*;

public class RGBA {

    private static final RedChannel RED = new RedChannel();
    private static final GreenChannel GREEN = new GreenChannel();
    private static final BlueChannel BLUE = new BlueChannel();
    private static final AlphaChannel ALPHA = new AlphaChannel();

    private static final MutableColor COLOR = new MutableColor(255, 0, 0);

    public static void create(Container container) {
        container.add(RED);
        container.add(GREEN);
        container.add(BLUE);
        container.add(ALPHA);
    }

    public static int getRedUnit() {
        return RED.getUnitVal();
    }

    public static int getGreenUnit() {
        return GREEN.getUnitVal();
    }

    public static int getBlueUnit() {
        return BLUE.getUnitVal();
    }

    public static int getAlphaUnit() {
        return ALPHA.getUnitVal();
    }

    public static MutableColor getColor() {
        updateColor();
        return COLOR;
    }

    private static void updateColor() {
        COLOR.setRGBA(RED.getUnitVal(), GREEN.getUnitVal(), BLUE.getUnitVal(), ALPHA.getUnitVal());
    }

}
