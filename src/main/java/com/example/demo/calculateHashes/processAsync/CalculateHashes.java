package com.example.demo.calculateHashes.processAsync;

import com.example.demo.calculateHashes.HashBuilder;
import com.example.demo.calculateHashes.createGridTransactions.GridTransactions;
import com.example.demo.util.ShowData;
import com.example.demo.util.TypesFields;
import com.vaadin.navigator.View;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

public class CalculateHashes implements View {


    public static void calcHaval256_4(final ProcessAsync process) {
        final List<String> haval256 = process.getHashes().getHaval256();
        for (int f = 0; f < haval256.size(); f++) {
            try (final BufferedInputStream in = new BufferedInputStream(Files.newInputStream(process.getPath()))) {
                process.getTimeCount().init();
                final StringBuilder sb = new StringBuilder();
                final AbstractChecksum haval = JacksumAPI.getChecksumInstance("haval_256_4");
                final byte[] bytes = new byte[1024];
                int bytesRead = 0;
                long sum = 0;
                long length = process.getPath().toFile().length();

                while ((bytesRead = in.read(bytes)) != -1) {
                    haval.update(bytes, 0, bytesRead);
                    sum += bytesRead;
                    final Float percent = (sum / (float) length);
                    process.getBar().setValue(percent);
                }
                process.getTimeCount().endTime();

                final HashBuilder hashBuilder = new HashBuilder();
                hashBuilder.setFileName(process.getPath().getFileName().toString())
                        .setHashType("HAVAL-256-4")
                        .setHashResult(haval.getFormattedValue())
                        .setSize(TypesFields.getSize(length))
                        .setHour(TypesFields.getHour())
                        .setTime(TypesFields.getTotalTime(process))
                        .build();
                GridTransactions.get().initData(hashBuilder);
                process.getGridLogic().initData();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

    }


    public static void calcCrc32(final ProcessAsync process) {
        final List<String> crc32List = process.getHashes().getCrc32();
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
                final String resultHash = Long.toHexString(crc32.getValue()).toUpperCase();

                final HashBuilder hashBuilder = new HashBuilder();
                hashBuilder.setFileName(process.getPath().getFileName().toString())
                        .setHashType("CRC32")
                        .setHashResult(resultHash)
                        .setHour(TypesFields.getHour())
                        .setTime(TypesFields.getTotalTime(process))
                        .setSize(TypesFields.getSize(length))
                        .build();
                /*
                Init the grid
                 */
                GridTransactions.get().initData(hashBuilder);
                process.getGridLogic().initData();
                ShowData.println("CRC32" + ": " + resultHash + "\n");
                String result2 = "\n" + resultHash;
                process.getRichTextAreaResult().setValue("CRC32" + ": " + result2 + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public static void calcAdler32(final ProcessAsync process) {
        final List<String> adel32List = process.getHashes().getAdler32();
        for (int f = 0; f < adel32List.size(); f++) {
            try (final BufferedInputStream in = new BufferedInputStream(Files.newInputStream(process.getPath()))) {
                final StringBuilder sb = new StringBuilder();
                final Adler32 adler32 = new Adler32();
                process.getTimeCount().init();
                int dataRead = 0;
                long sum = 0L;
                long length = process.getPath().toFile().length();
                final byte[] bytes = new byte[1024];
                while ((dataRead = in.read(bytes)) != -1) {
                    adler32.update(bytes, 0, dataRead);
                    sum += dataRead;
                    final Float percent = (sum / (float) length);
                    process.getBar().setValue(percent);
                }
                process.getTimeCount().endTime();
                final String resultHash = Long.toHexString(adler32.getValue()).toUpperCase();

                final HashBuilder hashBuilder = new HashBuilder();
                hashBuilder.setFileName(process.getPath().getFileName().toString())
                        .setHashType("ADLER32")
                        .setHashResult(resultHash)
                        .setHour(TypesFields.getHour())
                        .setTime(TypesFields.getTotalTime(process))
                        .setSize(TypesFields.getSize(length))
                        .build();
                /*
                Init the grid
                 */
                GridTransactions.get().initData(hashBuilder);
                process.getGridLogic().initData();

                ShowData.println("ADLER32" + ": " + resultHash + "\n");
                String result2 = "\n" + resultHash;
                process.getRichTextAreaResult().setValue("ADLER" + ": " + result2 + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void calcSha(final ProcessAsync process) {
        final List<String> md5AndShaList = process.getHashes().getMd5AndSha();
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
                final String resultHash = sb.toString().toUpperCase();
                final HashBuilder hashBuilder = new HashBuilder();
                hashBuilder.setFileName(process.getPath().getFileName().toString())
                        .setHashType(messageDigest.getAlgorithm())
                        .setHashResult(resultHash)
                        .setHour(TypesFields.getHour())
                        .setTime(TypesFields.getTotalTime(process))
                        .setSize(TypesFields.getSize(length))
                        .build();

                /*
                Init the grid
                 */
                GridTransactions.get().initData(hashBuilder);
                process.getGridLogic().initData();

                ShowData.println(messageDigest.getAlgorithm() + ": " + resultHash + "\n");
                final String resultString = messageDigest.getAlgorithm() + ": " + resultHash;
                process.getRichTextAreaResult().setValue(resultString);

            } catch (IOException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
    }
}

