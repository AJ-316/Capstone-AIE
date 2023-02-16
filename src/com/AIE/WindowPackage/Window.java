package com.AIE.WindowPackage;

import com.AIE.Canvas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    private static ArrayList<Canvas> canvasList;
    public static int currentCanvasIndex;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public Window(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        canvasList = new ArrayList<>();

        this.setSize(width, height);
        this.setLayout(null);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0x303031));
    }

    public void addCanvas(Canvas... canvasesToAdd) {

        for(Canvas canvas : canvasesToAdd) {
            canvasList.add(canvas);
            this.add(canvas);
        }
        currentCanvasIndex = canvasesToAdd.length-1;
    }

    public static void setCurrentCanvas(int i) {
        currentCanvasIndex = i;
    }

    public static Canvas getCurrentCanvas() {
        return canvasList.get(currentCanvasIndex);
    }

    public void createWindow() {
        this.setVisible(true);
    }

}
