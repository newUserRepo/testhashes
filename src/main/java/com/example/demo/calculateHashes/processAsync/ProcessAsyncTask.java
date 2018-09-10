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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//how to apply pattern builder here!!!
//view OCP design patterns
public class ProcessAsyncTask implements ProcessAsyncInterface {

    @Override
    public void calculateHashAsync(ProcessAsync process) {
        readFileTxtAsync(process);
        readFileAsync(process);
    }

    private void readFileTxtAsync(final ProcessAsync process) {
        final StringBuilder sb = new StringBuilder();
        if (process.getPath().toString().endsWith(".txt")) {
            try (Stream<String> lines = Files.lines(process.getPath())) {
                lines.map(param -> param.split("/")[0])
                        .forEach(f -> sb.append(f + "\n"));
                process.getRichTextAreaResult().setValue(sb.toString());

            } catch (IOException ex) {

            }
        }
    }

    private void readFileAsync(final ProcessAsync process) {
        for (int f = 0; f <= process.getHashes().size(); f++) {
            try (final BufferedInputStream input = new BufferedInputStream(Files.newInputStream(process.getPath()))) {
                final StringBuilder sb = new StringBuilder();
                process.getTimeCount().init();
                final MessageDigest messageDigest = MessageDigest.getInstance(process.getHashes().get(f));
                int dataRead = 0;
                long sum = 0L;
                long length = process.getPath().toFile().length();
                final byte[] bytes = new byte[1024];
                while ((dataRead = input.read(bytes)) != -1) {
                    messageDigest.update(bytes, 0, dataRead);
                    sum += dataRead;
                    final Float percent = (sum / (float) length);
                    process.getBar().setValue(percent);
                }
                final byte[] bytesDigest = messageDigest.digest();

                //FIXME add streams or parallelStreams
                //parallel task here, for example
                IntStream.range(0, bytesDigest.length)
                        .boxed()
                        .forEach(c -> sb.append(Integer.toString((bytesDigest[c] & 255) + 256, 16).substring(1)));

                process.getTimeCount().endTime();
                final String totalTime = TypesFields.getTotalTime(process.getTimeCount().getTimeSec(), process.getTimeCount().getTimeMillis());
                ShowData.println("Tiempo total " + totalTime);
                final String resultHash = sb.toString().toUpperCase();

                final HashBuilder hashBuilder = new HashBuilder();
                hashBuilder.setFileName(process.getPath().getFileName().toString())
                        .setHashType(messageDigest.getAlgorithm())
                        .setHashResult(resultHash)
                        .setHour(TypesFields.getHour())
                        .setTime(totalTime)
                        .setLength(TypesFields.getLength())
                        .build();

                /*
                Init the grid
                 */
                GridTransactions.get().initData(hashBuilder);
                process.getGridLogic().initData();

                ShowData.println(messageDigest.getAlgorithm() + ": " + resultHash + "\n");
                process.getRichTextAreaResult().setValue(messageDigest.getAlgorithm() + ": " + resultHash + "\n");
            } catch (IOException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
    }

}
