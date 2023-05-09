package com.AIE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLog {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void error(String name, String error, Throwable throwable) {
        logger.error("Error occurred[ " + name +" ]: " + error, throwable);
    }

    public static void uncaughtError(String name, String error, Throwable throwable) {
        logger.error("Uncaught exception[ " + name +" ]: " + error, throwable);
    }

}
