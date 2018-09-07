package com.example.demo.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckIpExternal {

    public static CompletableFuture<String> checkIP() {
        final ExecutorService exec = Executors.newSingleThreadExecutor();
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> checkIPProcess(),exec);
        return future;
    }

    private static String checkIPProcess() {
        String resultIP = "";
        try {
            final URL url = new URL(TypesFields.IP_EXTERNAL);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            while((s = reader.readLine()) != null) {
                resultIP = resultIP.concat(s);
            }
        } catch (IOException e) {
            resultIP = "Error";
            e.printStackTrace();
        }
        return resultIP;
    }

}
