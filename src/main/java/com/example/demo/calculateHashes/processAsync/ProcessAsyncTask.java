package com.example.demo.calculateHashes.processAsync;


import com.example.demo.util.event.MyEventBus;
import com.example.demo.util.event.MyEventBusEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

//how to apply pattern builder here!!!
//view OCP design patterns
public class ProcessAsyncTask implements ProcessAsyncInterface {

    final StringBuilder sbAppend = new StringBuilder();

    @Override
    public void calculateHashAsync(final ProcessAsync process) {
        readFileTxtAsync(process);
        readFileAsync(process);
        //background thread invoke access method
        process.getUI().access(() -> MyEventBus.post(process));
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

        CalculateHashes.calcAdler32(process);
        CalculateHashes.calcCrc32(process);
        CalculateHashes.calcSha(process);
        CalculateHashes.calcHaval256_4(process);

    }

}
