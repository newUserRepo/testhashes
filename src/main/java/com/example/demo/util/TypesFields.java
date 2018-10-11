package com.example.demo.util;

import com.example.demo.calculateHashes.processAsync.ProcessAsync;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class TypesFields {

    public static final String HASHES[] = {"ADLER32", "CRC32",   "MD5",
                                           "SHA1"   , "SHA-256", "SHA-384",
                                           "SHA-512",  "haval_256_4"};
    private static final String[] UNITS = new String[] { "bytes", "kB", "MB", "GB", "TB" };
    private static final String  DECIMAL_PATTERN = "#,##0.#";

    public static final String IP_EXTERNAL = "http://checkip.amazonaws.com";
    public static final String MENU_CAPTION = "HashesDemo";
    public static final String PROCESS_HASHES = "hashes";
    public static final String PATH = "path";
    public static final String CUSTOMER = "customer";
    public static final String ERROR = "https://www.google.com";
    public static final String PATTER_HOUR = "h:mm:ss a";

    public static boolean onlySHA(final String  onlySHA) {
        return !onlySHA.contains("CRC32") && !onlySHA.contains("ADLER32") && !onlySHA.contains("haval_256_4");
    }
    public static String getHour() {
        return DateTimeFormatter.ofPattern(PATTER_HOUR)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());
    }

    public static String getTotalTime(final ProcessAsync processAsync) {
        return processAsync.getTimeCount().getTimeSec() +" sec "+ processAsync.getTimeCount().getTimeMillis() + " ms";
    }

    public static String getSize(long size) {
        if(size <= 0) return "0";
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat(DECIMAL_PATTERN).format(size/Math.pow(1024, digitGroups)) + " " + UNITS[digitGroups];
    }
}
