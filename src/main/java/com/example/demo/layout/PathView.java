package com.example.demo.layout;

import com.example.demo.MyUI;
import com.example.demo.calculateHashes.processAsync.ProcessAsync;
import com.example.demo.util.ShowData;
import com.example.demo.util.event.MyEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Objects;

public class PathView extends VerticalLayout implements View {

    private MyUI ui;
    private Button ocultar = new Button("Ocultar");
    private Button mostrar = new Button("Mostrar");
    private ProcessAsync processAsync;
    private Label caption = new Label("Atributos");
    private Label labeldata = new Label();
    private final HorizontalLayout row = new HorizontalLayout();
    private final StringBuilder sb = new StringBuilder();


    public PathView(final MyUI ui) {
        this.ui = ui;

        init();
        MyEventBus.register(this);
    }

    public void init() {
        caption.addStyleName("h2");
        caption.addStyleName("bold");
        labeldata.setContentMode(ContentMode.PREFORMATTED);
        behavior();

        row.addComponents(caption,ocultar,mostrar);
        row.setComponentAlignment(caption, Alignment.MIDDLE_CENTER);
        row.setComponentAlignment(ocultar, Alignment.MIDDLE_CENTER);
        row.setComponentAlignment(mostrar, Alignment.MIDDLE_CENTER);
        addComponents(row,labeldata);
    }

    public void behavior() {
        ocultar.setIcon(VaadinIcons.LOCK);
        ocultar.addClickListener( e -> {
            try {
                Files.setAttribute(processAsync.getPath(), "dos:hidden",true);
                Files.setAttribute(processAsync.getPath(), "dos:readonly",true);

                readAttibrutes();
            }catch (IOException ex) {
                Notification.show("No se ecuentra el archivo " + Notification.Type.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        mostrar.setIcon(VaadinIcons.EYE);
        mostrar.addClickListener( e -> {
            try {
                Files.setAttribute(processAsync.getPath(),"dos:hidden", false);
                readAttibrutes();
            }catch (IOException ex) {
                Notification.show("No se ecuentra el archivo " + Notification.Type.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }

    @Subscribe
    public void getPathArchive(final ProcessAsync processAsync) {
        this.processAsync = processAsync;
        readAttibrutes();
    }

    public void readAttibrutes() {
        try {
            final DosFileAttributes dos = Files.readAttributes(processAsync.getPath() , DosFileAttributes.class);
            sb.append(processAsync.getPath().getFileName().toAbsolutePath().toString()+"\n");
            sb.append("Es Archivo: "          + dos.isArchive()+ "\n");
            sb.append("Es oculto: "           + dos.isHidden()  + "\n");
            sb.append("Es solo lectura:   "   + dos.isReadOnly()+"\n");
            sb.append("Es Archivo de sitema " + dos.isSystem() + "\n");
            labeldata.setValue(sb.toString());
            sb.delete(0 , sb.length());
        } catch (IOException  | UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }
}
