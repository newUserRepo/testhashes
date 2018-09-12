package com.example.demo.calculateHashes.processAsync;

import com.example.demo.calculateHashes.HashBuilder;
import com.example.demo.calculateHashes.createGridTransactions.GridTransactions;
import com.example.demo.util.ShowData;
import com.example.demo.util.TypesFields;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.CRC32;

//how to apply pattern builder here!!!
//view OCP design patterns
public class ProcessAsyncTask implements ProcessAsyncInterface {

    final StringBuilder sbAppend = new StringBuilder();

    @Override
    public void calculateHashAsync(final ProcessAsync process) {
        readFileTxtAsync(process);
        readFileAsync(process);
    }

    private void readFileTxtAsync(final ProcessAsync process) {
        final StringBuilder sb = new StringBuilder();
        if (process.getPath().toString().endsWith(".txt")) {
            try (Stream<String> lines = Files.lines(process.getPath())) {
                lines.map(param -> param.split("/")[0])
                        .forEach(f -> sb.append(f + "\n"));

                sbAppend.append(sb);
                process.getRichTextAreaResult().setValue(sbAppend.toString() +"\n");

            } catch (IOException ex) {

            }
        }
    }

    private void readFileAsync(final ProcessAsync process) {

        CalculateHashes.calcCrc32(process);
        CalculateHashes.calcAdler32(process);
        CalculateHashes.calcSha(process);
        CalculateHashes.calcHaval256_4(process);

    }

}
