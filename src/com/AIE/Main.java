package com.AIE;

import com.AIE.WindowPackage.Window;

public class Main {

    public static void main(String[] args) {
        Window window = new Window(1280, 720);
        Canvas canvas = new Canvas();
        window.addCanvas(canvas);
        canvas.createNewImage(100, 100);
        window.createWindow();
    }

}
