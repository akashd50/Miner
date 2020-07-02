package com.greymatter.miner.containers.datastructureextensions;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HashMapE<K,V> extends HashMap<K,V> {
    private boolean hasDataChanged;
    private ArrayList<V> toList;
    public HashMapE(){
        super();
        hasDataChanged = false;
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        hasDataChanged = true;
        return super.put(key, value);
    }

    public ArrayList<V> toList() {
        if(hasDataChanged) {
            toList = new ArrayList<>();
            forEach((key, obj) -> {
                toList.add(obj);
            });

            hasDataChanged = false;
        }
        return toList;
    }

    public void sort(Comparator<V> comparator) {
        toList = toList == null ? toList() : toList;
        toList.sort(comparator);
    }
}
