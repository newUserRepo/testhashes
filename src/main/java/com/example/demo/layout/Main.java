package com.example.demo.layout;

import com.example.demo.MyUI;
import com.example.demo.util.Hora;
import com.example.demo.util.TypesFields;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.SideMenu;

public class Main extends SideMenu {

    private final MyUI ui;

    private Hora hora;

    public Main(final MyUI ui, final Hora hora) {
        this.ui = ui;
        this.hora = hora;

        init();
    }

    private void init() {
        final Navigator navigator = new Navigator(ui,this);
        ui.setNavigator(navigator);

        //add view
        navigator.addView("" , new InitialView(ui));
        navigator.addView(TypesFields.PROCESS_HASHES , new MainLayout(ui, hora));
        navigator.addView(TypesFields.PATH , new PathView(ui));
        navigator.navigateTo("");

        setMenuCaption(TypesFields.MENU_CAPTION);
        addNavigation("Initial View", "");
        addNavigation("Process-Hashes", VaadinIcons.HAMMER, TypesFields.PROCESS_HASHES);
        addNavigation("Path" , VaadinIcons.FOLDER , TypesFields.PATH);

    }
}
