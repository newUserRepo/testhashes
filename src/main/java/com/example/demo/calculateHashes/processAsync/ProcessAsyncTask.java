package com.example.demo.calculateHashes.processAsync;

import com.example.demo.calculateHashes.HashBuilder;
import com.example.demo.calculateHashes.createGridTransactions.GridTransactions;
import com.example.demo.util.ShowData;
import com.example.demo.util.TypesFields;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.CRC32;

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
        final List<String> crc32List = process.getHashes().getCrc32();
        final List<String> md5AndShaList = process.getHashes().getMd5AndSha();

        for (int f = 0; f < crc32List.size(); f++) {
            try (final BufferedInputStream in = new BufferedInputStream(Files.newInputStream(process.getPath()))) {
                final StringBuilder sb = new StringBuilder();
                final CRC32 crc32 = new CRC32();
                process.getTimeCount().init();
                int dataRead = 0;
                long sum = 0L;
                long length = process.getPath().toFile().length();
                final byte[] bytes = new byte[1024];
                while ((dataRead = in.read(bytes)) != -1) {
                    crc32.update(bytes, 0, dataRead);
                    sum += dataRead;
                    final Float percent = (sum / (float) length);
                    process.getBar().setValue(percent);
                }
                process.getTimeCount().endTime();
                final String totalTime = TypesFields.getTotalTime(process.getTimeCount().getTimeSec(), process.getTimeCount().getTimeMillis());
                ShowData.println("Tiempo total " + totalTime);

                final String resultHash = Long.toHexString(crc32.getValue()).toUpperCase();

                final HashBuilder hashBuilder = new HashBuilder();
                hashBuilder.setFileName(process.getPath().getFileName().toString())
                        .setHashType("CRC32")
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

                ShowData.println("CRC32" + ": " + resultHash + "\n");
                process.getRichTextAreaResult().setValue("CRC32" + ": " + resultHash + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int f = 0; f < md5AndShaList.size(); f++) {
            try (final BufferedInputStream input = new BufferedInputStream(Files.newInputStream(process.getPath()))) {
                final StringBuilder sb = new StringBuilder();
                process.getTimeCount().init();
                final MessageDigest messageDigest = MessageDigest.getInstance(md5AndShaList.get(f));
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
