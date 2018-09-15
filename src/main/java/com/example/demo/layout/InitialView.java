package com.example.demo.layout;

import com.example.demo.MyUI;
import com.example.demo.calculateHashes.processAsync.ProcessAsync;
import com.example.demo.util.ShowData;
import com.example.demo.util.event.MyEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.DosFileAttributes;

public class InitialView extends VerticalLayout implements View {

    private MyUI ui;
    private Label label = new Label("Fucking Initial View");

    public InitialView(final MyUI ui) {
        this.ui = ui;

        label.addStyleName("bold");
        label.addStyleName("h2");


        addComponents(label);

        MyEventBus.register(this);

    }



    @Subscribe
    public void update(final String count) {
        ShowData.println("HashProcesados: " + count);
        addComponent(new Label("HashProcesados: " + count));
    }


}
