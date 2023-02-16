package com.AIE;

import com.AIE.WindowPackage.MainFrame;

public class Main {

    public static void main(String[] args) {

        MainFrame.init(1280, 720);
        Canvas canvas = new Canvas();
        canvas.createNewImage(100, 100);
        canvas.setScale(5);
        MainFrame.instance.addCanvas(canvas);
        MainFrame.instance.createWindow();
    }

}
