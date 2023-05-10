package com.AIE;

import com.AIE.WindowPackage.MainFrame;
import com.AIE.WindowPackage.WindowMenu;

public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) ->
                AppLog.uncaughtError(thread.getName(), "Global Exception", throwable));
        WindowMenu.setRelativeLocation(new MainFrame(1280, 720));
    }
}
