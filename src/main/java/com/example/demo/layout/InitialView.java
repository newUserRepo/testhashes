package com.example.demo.layout;

import com.example.demo.MyUI;
import com.example.demo.util.ShowData;
import com.example.demo.util.event.MyEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.easyuploads.MultiFileUpload;

import java.io.File;

public class InitialView extends VerticalLayout implements View {

    private MyUI ui;
    private Label label = new Label();

    public InitialView() {
        //this.ui = ui;

        label.addStyleName("bold");
        label.addStyleName("h2");
        label.setContentMode(ContentMode.HTML);
        label.setValue(getPhrase());

        final MultiFileUpload upload = upload();

        addComponents(label, upload);
        setExpandRatio(upload,1);
        MyEventBus.register(this);
    }

    private MultiFileUpload upload() {
        final MultiFileUpload multiFileUpload = new MultiFileUpload() {
            @Override
            protected void handleFile(File tmpFile, String fileName, String mimeType, long length) {

            }
        };
        return multiFileUpload;
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
