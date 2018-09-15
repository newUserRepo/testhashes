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

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UPloadFile extends VerticalLayout implements Upload.Receiver, Upload.ProgressListener, Upload.SucceededListener {

    private final MyUI ui;
    private GridLogic gridLogic;
    private ProgressBar progressBar;
    private final Button btnInterrupt;
    private Path path;
    private TextField txtPath = new TextField();
    private TimeCount timeCount = new TimeCount();
    private HashesTypes hashesTypes;
    private Panel panel = new Panel();
    private Label labelHour = new Label();
    private Upload upload;
    private CssLayout row = new CssLayout();
    private final UploadService uploadService;


    public UPloadFile(final UploadService uploadFileClass) {
        this.uploadService = uploadFileClass;
        this.ui = uploadFileClass.getUi();
        this.progressBar = uploadFileClass.getProgressBar();
        this.btnInterrupt = uploadFileClass.getBtnInterrupt();
        this.gridLogic = uploadFileClass.gridLogic();

        setMargin(false);
        upload = new Upload(null, this);
        upload.setEnabled(false);
        upload.addProgressListener(this);
        upload.addSucceededListener(this);
        upload.setButtonCaption("...");

        final Component row_ = getRow();
        addComponents(row_);
    }

    public void setHashes(final HashesTypes hashesTypes) {
        this.hashesTypes = hashesTypes;
    }

    public void setEnabledButton(final boolean value) {
        upload.setEnabled(value);
    }

    private Component getRow() {
        row.setWidth("100%");
        txtPath.setPlaceholder("filename");
        txtPath.setWidth("100%");
        row.addComponents(upload, txtPath);
        row.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        row.addStyleName("css-space-2");
        return row;
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        final Float percent = (readBytes / (float) contentLength);
        progressBar.setValue(percent);
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        txtPath.setValue(Paths.get(filename).toAbsolutePath().toString());
        path = Paths.get(System.getProperty("java.io.tmpdir") + filename);
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(path));
        } catch (Exception ex) {
            Notification.show("Error al subir archivo", Notification.Type.ERROR_MESSAGE);
        }
        return bufferedOutputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        progressBar.setValue(0.0f);

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


}
