package com.example.demo.calculateHashes.createGridTransactions;

import com.example.demo.calculateHashes.Hash;
import com.example.demo.calculateHashes.HashBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridTransactions implements Serializable {

    private static final GridTransactions INSTANCE = new GridTransactions();

    public static GridTransactions get() {
        return INSTANCE;
    }

    private List<Hash> hashes = Collections.synchronizedList(new ArrayList<>());

    public void initData(final HashBuilder hashBuilder) {
        hashes.add(new Hash(hashBuilder));
    }

    public List<Hash> findAll() {
        return Collections.unmodifiableList(hashes);
    }

}
