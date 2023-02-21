package com.AIE;

import com.AIE.WindowPackage.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(1280, 720);
        Canvas canvas = new Canvas();
        canvas.createNewImage(100, 100);
        MainFrame.CANVAS_MANAGER.addCanvas(canvas);
        JScrollBar js = new JScrollBar(JScrollBar.HORIZONTAL, 100, 100, 0, 500);
        mainFrame.add(js, "South");
        mainFrame.createWindow();
    }

}
