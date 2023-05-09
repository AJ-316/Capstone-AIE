package com.AIE;

import com.AIE.WindowPackage.MainFrame;

public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) ->
                AppLog.uncaughtError(thread.getName(), "Global Exception", throwable));
        new MainFrame(1280, 720);
    }
}
