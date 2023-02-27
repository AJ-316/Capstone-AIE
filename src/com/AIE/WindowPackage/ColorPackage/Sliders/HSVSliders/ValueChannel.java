package com.AIE.WindowPackage.ColorPackage.Sliders.HSVSliders;

import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.CSlider;
import com.AIE.WindowPackage.ColorPackage.Sliders.UI.ValueChannelUI;

public class ValueChannel extends CSlider {
    public ValueChannel() {
        super("hsv", "V:", 100, 100, new ValueChannelUI());
    }

    @Override
    public void updateColor() {
        ColorPalette.update(HSV.toRGB(), ELEMENT_NAME);
    }

    @Override
    public void updateElement(MutableColor color, String invoker) {
        if(invoker.equals(ELEMENT_NAME)) {
            repaint();
            updateInputValue();
            return;
        }
        setValue(color.toValue());
    }
}