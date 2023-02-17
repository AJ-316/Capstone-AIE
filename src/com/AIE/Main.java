package com.AIE;

import com.AIE.WindowPackage.MainFrame;

public class Main {

    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame(1280, 720);
        Canvas canvas = new Canvas();
        canvas.createNewImage(100, 100);
        canvas.setScale(5);
        mainFrame.addCanvas(canvas);
        mainFrame.createWindow();
    }

}
