package com.AIE.EffectsPackage;

import com.AIE.HeadLabel;
import com.AIE.WindowPackage.ColorPackage.MutableColor;
import com.AIE.WindowPackage.MainFrame;

import java.awt.image.BufferedImage;

public class InvertColorEffect extends Effect {

    private final MutableColor color;

    public InvertColorEffect(MainFrame frame) {
        super("InvertColor", frame, 200, 110);

        color = new MutableColor(0);

        finalizeComponents(new HeadLabel("Confirm", 100));
        previewEffect();
    }

    @Override
    protected BufferedImage applyEffect(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int progressVal = 0;
        int totalPixels = width * height;
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(checkForceStop())
                    return null;

                int pixel = source.getRGB(x,y);

                color.setRGBA(pixel);
                color.setRGB(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
                sepiaImage.setRGB(x, y, color.getRGB());

                progressEffect(progressVal++, totalPixels);
            }
        }
        return sepiaImage;
    }
}
