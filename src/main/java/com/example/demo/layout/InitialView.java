package com.example.demo.layout;

import com.example.demo.MyUI;
import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class InitialView extends VerticalLayout implements View {

    private MyUI ui;
    private Label label = new Label("Fucking Initial View");
    public InitialView(final MyUI ui) {
        this.ui = ui;

        label.addStyleName("bold");
        label.addStyleName("h2");

        addComponent(label);
    }
}
