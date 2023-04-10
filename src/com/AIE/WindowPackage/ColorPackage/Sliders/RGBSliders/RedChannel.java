package com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders;

import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.ColorSlider;
import com.AIE.WindowPackage.ColorPackage.Sliders.UI.RGBChannelUI;

public class RedChannel extends ColorSlider {

    public RedChannel() {
        super("red", "R:", 255, 255, new RGBChannelUI(0));
    }

    @Override
    public void updateColor() {
        ColorPalette.update(RGB.getColor(), ELEMENT_NAME);
    }

    public void updateElement(MutableColor color, String invoker) {
        if(invoker.equals(ELEMENT_NAME)) {
            repaint();
            updateInputValue();
            return;
        }
        setValue(color.getRed());
    }
}
