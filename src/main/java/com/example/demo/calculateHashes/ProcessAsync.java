package com.example.demo.calculateHashes;

import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

//how to apply pattern builder here!!!
//view OCP design patterns
public class ProcessAsync {

    public ProcessAsync() {

    }

    public static void processAsyncTask(final Path path, final ProgressBar progressBar, final Label labelResult, final String hash) {
        final StringBuilder sb = new StringBuilder();
        if (path.toString().endsWith(".txt")) {
            String line = "";
            try(Stream<String> lines = Files.lines(path)){
                lines.map(p -> p.split("/")[0])
                        .forEach((p) -> sb.append(p + "\n"));
                labelResult.setValue(sb.toString());
            }catch (IOException ex) {

            }
        }

        try (final BufferedInputStream input = new BufferedInputStream(Files.newInputStream(path))) {
            final MessageDigest messageDigest = MessageDigest.getInstance(hash);
            int dataRead = 0;
            long sum = 0L;
            long length = path.toFile().length();
            final byte[] bytes = new byte[1024];
            while ((dataRead = input.read(bytes)) != -1) {
                messageDigest.update(bytes, 0, dataRead);
                sum += dataRead;
                final Float percent = (sum / (float) length);
                progressBar.setValue(percent);
            }
            final byte[] bytesDigest = messageDigest.digest();
            for (int f = 0; f < bytesDigest.length; f++) {
                sb.append(Integer.toString((bytesDigest[f] & 255) + 256, 16).substring(1));
            }
            final String resultHash = sb.toString().toUpperCase();
            //labelResult.setValue("");
            labelResult.setValue(messageDigest.getAlgorithm() + ": " + resultHash + "\n");
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
}
