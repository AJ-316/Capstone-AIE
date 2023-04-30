package com.AIE.CanvasPackage;

import com.AIE.ImageLoader;

import java.awt.image.BufferedImage;

public class CanvasBackground {

    public static final BufferedImage img = ImageLoader.loadImage("canvas/background");
    private final int bkgWidth;
    private final int bkgHeight;

    public CanvasBackground() {
        bkgWidth = img.getWidth();
        bkgHeight = img.getHeight();
    }

    public BufferedImage[][] getTiledImages(int canvasWidth, int canvasHeight) {
        int lastTilesX = (int) Math.ceil((double) canvasWidth /  bkgWidth);
        int lastTilesY = (int) Math.ceil((double) canvasHeight /  bkgHeight);

        int tileNewWidth = bkgWidth - (lastTilesX*bkgWidth - canvasWidth);
        int tileNewHeight = bkgHeight - (lastTilesY*bkgHeight - canvasHeight);

        BufferedImage[][] bkgTiles;
//        if(lastTilesX == 1 && lastTilesY == 1) {
            bkgTiles = new BufferedImage[][] { {getCroppedBkg(tileNewWidth, tileNewHeight)} };
            return bkgTiles;
//        }

//        bkgTiles = new BufferedImage[lastTilesX][lastTilesY];
//        for(int x = 0; x < lastTilesX; x++) {
//            for (int y = 0; y < lastTilesY; y++) {
//                bkgTiles[x][y] = bkg;
//            }
//        }
//        return bkgTiles;
    }

    private BufferedImage getCroppedBkg(int width, int height) {
        return img.getSubimage(0, 0, width, height);
    }
}
