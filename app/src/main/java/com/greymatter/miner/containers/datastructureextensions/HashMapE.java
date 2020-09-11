package com.greymatter.miner.containers.datastructureextensions;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class HashMapE<K,V> extends HashMap<K,V> {
    private boolean hasDataChanged;
    private ArrayList<V> toList, reversedList;
    public HashMapE(){
        super();
        hasDataChanged = true;
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        hasDataChanged = true;
        return super.put(key, value);
    }

    public ArrayList<V> toList() {
        if(hasDataChanged) {
            toList = toList == null? new ArrayList<>() : toList;
            toList.clear();
            for (K key : this.keySet()) {
                toList.add(this.get(key));
            }
            hasDataChanged = false;
        }
        return toList;
    }

    public ArrayList<V> toReversedList() {
        return reversedList;
    }

    public void sort(Comparator<V> comparator) {
        toList();
        toList.sort(comparator);
        reversedList = new ArrayList<>();
        for(int i = toList.size()-1; i >= 0; i--) {
            reversedList.add(toList.get(i));
        }
    }
}
