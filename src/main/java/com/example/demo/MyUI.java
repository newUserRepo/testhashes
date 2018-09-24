package com.example.demo;

import com.example.demo.layout.Main;
import com.example.demo.util.Hora;
import com.example.demo.util.event.MyEventBus;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author Rub´n
 */
@SpringUI
@Push // enable UI modifications from background threads in the server
@Theme("mytheme")
@Viewport("user-scalable=no, initial-scale=1.0")
public class MyUI extends UI {

    private final MyEventBus myEventBus = new MyEventBus();

    @Autowired
    private Hora hora;

    @Autowired
    private SpringViewProvider springViewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setContent(new Main(this , springViewProvider , hora));

    }

    public static MyEventBus getEventBusUI() {
        return ((MyUI) getCurrent()).myEventBus;
    }
}
