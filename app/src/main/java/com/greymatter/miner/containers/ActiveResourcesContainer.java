package com.greymatter.miner.containers;

import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.resources.ResourceBlock;

import java.util.ArrayList;

public class ActiveResourcesContainer extends ActiveGameObjectContainer {
    private ArrayList<ResourceBlock> gameResources;

    public ActiveResourcesContainer() {
        super();
        gameResources = new ArrayList<>();
    }

    public synchronized void add(String id, ResourceBlock gameObject) {
        gameResources.add(gameObject);
        super.add(gameObject);
    }

    public synchronized ResourceBlock remove(String id) {
        ResourceBlock removed = (ResourceBlock)super.remove(id);
        gameResources.remove(removed);
        return removed;
    }

    public ResourceBlock get(String id) {
        return (ResourceBlock)super.get(id);
    }

    public ArrayList<ResourceBlock> getAllResources() {
        return gameResources;
    }

    public ArrayList<ResourceBlock> getAllResourcesWithTag(Tag tag) {
        ArrayList<ResourceBlock> toReturn = new ArrayList<>();
        for(ResourceBlock d : getAllResources()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public ArrayList<ResourceBlock> getAllResourcesWithOnlyTag(Tag tag) {
        ArrayList<ResourceBlock> toReturn = new ArrayList<>();
        for(ResourceBlock d : getAllResources()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
