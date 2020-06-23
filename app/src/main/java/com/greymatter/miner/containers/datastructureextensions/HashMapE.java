package com.greymatter.miner.containers.datastructureextensions;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapE<K,V> extends HashMap<K,V> {
    public HashMapE(){
        super();
    }

    public ArrayList<V> toList() {
        ArrayList<V> toReturn = new ArrayList<>();
        forEach((key, obj) -> {
            toReturn.add(obj);
        });
        return toReturn;
    }
}
