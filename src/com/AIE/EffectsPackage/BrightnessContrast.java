package com.AIE.EffectsPackage;

import com.AIE.WindowPackage.MainFrame;

import java.awt.image.BufferedImage;

public class BrightnessContrast extends Effect {

    public BrightnessContrast(MainFrame frame) {
        super("Brightness & Contrast", frame, 300, 285);
        finalizeComponents();
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        return null;
    }
}
