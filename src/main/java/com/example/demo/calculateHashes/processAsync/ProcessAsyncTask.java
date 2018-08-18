package com.example.demo.calculateHashes.processAsync;

import com.example.demo.util.ShowData;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

//how to apply pattern builder here!!!
//view OCP design patterns
public class ProcessAsyncTask implements ProcessAsyncInterface {

    private StringBuilder SB = new StringBuilder();

    @Override
    public void calculateHashAsync(ProcessAsync process) {
        readFileTxtAsync(process);
        readFileAsync(process);
    }

    private void readFileTxtAsync(final ProcessAsync process ) {
        if(process.getPath().toString().endsWith(".txt")) {
            try(Stream<String> lines = Files.lines(process.getPath())) {
                lines.map(param -> param.split("/")[0])
                        .forEach(f -> SB.append(f + "\n"));
                process.getRichTextAreaResult().setValue(SB.toString());

            }catch (IOException ex) {

            }
        }
    }
    private void readFileAsync(final ProcessAsync process) {
        for(int f=0; f<= process.getHashes().size(); f++) {
            try (final BufferedInputStream input = new BufferedInputStream(Files.newInputStream(process.getPath()))) {
                final MessageDigest messageDigest = MessageDigest.getInstance(process.getHashes().get(f));
                int dataRead = 0;
                long sum = 0L;
                long length = process.getPath().toFile().length();
                final byte[] bytes = new byte[1024];
                while ((dataRead = input.read(bytes)) != -1) {
                    messageDigest.update(bytes, 0, dataRead);
                    sum += dataRead;
                    final Float percent = (sum / (float) length);
                    process.getProgressBar().setValue(percent);
                }
                final byte[] bytesDigest = messageDigest.digest();
                for (int c = 0; c < bytesDigest.length; c++) {
                    SB.append(Integer.toString((bytesDigest[c] & 255) + 256, 16).substring(1));
                }
                final String resultHash = SB.toString().toUpperCase();
                //labelResult.setValue("");
                ShowData.println(messageDigest.getAlgorithm() + ": " + resultHash + "\n");
                process.getRichTextAreaResult().setValue(messageDigest.getAlgorithm() + ": " + resultHash + "\n");
            } catch (IOException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
    }

}
