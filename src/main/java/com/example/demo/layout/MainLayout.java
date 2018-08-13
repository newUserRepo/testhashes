package com.example.demo.layout;

import com.example.demo.MyUI;
import com.example.demo.calculateHashes.Hash;
import com.example.demo.calculateHashes.UPloadService;
import com.example.demo.util.Hora;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainLayout extends VerticalLayout {

    private final MyUI ui;
    private VerticalLayout verticalLayoutMenu = new VerticalLayout();
    private Grid<Hash> grid = new Grid<Hash>();
    private GridLayout gridLayout = new GridLayout();
    private Panel panel = new Panel();
    private UPloadService up;
    private final ProgressBar bar = new ProgressBar();
    private final Button btnInterrupt = new Button();
    private CheckBoxGroup<String> checkBoxGroup = new CheckBoxGroup<String>();
    private static final String HASHES[] = {"MD5","SHA1","SHA-385","SHA-256","SHA-512"};
    private final Button buttonDeleteFile = new Button();
    private final Hora hora;
    private Label labelHora;
    private boolean value;

    public MainLayout(final MyUI ui, final Hora hora) {

        this.ui = ui;
        this.hora = hora;
        hora.setHour(this::getHour);

        setSizeFull();

        final Component header = getHeader();
        final Component getMenu = getMenu();

        addComponents( header , getMenu );
        setExpandRatio(getMenu , 1);

    }

    private Component getHeader() {
        final Label label = new Label();
        label.addStyleName("h2");
        label.addStyleName("bold");
        label.setValue("Calculate Hashes...");

        labelHora = new Label("Hora Servidor: " + hora.getHour());
        final HorizontalLayout horizontalLayoutHeader = new HorizontalLayout(label, labelHora);
        horizontalLayoutHeader.setWidth("100%");

        return horizontalLayoutHeader;
    }

    private void getHour(final String hora) {
        ui.access(() -> labelHora.setValue(hora));
    }

    private Component getMenu() {
        final Label labelVersion = new Label("v1.0");
        labelVersion.addStyleName("bold");
        labelVersion.addStyleName("h2");
        labelVersion.addStyleName("colored");
        verticalLayoutMenu.setWidth("120px");
        verticalLayoutMenu.setHeight("100%");
        verticalLayoutMenu.setMargin(false);

        checkBoxGroup.setItems(HASHES);
        checkBoxGroup.addValueChangeListener( e -> {
           final Set<String> values  = e.getValue();
            if(values.size() >= 1) {
                value = true;
            }else {
                value = false;
            }
        });

        verticalLayoutMenu.addComponents(checkBoxGroup,labelVersion);
        verticalLayoutMenu.setComponentAlignment(labelVersion,Alignment.BOTTOM_CENTER);
        verticalLayoutMenu.setExpandRatio(labelVersion , 1);

        final Component getMainArea = getMainArea();
        final HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayoutMenu,getMainArea);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(getMainArea,1);

        return horizontalLayout;
    }

    private Component getMainArea() {
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);
        gridLayout.setColumns(2);
        gridLayout.setRows(3);
        gridLayout.setRowExpandRatio(1,1);

        final CssLayout cssLayout = new CssLayout();
        final TextField textField = new TextField();
        textField.setWidth("100%");
        cssLayout.setWidth("100%");
        final Button btnChecks = new Button();
        btnChecks.setIcon(VaadinIcons.SEARCH);
        cssLayout.setStyleName("css-space");
        cssLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        cssLayout.addComponents(textField,btnChecks);
        gridLayout.addComponent(cssLayout , 0 , 0 );

        final Component g = fillTheGrid();
        gridLayout.addComponent(g,0,1);

        up = new UPloadService( ui , bar , btnInterrupt , value);
        gridLayout.addComponent(up,1,0);

        panel.setSizeFull();
        final VerticalLayout verticalLayout = new VerticalLayout();
        final Label labelPanel = new Label();
        labelPanel.setContentMode(ContentMode.PREFORMATTED);
        final StringBuilder sb = new StringBuilder();
        sb.append("Information here!!!\n");
        sb.append("5000\n");
        sb.append("1000\n");
        sb.append("9500\n");

        labelPanel.setValue(sb.toString());
        verticalLayout.addComponent(labelPanel);
        panel.setContent(verticalLayout);
        gridLayout.addComponent(panel,1,1);

        buttonDeleteFile.setWidth("100%");
        buttonDeleteFile.setIcon(VaadinIcons.TRASH);
        gridLayout.addComponent(buttonDeleteFile , 0, 2);

        btnInterrupt.setIcon(VaadinIcons.STOP);
        bar.setWidth("100%");
        final CssLayout cssLayout1 = new CssLayout(bar,btnInterrupt);
        cssLayout1.setWidth("100%");
        cssLayout1.addStyleName("css-space-3");
        cssLayout1.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        gridLayout.addComponent(cssLayout1 , 1,  2);

        return gridLayout;
    }

    private Grid<Hash> fillTheGrid() {

        //aplicar patron builder aqui, se debera crear un constructor con muchos parametros
        //para este caso amerita hacer dicho pattern
        grid.setSizeFull();
        grid.addColumn(Hash::getFilename).setCaption("File Name").setId("name");
        grid.addColumn(Hash::getHashtype).setCaption("com.example.demo.calculateHashes.Hash Type").setId("type");
        grid.addColumn(Hash::getHashresult).setCaption("com.example.demo.calculateHashes.Hash Result").setId("result");
        grid.addColumn(Hash::getLength).setCaption("Length").setId("length");
        grid.addColumn(Hash::getTime).setCaption("Time").setId("time");
        grid.addColumn(Hash::getHour).setCaption("Hour").setId("hour");

        final List<Hash> hashList = new ArrayList<Hash>();
        final ListDataProvider<Hash> listDataProvider = new ListDataProvider<Hash>(hashList);

        grid.setDataProvider(listDataProvider);

        return grid;
    }
}
