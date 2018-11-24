package com.example.demo.calculateHashes;

import com.example.demo.MyUI;
import com.example.demo.calculateHashes.createGridTransactions.GridLogic;
import com.example.demo.calculateHashes.processAsync.ProcessAsync;
import com.example.demo.calculateHashes.processAsync.ProcessAsyncTask;
import com.example.demo.calculateHashes.upload.UploadService;
import com.example.demo.util.TimeCount;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.easyuploads.MultiFileUpload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UPloadFile extends MultiFileUpload {

    private final MyUI ui;
    private GridLogic gridLogic;
    private ProgressBar progressBar;
    private final Button btnInterrupt;
    private Path path;
    private TimeCount timeCount = new TimeCount();
    private HashesTypes hashesTypes;
    private final UploadService uploadService;


    public UPloadFile(final UploadService uploadFileClass) {
        this.uploadService = uploadFileClass;
        this.ui = uploadFileClass.getUi();
        this.progressBar = uploadFileClass.getProgressBar();
        this.btnInterrupt = uploadFileClass.getBtnInterrupt();
        this.gridLogic = uploadFileClass.gridLogic();
        setWidth("0px");
        addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    }

    public void setHashes(final HashesTypes hashesTypes) {
        this.hashesTypes = hashesTypes;
    }

    public void setEnabledButton(final boolean value) {
        setEnabled(value);
        focus();
    }

    public void createBuilder() {
        final ProcessAsync processAsync = new ProcessAsync.Builder()
                .setUI(ui)
                .setPath(path)
                .setProgressBar(uploadService.getProgressBar())
                .setRichTextAreaResult(uploadService.getRichTextArea())
                .setHashes(hashesTypes)
                .setTimeCount(timeCount)
                .setGridLogic(gridLogic)
                .build();
        final ProcessAsyncTask processAsyncTask = new ProcessAsyncTask();

        run(() -> processAsyncTask.calculateHashAsync(processAsync), "Hash ready!!!");
    }


    private void run(final Runnable run, final String msg) {
        final ExecutorService ex = Executors.newSingleThreadExecutor();
        final CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(run, ex);
        completableFuture.whenCompleteAsync((message, error) -> {
            ui.access(() -> Notification.show(msg, Notification.Type.HUMANIZED_MESSAGE));
        });
    }


    @Override
    protected void handleFile(File tmpFile, String fileName, String mimeType, long length) {
        final String resources = "src/main/resources/" + fileName;
        try {

            path = Files.write(Paths.get(resources), //write file to resources
                    Files.readAllBytes(tmpFile.toPath()), // read file from
                    StandardOpenOption.CREATE);          //option

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        createBuilder();
    }
}
