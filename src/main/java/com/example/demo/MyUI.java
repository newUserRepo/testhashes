package com.example.demo;

import com.example.demo.layout.MainLayout;
import com.example.demo.util.Hora;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

/**
 * @author Rub´n
 */
@SpringUI
@Push // enable UI modifications from background threads in the server
@Theme("mytheme")
public class MyUI extends UI  {

    private final Hora hora;

    public MyUI(final Hora hora) {
        this.hora = hora;
    }

    @Override
    protected void init(VaadinRequest request) {
        //setPollInterval(-1);

        setContent( new MainLayout(this , hora) );

    }

}
