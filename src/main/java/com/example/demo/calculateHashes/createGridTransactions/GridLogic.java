package com.example.demo.calculateHashes.createGridTransactions;

import com.example.demo.calculateHashes.Hash;
import com.example.demo.calculateHashes.processAsync.ProcessAsync;
import com.example.demo.util.ShowData;
import com.example.demo.util.event.MyEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GridLogic extends Grid<Hash> {

    private List<Hash> hashList;
    private ListDataProvider<Hash> listDataProvider;
    private Button button;
    private FooterRow footer = prependFooterRow();
    private ProcessAsync processAsync;

    public GridLogic(final Button button) {
        this.button = button;
        button.setEnabled(false);
        button.addStyleName(ValoTheme.BUTTON_DANGER);
        setSizeFull();
        initGrid();

        deleteItem();
        MyEventBus.register(this);
    }

    public void initGrid() {
        addColumn(Hash::getFilename).setCaption("File Name").setId("name");
        addColumn(Hash::getHashtype).setCaption("Hash Type").setId("type");
        addColumn(Hash::getHashresult).setCaption("Hash Result").setId("result");
        addColumn(Hash::getSize).setCaption("Size").setId("size").setWidth(116);
        addColumn(Hash::getTime).setCaption("Time").setId("time").setWidth(126.547);
        addColumn(Hash::getHour).setCaption("Hour").setId("hour").setWidth(130);
        setColumnResizeMode(ColumnResizeMode.ANIMATED);
        setSelectionMode(SelectionMode.MULTI);

        footer.getCell("name").setHtml("<b>Total: </b>");

    }

    public void initData() {
        hashList = new ArrayList<>(GridTransactions.get().findAll());
        listDataProvider = new ListDataProvider<Hash>(hashList);
        setDataProvider(listDataProvider);
        listDataProvider.refreshAll();
        //logic to enabled button, only the grid contains items
        addSelectionListener( e -> button.setEnabled(!e.getAllSelectedItems().isEmpty()));
        footer.getCell("name").setHtml("<b>Total: </b>" + calculateTotal());
        getUI().access(() -> MyEventBus.post(calculateTotal()));

    }

    public void deleteItem() {
        button.addClickListener(e -> {
            button.setEnabled(false);
            getSelectedItems().forEach(item -> listDataProvider.getItems().remove(item));
            footer.getCell("name").setHtml("<b>Total: </b>" + calculateTotal());
            try {
                Files.deleteIfExists(processAsync.getPath());
                ShowData.println("Archivo borrado: " + processAsync.getPath().getFileName().toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            getDataProvider().refreshAll();
        });
    }

    private String calculateTotal() {
        return "<b>" + String.valueOf(listDataProvider.getItems().size()) + "</b>";
    }

    @Subscribe
    public void deletePath(final ProcessAsync processAsync) {
        this.processAsync = processAsync;
    }
}
