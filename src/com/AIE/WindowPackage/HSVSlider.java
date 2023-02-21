package com.AIE.WindowPackage;

import java.awt.*;

public class HSVSlider extends ColorSlider {

    private float max, min;
    public static HSVSlider hue, saturation, value;

    public static HSVSlider create(int channelID) {
        switch (channelID) {
            case 0 -> {
                hue = new HSVSlider(360, new HueSliderUI());
                hue.slider.setName("hue");
                return hue;
            }
            case 1 -> {
                saturation = new HSVSlider(100, new SaturationSliderUI());
                saturation.slider.setName("saturation");
                return saturation;
            }
            case 2 -> {
                value = new HSVSlider(100, new ValueSliderUI());
                value.slider.setName("value");
                return value;
            }
        }
        return null;
    }

    private HSVSlider(int max, ColorSliderUI colorSliderUI) {
        init(colorSliderUI, max);
    }

    @Override
    protected void update(String name) {
        if(name.equals(ColorChannelSlider.red.slider.getName()) ||
                name.equals(ColorChannelSlider.green.slider.getName()) ||
                name.equals(ColorChannelSlider.blue.slider.getName())) {
            System.out.println(name);
            hue.slider.setValue(calculateHue());
            saturation.slider.setValue(calculateSaturation());
            value.slider.setValue(calculateValue());
        }
        super.update(name);
    }

    private int calculateValue() {
        Color rgb = ColorChannelSlider.getColor();
        float r = rgb.getRed()/255f;
        float g = rgb.getGreen()/255f;
        float b = rgb.getBlue()/255f;
        max = Math.max(r, Math.max(g, b));
        min = Math.min(r, Math.min(g, b));
        return (int) (max*100);
    }

    private int calculateSaturation() {
        Color rgb = ColorChannelSlider.getColor();
        float r = rgb.getRed()/255f;
        float g = rgb.getGreen()/255f;
        float b = rgb.getBlue()/255f;
        max = Math.max(r, Math.max(g, b));
        min = Math.min(r, Math.min(g, b));
        if(max == 0)
            return 0;
        return (int) ((max-min)/max*100);
    }

    private int calculateHue() {
        Color rgb = ColorChannelSlider.getColor();
        float r = rgb.getRed()/255f;
        float g = rgb.getGreen()/255f;
        float b = rgb.getBlue()/255f;
        max = Math.max(r, Math.max(g, b));
        min = Math.min(r, Math.min(g, b));

        float diff = max-min;
        int resultHue = 0;

        if(diff == 0)
            return 0;

        if(max == r) {
            resultHue = (int) ((((g - b) / diff) % 6) * 60);
        } else if(max == g) {
            resultHue = (int) (((b-r)/diff + 2) * 60);
        } else if(max == b) {
            resultHue = (int) (((r-g)/diff + 4) * 60);
        }

        return resultHue < 0 ? resultHue+360 : resultHue;
    }

    public static Color getHSVToRGB(MutableColor color, int hue, int saturation, int value) {
        if(color == null) {
            color = new MutableColor(0,0,0);
        }

        float c = value/100f * saturation/100f;
        float x = c * (1 - Math.abs((hue/60f) % 2 - 1));
        float m = value/100f - c;

        if(hue <=  60) convertToRGB(c, x, 0, m, color); else
        if(hue <= 120) convertToRGB(x, c, 0, m, color); else
        if(hue <= 180) convertToRGB(0, c, x, m, color); else
        if(hue <= 240) convertToRGB(0, x, c, m, color); else
        if(hue <= 300) convertToRGB(x, 0, c, m, color); else
        if(hue <= 360) convertToRGB(c, 0, x, m, color);

        return color;
    }

    private static void convertToRGB(float r, float g, float b, float m, MutableColor color) {
        color.setRGB((int)((r+m)*255), (int)((g+m)*255), (int)((b+m)*255));
    }

    public static class HueSliderUI extends ColorSliderUI {

        @Override
        public void paintTrack(Graphics g) {
            LinearGradientPaint paint = new LinearGradientPaint(trackRect.x, 0, trackRect.width+trackRect.x, 0,
                    new float[]{0,60/360f,120/360f,180/360f,240/360f,300/360f,1f},
                    new Color[]{Color.red,Color.yellow,Color.green,Color.cyan,Color.blue,Color.magenta,Color.red});

            ((Graphics2D) g).setPaint(paint);
            super.paintTrack(g);
        }
    }

    public static class SaturationSliderUI extends ColorSliderUI {
        private static final MutableColor gradientEnd = new MutableColor(0,0,0);
        @Override
        public void paintTrack(Graphics g) {
            float fraction = value.getValue()/100f;
            LinearGradientPaint paint = new LinearGradientPaint(trackRect.x, 0, trackRect.width+trackRect.x, 0,
                    new float[]{0.0f, 1f}, new Color[]{new Color(fraction, fraction, fraction),
                    getHSVToRGB(gradientEnd, hue.getValue(), 100, value.getValue())});
            // hue to rgb value for saturation end gradient

            ((Graphics2D) g).setPaint(paint);
            super.paintTrack(g);
        }
    }

    public static class ValueSliderUI extends ColorSliderUI {
        @Override
        public void paintTrack(Graphics g) {
            LinearGradientPaint paint = new LinearGradientPaint(trackRect.x, 0, trackRect.width+trackRect.x, 0,
                    new float[]{0.0f, 1f}, new Color[]{Color.black,ColorChannelSlider.getColor()});

            ((Graphics2D) g).setPaint(paint);
            super.paintTrack(g);
        }
    }

}
