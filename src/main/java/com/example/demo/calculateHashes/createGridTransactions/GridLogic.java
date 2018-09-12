package com.example.demo.calculateHashes.createGridTransactions;

import com.example.demo.calculateHashes.Hash;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.FooterRow;

import java.util.ArrayList;
import java.util.List;

public class GridLogic extends Grid<Hash> {

    private List<Hash> hashList;
    private ListDataProvider<Hash>listDataProvider;
    private Button button;

    private FooterRow footer = prependFooterRow();


    public GridLogic(final Button button) {
        this.button = button;
        setSizeFull();
        initGrid();

        deleteItem();
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

        footer.getCell("name").setHtml("<b>Total: </b>" + calculateTotal());

    }

    public void deleteItem() {
        button.addClickListener( e -> {
            getSelectedItems().forEach( item -> listDataProvider.getItems().remove(item));
            footer.getCell("name").setHtml("<b>Total: </b>" + calculateTotal());
            getDataProvider().refreshAll();
        });
    }

    private String calculateTotal() {
        return "<b>" + String.valueOf(listDataProvider.getItems().size()) + "</b>";
    }

}
