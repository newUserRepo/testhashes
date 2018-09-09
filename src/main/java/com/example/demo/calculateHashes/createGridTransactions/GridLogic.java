package com.example.demo.calculateHashes.createGridTransactions;

import com.example.demo.calculateHashes.Hash;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import java.util.ArrayList;
import java.util.List;

public class GridLogic extends Grid<Hash> {

    private List<Hash> hashList;
    private ListDataProvider<Hash>listDataProvider;
    private Button button;

    public GridLogic(final Button button) {
        this.button = button;
        setSizeFull();
        initGrid();

        deleteItem();
    }

    public void initGrid() {
        //setHeightByRows(8);
        addColumn(Hash::getFilename).setCaption("File Name").setId("name");
        addColumn(Hash::getHashtype).setCaption("Hash Type").setId("type");
        addColumn(Hash::getHashresult).setCaption("Hash Result").setId("result");
        addColumn(Hash::getLength).setCaption("Length").setId("length");
        addColumn(Hash::getTime).setCaption("Time").setId("time");
        addColumn(Hash::getHour).setCaption("Hour").setId("hour");
        setSelectionMode(SelectionMode.MULTI);
    }

    /*
    Fixear este metodo  que se instancie despues
     */
    public void initData() {
        hashList = new ArrayList<>(GridTransactions.get().findAll());
        listDataProvider = new ListDataProvider<Hash>(hashList);
        setDataProvider(listDataProvider);
    }

    public void deleteItem() {
        button.addClickListener( e -> {
            getSelectedItems().forEach( item -> listDataProvider.getItems().remove(item));
            getDataProvider().refreshAll();
        });
    }

}
