package com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders;

import com.AIE.WindowPackage.ColorPackage.ColorPaletteWindow;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.ColorSlider;
import com.AIE.WindowPackage.ColorPackage.Sliders.UI.RGBChannelUI;

public class AlphaChannel extends ColorSlider {

    public AlphaChannel() {
        super("alpha", "A:", 255, 255, new RGBChannelUI.AlphaChannelUI());
    }

    @Override
    public void updateColor() {
        ColorPaletteWindow.update(RGBA.getColor(), ELEMENT_NAME);
    }

    public void updateElement(MutableColor color, String invoker) {
        if(invoker.equals(ELEMENT_NAME)) {
            repaint();
            updateInputValue();
            return;
        }

        setValue(color.getAlpha());
    }
}
