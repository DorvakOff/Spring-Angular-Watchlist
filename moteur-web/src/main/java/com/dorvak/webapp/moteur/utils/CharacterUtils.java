package com.dorvak.webapp.moteur.utils;

public class CharacterUtils {

    private CharacterUtils() {
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isEmptyTrim(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isNotEmptyTrim(String s) {
        return !isEmptyTrim(s);
    }

    public static String capitalize(String text) {
        if (isEmptyTrim(text)) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}