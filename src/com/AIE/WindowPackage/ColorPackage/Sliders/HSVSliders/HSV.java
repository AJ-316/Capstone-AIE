package com.AIE.WindowPackage.ColorPackage.Sliders.HSVSliders;

import com.AIE.WindowPackage.ColorPackage.MutableColor;

import java.awt.*;

public class HSV {

    private static final HueChannel HUE = new HueChannel();
    private static final SaturationChannel SATURATION = new SaturationChannel();
    private static final ValueChannel VALUE = new ValueChannel();

    private static final MutableColor COLOR = new MutableColor(255, 0, 0);

    public static void create(Container container) {
        container.add(HUE);
        container.add(SATURATION);
        container.add(VALUE);
    }

    public static MutableColor toRGB() {
        return toRGB(getHueUnit(), getSaturation(), getValue());
    }

    public static MutableColor toRGB(int hue, float saturation, float value) {
        float c = value * saturation;
        float x = c * (1 - Math.abs((hue / 60f) % 2 - 1));
        float m = value - c;

        if (hue <=  60) convertToUnit(c, x, 0, m); else
        if (hue <= 120) convertToUnit(x, c, 0, m); else
        if (hue <= 180) convertToUnit(0, c, x, m); else
        if (hue <= 240) convertToUnit(0, x, c, m); else
        if (hue <= 300) convertToUnit(x, 0, c, m); else
        if (hue <= 360) convertToUnit(c, 0, x, m);

        return COLOR;
    }

    private static void convertToUnit(float r, float g, float b, float m) {
        COLOR.setRGB((int)((r+m)*255), (int)((g+m)*255), (int)((b+m)*255));
    }

    public static int getHueUnit() {
        return HUE.getUnitVal();
    }

    public static float getHue() {
        return HUE.getVal();
    }

    public static float getSaturation() {
        return SATURATION.getVal();
    }

    public static float getValue() {
        return VALUE.getVal();
    }

}
