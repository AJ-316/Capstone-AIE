package com.AIE.WindowPackage.ColorPackage.Sliders.HSVSliders;

import com.AIE.WindowPackage.ColorPackage.ColorPaletteWindow;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.ColorSlider;
import com.AIE.WindowPackage.ColorPackage.Sliders.UI.SaturationChannelUI;

public class SaturationChannel extends ColorSlider {
    public SaturationChannel() {
        super("hsv", "S:", 100, 100, new SaturationChannelUI());
    }

    @Override
    public void updateColor() {
        ColorPaletteWindow.update(HSV.toRGB(), ELEMENT_NAME);
    }

    @Override
    public void updateElement(MutableColor color, String invoker) {
        if(invoker.equals(ELEMENT_NAME)) {
            repaint();
            updateInputValue();
            return;
        }
        setValue(color.toSaturation());
    }
}
