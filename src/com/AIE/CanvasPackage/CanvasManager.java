package com.AIE.CanvasPackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CanvasManager extends JPanel {

    private static ArrayList<com.AIE.CanvasPackage.Canvas> canvasList;
    public static int currentCanvasIndex;

    public CanvasManager(int width, int height) {
        super(null);
        setPreferredSize(new Dimension(width, height));
        canvasList = new ArrayList<>();
    }

    public void addCanvas(com.AIE.CanvasPackage.Canvas... canvasesToAdd) {

        canvasList.addAll(Arrays.asList(canvasesToAdd));

        currentCanvasIndex = canvasesToAdd.length-1;

        setCurrentCanvas(currentCanvasIndex);
    }

    public void setCurrentCanvas(int i) {

        remove(canvasList.get(currentCanvasIndex));
        add(canvasList.get(i));
        currentCanvasIndex = i;
        repaint();
    }

    public static Canvas getCurrentCanvas() {
        if(canvasList.size() == 0) return null;

        return canvasList.get(currentCanvasIndex);
    }

}
