package com.AIE;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CanvasManager extends JPanel {

    private static ArrayList<Canvas> canvasList;
    public static int currentCanvasIndex;

    public CanvasManager() {
        super(null);
        canvasList = new ArrayList<>();
    }

    public void addCanvas(Canvas... canvasesToAdd) {
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
        return canvasList.get(currentCanvasIndex);
    }

}
