package com.AIE;

import com.AIE.WindowPackage.MainFrame;

public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(1280, 720, 1000, 700);
        ImageLoader.init(mainFrame);

        mainFrame.createWindow();
    }


}
