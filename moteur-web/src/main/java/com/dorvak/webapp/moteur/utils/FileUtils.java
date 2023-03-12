package com.dorvak.webapp.moteur.utils;

import java.io.File;

public class FileUtils {

    public static void createDirectory(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists() && directory.mkdirs()) {
            LoggerUtils.info("Created new directory: %s", directoryName);
        }
    }

    public static boolean fileExists(String path, String file) {
        return new File(path, file).exists();
    }

    public static void generateFile(String path, String file) {
        File f = new File(path, file);
        if (!f.exists()) {
            try {
                if (f.createNewFile()) {
                    LoggerUtils.info("Created new file: %s", f.getPath());
                }
            } catch (Exception e) {
                LoggerUtils.severe("Error while creating file %s: %s", f.getPath(), e.getMessage());
            }
        }
    }
}
