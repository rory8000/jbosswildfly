package com.rory.demo.util;

public class StringUtils {

    public static String toUpperCase(String name) {
        if (name == null) {
            return null;
        }
        return name.trim().toUpperCase();
    }
}
