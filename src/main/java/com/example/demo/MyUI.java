package com.example.demo;

import com.example.demo.layout.Main;
import com.example.demo.layout.MainLayout;
import com.example.demo.util.Hora;
import com.example.demo.util.event.MyEventBus;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

/**
 * @author Rub´n
 */
@SpringUI
@Push // enable UI modifications from background threads in the server
@Theme("mytheme")
@Viewport("user-scalable=no, initial-scale=1.0")
public class MyUI extends UI  {

    private Hora hora;

    private final MyEventBus myEventBus = new MyEventBus();

    public MyUI(final Hora hora) {
        this.hora = hora;
    }
    @Override
    protected void init(VaadinRequest request) {

        setContent( new Main(this , hora) );

    }

    public static MyEventBus getEventBusUI() {
        return ((MyUI) getCurrent()).myEventBus;
    }
}
