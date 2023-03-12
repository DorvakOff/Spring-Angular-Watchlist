package com.dorvak.webapp.moteur.utils;

import java.util.logging.Logger;

public class LoggerUtils {

    public static Logger getLogger(int depth) {
        String clazz = Thread.currentThread().getStackTrace()[depth].getClassName();
        return Logger.getLogger(clazz);
    }

    public static void info(String s, Object... args) {
        getLogger(3).info(String.format(s, args));
    }

    public static void severe(String s, Object... args) {
        getLogger(3).severe(String.format(s, args));
    }
}
