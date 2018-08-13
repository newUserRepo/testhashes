package com.example.demo.calculateHashes;

import com.example.demo.MyUI;
import com.example.demo.util.Hora;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringUI
public class UPloadService extends VerticalLayout implements Upload.Receiver,Upload.ProgressListener,Upload.SucceededListener {

    private final MyUI ui;
    private Path path;
    private TextField txtPath = new TextField();
    private ProgressBar progressBar;
    private String hash = "";
    private Panel panel = new Panel();
    private Label labelResul = new Label();
    private Label labelHour = new Label();
    private final Button btnInterrupt;
    private Upload upload;
    private CssLayout row = new CssLayout();


    public UPloadService(final MyUI ui , final ProgressBar bar, final Button btnInterrupt , boolean value) {
        this.ui = ui;
        this.progressBar = bar;
        this.btnInterrupt = btnInterrupt;
        setMargin(false);
        upload = new Upload(null , this );
        upload.setEnabled(value);
        upload.addProgressListener(this);
        upload.addSucceededListener(this);
        upload.setButtonCaption("...");

        final Component row_ = getRow();
        addComponents(row_);
    }

    private Component getRow() {
        row.setWidth("100%");
        txtPath.setPlaceholder("filename");
        txtPath.setWidth("100%");
        row.addComponents(upload,txtPath);
        row.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        row.addStyleName("css-space-2");
        return row;
    }



    @Override
    public void updateProgress(long readBytes, long contentLength) {
        final Float percent = (readBytes/(float)contentLength);
        progressBar.setValue(percent);
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        txtPath.setValue(Paths.get(filename).toAbsolutePath().toString());
        path = Paths.get(System.getProperty("java.io.tmpdir") + filename);
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(path));
        }catch (Exception ex) {
            Notification.show("Error al subir archivo", Notification.Type.ERROR_MESSAGE);
        }
        return bufferedOutputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        progressBar.setValue(0.0f);

        run(() -> new ProcessAsync().processAsyncTask(path, progressBar, labelResul, hash) , "com.example.demo.calculateHashes.Hash ready!!!");

    }

    private void run(final Runnable run , final String msg) {
        final ExecutorService ex = Executors.newSingleThreadExecutor();
        final CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(run,ex);
        completableFuture.whenCompleteAsync((message , error) -> {
           ui.access(() -> Notification.show( msg , Notification.Type.HUMANIZED_MESSAGE));
        });
    }


}
