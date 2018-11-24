package com.example.demo.layout;

import com.example.demo.MyUI;
import com.example.demo.layout.dbJdbc.CustomerService;
import com.example.demo.layout.dbJdbc.CustomerUI;
import com.example.demo.util.Hora;
import com.example.demo.util.TypesFields;
import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.navigator.SpringViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.SideMenu;


public class Main extends SideMenu {

    private final MyUI ui;

    private Hora hora;

    private SpringViewProvider springViewProvider;

    public Main(final MyUI ui , final SpringViewProvider springViewProvider, final Hora hora) {
        this.ui = ui;
        this.hora = hora;
        this.springViewProvider = springViewProvider;
        init();
    }

    private void init() {
        final Navigator navigator = new Navigator(ui,this);
        navigator.addProvider(springViewProvider);
        ui.setNavigator(navigator);


        //add view
        navigator.addView("" , new InitialView());
       /* navigator.addView(TypesFields.PROCESS_HASHES , new MainLayout(ui, hora));
        navigator.addView(TypesFields.PATH , new PathView(ui));
        navigator.addView("Customer" , CustomerUI.class);*/
        navigator.navigateTo("");

        setMenuCaption(TypesFields.MENU_CAPTION);
        addNavigation("Initial View", "");
       /* addNavigation("Process-Hashes", VaadinIcons.HAMMER, TypesFields.PROCESS_HASHES);
        addNavigation("Path" , VaadinIcons.FOLDER , TypesFields.PATH);
        addNavigation("Customer" , VaadinIcons.USER , "customer");*/


    }

}
