package com.example.demo.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TypesFields {

    public static final String IP_EXTERNAL = "http://checkip.amazonaws.com";
    public static final String MENU_CAPTION = "HashesDemo";
    public static final String PROCESS_HASHES = "hashes";
    public static final String ERROR = "https://www.google.com";
    public static final String PATTER_HOUR = "h:mm:ss a";
    public static Integer getLength() {
        return 0000;
    }

    public static String getHour() {
        return DateTimeFormatter.ofPattern(PATTER_HOUR)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());
    }
}
