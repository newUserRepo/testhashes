package com.example.demo.layout;

import com.example.demo.MyUI;
import com.example.demo.calculateHashes.Hash;
import com.example.demo.calculateHashes.HashesTypes;
import com.example.demo.calculateHashes.UPloadFile;
import com.example.demo.calculateHashes.createGridTransactions.GridLogic;
import com.example.demo.calculateHashes.upload.UploadService;
import com.example.demo.util.CheckIpExternal;
import com.example.demo.util.Hora;
import com.example.demo.util.ShowData;
import com.example.demo.util.TypesFields;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.teemu.switchui.Switch;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainLayout extends VerticalLayout implements View {

    private final MyUI ui;

    private GridLogic gridLogic;
    private Button buttonDeleteFile;

    private VerticalLayout verticalLayoutMenu = new VerticalLayout();
    private GridLayout gridLayout = new GridLayout();
    private RichTextArea richTextArea = new RichTextArea();
    private UPloadFile up;
    private final ProgressBar bar = new ProgressBar();
    private final Button btnInterrupt = new Button();
    private CheckBoxGroup<String> checkBoxGroup = new CheckBoxGroup<String>();
    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private Hora hora;

    private Label labelHora;
    private boolean value;

    private Label labelIPExternal = new Label();
    private HashesTypes hashesTypes;
    private Switch s = new Switch();

    public MainLayout(final MyUI ui, final Hora hora) {
        this.ui = ui;
        this.hora = hora;

        setSizeFull();

        final Component header = getHeader();
        final Component getMenu = getMenu();

        addComponents(header, getMenu);
        setExpandRatio(getMenu, 1);

    }

    private Component getHeader() {
        final Label label = new Label();
        label.addStyleName("h2");
        label.addStyleName("bold");
        label.setValue("Calculate Hashes...");

        hora.setHour(this::getHour);
        labelHora = new Label("Hora Servidor: " + hora.getHour());
        labelHora.addStyleName("label-hour");
        labelHora.addStyleName("colored");
        labelIPExternal.addStyleName("colored");


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    CheckIpExternal.checkIP().whenCompleteAsync((string, error) -> {
                        ui.access(() -> {
                            if (!string.equals("Error")) {
                                labelIPExternal.removeStyleName(ValoTheme.LABEL_FAILURE);
                                labelIPExternal.addStyleName(ValoTheme.LABEL_SUCCESS);
                                labelIPExternal.setValue("Ip: " + string);
                            } else {
                                labelIPExternal.addStyleName(ValoTheme.LABEL_FAILURE);
                                labelIPExternal.setValue("Ip: " + string);
                            }
                        });
                    });
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();


        final HorizontalLayout rowRigth = new HorizontalLayout(labelIPExternal, labelHora);
        final HorizontalLayout horizontalLayoutHeader = new HorizontalLayout(label, rowRigth);
        horizontalLayoutHeader.setWidth("100%");
        horizontalLayoutHeader.setComponentAlignment(rowRigth, Alignment.MIDDLE_RIGHT);

        return horizontalLayoutHeader;
    }

    private void getHour(final String hora) {

        if (ui.isAttached())
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

        checkBoxGroup.setItems(TypesFields.HASHES);
        checkBoxGroup.setItemCaptionGenerator( e -> e.replace("haval_256_4","HAVAL-256-4"));
        checkBoxGroup.addValueChangeListener(e -> {
            final Set<String> set = e.getValue();
            final List<String> crc32 = new ArrayList<>();
            final List<String> md5andSha = new ArrayList<>();
            final List<String> adler32 = new ArrayList<>();
            final List<String> haval256 = new ArrayList<>();

            set.stream()
                    .filter(hash -> hash.contains("CRC32"))
                    .forEach(p -> crc32.add(p));
            set.stream()
                    .filter(hashes -> hashes.contains("ADLER32"))
                    .forEach(p -> adler32.add(p));
            set.stream()
                    .filter(hash -> TypesFields.onlySHA(hash))
                    .forEach(p -> md5andSha.add(p));
            set.stream()
                    .filter(hash -> hash.contains("haval_256_4"))
                    .forEach(p -> haval256.add(p));

            crc32.forEach(c -> System.out.println("Hashes only crc32 " + c));
            adler32.forEach(a -> ShowData.println("Hashes adler: "+a));
            md5andSha.forEach(m -> System.out.println("Md5AndHash only: " +m));

            final HashesTypes hashesTypes = new HashesTypes.Builder()
                    .setCrc32(crc32)
                    .setMd5AndSha(md5andSha)
                    .setAdler32(adler32)
                    .setHaval256(haval256)
                    .build();

            if (set.size() >= 1) {
                up.setEnabledButton(true);
                up.setHashes(hashesTypes);
            } else {
                up.setEnabledButton(false);
            }

        });
        s.addValueChangeListener( e -> {
            if(e.getValue()) {
                checkBoxGroup.select(TypesFields.HASHES);
            } else {
                checkBoxGroup.deselectAll();
            }
        });

        verticalLayoutMenu.addComponents(checkBoxGroup, s, labelVersion);
        verticalLayoutMenu.setComponentAlignment(s, Alignment.TOP_CENTER);
        verticalLayoutMenu.setComponentAlignment(labelVersion, Alignment.BOTTOM_CENTER);
        verticalLayoutMenu.setExpandRatio(labelVersion, 1);

        final Component getMainArea = getMainArea();
        final HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayoutMenu, getMainArea);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(getMainArea, 1);

        return horizontalLayout;
    }

    private Component getMainArea() {
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);
        gridLayout.setColumns(2);
        gridLayout.setRows(3);
        gridLayout.setRowExpandRatio(1, 1);

        final CssLayout cssLayout = new CssLayout();
        final TextField textField = new TextField();
        textField.setWidth("100%");
        cssLayout.setWidth("100%");
        final Button btnChecks = new Button();
        btnChecks.setIcon(VaadinIcons.SEARCH);
        cssLayout.setStyleName("css-space");
        cssLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        cssLayout.addComponents(textField, btnChecks);
        gridLayout.addComponent(cssLayout, 0, 0);

        final Component grid = fillGridLogic();
        gridLayout.addComponent(grid, 0, 1);

        richTextArea.setSizeFull();

        final UploadService uploadFileClass = new UploadService.Builder()
                .setUI(ui)
                .setProgressBar(bar)
                .setBtnInterrupt(btnInterrupt)
                .setRichTextArea(richTextArea)
                .setGridLogic(gridLogic)
                .build();
        up = new UPloadFile(uploadFileClass);
        gridLayout.addComponent(up, 1, 0);

        gridLayout.addComponent(richTextArea, 1, 1);

        buttonDeleteFile.setWidth("100%");
        buttonDeleteFile.setIcon(VaadinIcons.TRASH);
        gridLayout.addComponent(buttonDeleteFile, 0, 2);

        btnInterrupt.setIcon(VaadinIcons.STOP);
        bar.setWidth("100%");
        final CssLayout cssLayout1 = new CssLayout(bar, btnInterrupt);
        cssLayout1.setWidth("100%");
        cssLayout1.addStyleName("css-space-3");
        cssLayout1.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        gridLayout.addComponent(cssLayout1, 1, 2);

        return gridLayout;
    }

    private Grid<Hash> fillGridLogic() {
        buttonDeleteFile = new Button();
        gridLogic = new GridLogic(buttonDeleteFile);
        return gridLogic;
    }

}
