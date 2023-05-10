package com.AIE.WindowPackage.ColorPackage.Sliders.RGBSliders;

import com.AIE.WindowPackage.ColorPackage.ColorPaletteWindow;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.ColorPackage.Sliders.ColorSlider;
import com.AIE.WindowPackage.ColorPackage.Sliders.UI.RGBChannelUI;

public class GreenChannel extends ColorSlider {

    public GreenChannel() {
        super("green", "G:", 255, 0, new RGBChannelUI(1));
    }

    public void updateColor() {
        ColorPaletteWindow.update(RGBA.getColor(), ELEMENT_NAME);
    }

    public void updateElement(MutableColor color, String invoker) {
        if(invoker.equals(ELEMENT_NAME)) {
            repaint();
            updateInputValue();
            return;
        }
        setValue(color.getGreen());
    }
}
