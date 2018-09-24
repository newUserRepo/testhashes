package com.example.demo.navigation;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.AbstractOrderedLayout;


public interface ViewChangeManager {

    boolean manage(AbstractOrderedLayout menuContent, ViewChangeListener
            .ViewChangeEvent event);
}
