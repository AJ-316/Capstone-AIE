package com.AIE;

import com.AIE.WindowPackage.MainFrame;

public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(1280, 720);
        Canvas canvas = new Canvas();
        canvas.createNewImage(100, 100, 0);
        MainFrame.CANVAS_MANAGER.addCanvas(canvas);
        mainFrame.createWindow();
    }


}
