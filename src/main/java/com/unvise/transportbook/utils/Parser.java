package com.unvise.transportbook.utils;

public class Parser {

    public static Boolean parseByAvailability(String value) {
        if (value.trim().equalsIgnoreCase("yes") || value.trim().equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } return Boolean.FALSE;
    }

}
