package com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders;

import com.AIE.WindowPackage.ColorPackage.ColorPalette;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.ColorSlider;
import com.AIE.WindowPackage.ColorPackage.Sliders.UI.RGBChannelUI;

public class AlphaChannel extends ColorSlider {

    public AlphaChannel() {
        super("alpha", "A:", 255, 255, new RGBChannelUI.AlphaChannelUI());
    }

    @Override
    public void updateColor() {
        ColorPalette.update(RGBA.getColor(), ELEMENT_NAME);
    }

    public void updateElement(MutableColor color, String invoker) {
        if(invoker.equals(ELEMENT_NAME)) {
            repaint();
            updateInputValue();
        } else if(invoker.equals("brush")) {
            setValue(color.getAlpha());
        }
    }
}
