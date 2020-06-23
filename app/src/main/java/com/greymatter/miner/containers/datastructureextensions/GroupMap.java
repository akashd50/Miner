package com.greymatter.miner.containers.datastructureextensions;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupMap<K,V> extends HashMap<K, ArrayList<V>> {
    public GroupMap(){
        super();
    }

    public boolean delete(K key, V value) {
        ArrayList<V> toRemoveFrom = this.get(key);
        toRemoveFrom.remove(value);
        if(toRemoveFrom.isEmpty()) {
            super.remove(key);
        }
        return true;
    }

    public void add(K groupBy, V toAdd) {
        if(this.containsKey(groupBy)) {
            this.get(groupBy).add(toAdd);
        }else{
            ArrayList<V> list = new ArrayList<>();
            list.add(toAdd);
            this.put(groupBy, list);
        }
    }
}
