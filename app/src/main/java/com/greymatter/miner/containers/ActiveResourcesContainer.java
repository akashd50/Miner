package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.resources.ResourceBlock;

import java.util.ArrayList;

public class ActiveResourcesContainer {
    private static HashMapE<String, ResourceBlock> gameResources;

    public static void add(ResourceBlock gameObject) {
        if(gameResources == null) {
            gameResources = new HashMapE<>();
        }
        gameResources.put(gameObject.getId(), gameObject);
    }

    public static void remove(String id) {
        GameObject removed = null;
        if(gameResources !=null) {
            removed = gameResources.remove(id);
        }
    }

    public static ResourceBlock get(String id) {
        return gameResources.get(id);
    }

    public static ArrayList<ResourceBlock> getAll() {
        return gameResources.toList();
    }

    public static ArrayList<ResourceBlock> getAllWithTag(Tag tag) {
        ArrayList<ResourceBlock> toReturn = new ArrayList<>();
        for(ResourceBlock d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<ResourceBlock> getAllWithOnlyTag(Tag tag) {
        ArrayList<ResourceBlock> toReturn = new ArrayList<>();
        for(ResourceBlock d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
