package com.AIE.WindowPackage;

import java.awt.*;

public class ColorChannelSlider extends ColorSlider {

    private final int channelID;
    public static ColorChannelSlider red, blue, green;
    private static final MutableColor color = new MutableColor(0, 0, 0);

    public static ColorChannelSlider create(int channelID) {
        switch (channelID) {
            case 0 -> {
                red = new ColorChannelSlider(channelID);
                red.slider.setName("red");
                return red;
            }
            case 1 -> {
                green = new ColorChannelSlider(channelID);
                green.slider.setName("green");
                return green;
            }
            case 2 -> {
                blue = new ColorChannelSlider(channelID);
                blue.slider.setName("blue");
                return blue;
            }
        }
        return null;
    }

    private ColorChannelSlider(int channelID) {
        this.channelID = channelID;
        init(new ChannelSliderUI(), 255);
    }

    @Override
    protected void update(String name) {
        if(name.equals(HSVSlider.hue.slider.getName()) ||
                name.equals(HSVSlider.saturation.slider.getName()) ||
                name.equals(HSVSlider.value.slider.getName())) {
            System.out.println(name);
            Color c = HSVSlider.getHSVToRGB(null, HSVSlider.hue.getValue(), HSVSlider.saturation.getValue(), HSVSlider.value.getValue());
            red.slider.setValue(c.getRed());
            green.slider.setValue(c.getGreen());
            blue.slider.setValue(c.getBlue());
        }
        super.update(name);
    }

    public static MutableColor getColor() {
        color.setRGB(red.slider.getValue(), green.slider.getValue(),
                blue.slider.getValue());
        return color;
    }

    private static MutableColor getColor(int channelID, int amt) {
        color.setRGB(
                channelID == 0 ? amt : red.getValue(),
                channelID == 1 ? amt : green.getValue(),
                channelID == 2 ? amt : blue.getValue());
        return color;
    }

    private class ChannelSliderUI extends ColorSliderUI {
        private static final float[] FRACTIONS = new float[]{0.0f, 1f};
        private final MutableColor[] gradient;

        private ChannelSliderUI() {
            gradient = new MutableColor[] {new MutableColor(0,0,0), new MutableColor(0,0,0)};
        }

        @Override
        public void paintTrack(Graphics g) {
            gradient[0].set(getColor(channelID, 0));
            gradient[1].set(getColor(channelID, 255));

            LinearGradientPaint paint = new LinearGradientPaint(trackRect.x, 0, trackRect.width+trackRect.x, 0,
                    FRACTIONS, gradient);
            ((Graphics2D) g).setPaint(paint);
            super.paintTrack(g);
        }
    }
}
