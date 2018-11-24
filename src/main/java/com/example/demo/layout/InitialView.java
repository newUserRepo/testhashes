package com.example.demo.layout;

import com.example.demo.MyUI;
import com.example.demo.calculateHashes.processAsync.ProcessAsync;
import com.example.demo.calculateHashes.processAsync.ProcessAsyncTask;
import com.example.demo.util.ShowData;
import com.example.demo.util.event.MyEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.atmosphere.config.service.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.easyuploads.MultiFileUpload;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringComponent
@UIScope
public class InitialView extends VerticalLayout implements View {

    private static final String RESOURCES = "src/main/resources/";
    private MyUI ui;
    private Label label = new Label();
    private Path path;

    @PostConstruct
    public void initialView() {
        //this.ui = ui;

        label.addStyleName("bold");
        label.addStyleName("h2");
        label.setContentMode(ContentMode.HTML);
        label.setValue(getPhrase());

        final MultiFileUpload upload = upload();

        addComponents(label, upload);
        setExpandRatio(upload,1);
        MyEventBus.register(this);
        processFileAndUploadToS3();
    }

    private MultiFileUpload upload() {
        final MultiFileUpload multiFileUpload = new MultiFileUpload() {
            @Override
            protected void handleFile(File tmpFile, String fileName, String mimeType, long length) {
                try {
                    Files.write(Paths.get(RESOURCES + fileName)
                                ,Files.readAllBytes(tmpFile.toPath())
                                ,StandardOpenOption.CREATE);
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        return multiFileUpload;
    }

    private void processFileAndUploadToS3() {
        final ProcessAsync processAsync = new ProcessAsync.Builder()
                .setPath(path)
                .build();
        final ProcessAsyncTask task = new ProcessAsyncTask();
        final ExecutorService es = Executors.newSingleThreadExecutor();
        final CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            task.calculateHashAsync(processAsync);
        },es);
        cf.whenCompleteAsync((msg,error) -> {

        });
    }

    @Subscribe
    public void update(final String count) {
        ShowData.println("HashProcesados: " + count);
        addComponent(new Label("HashProcesados: " + count));
    }

    private String getPhrase() {
        final StringBuilder sb = new StringBuilder();
              sb.append("<font size = \"5\" color = \"black\"> fucking")
                .append("<font size = \"5\" color = \"red\"  > initial")
                .append("<font size = \"5\" color = \"blue\" > view");
        return sb.toString();
    }

}
