package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.base.IGameObject;

import java.util.ArrayList;

public class AllGameObjectsContainer {
    private HashMapE<String, IGameObject> gameObjects ;

    public AllGameObjectsContainer() {
        gameObjects = new HashMapE<>();
    }

    public void add(IGameObject gameObject) {
        gameObjects.put(gameObject.getId(), gameObject);
    }

    public void add(String id, IGameObject gameObject) {
        gameObjects.put(id, gameObject);
    }

    public IGameObject remove(String id) {
        IGameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
        return removed;
    }

    public IGameObject get(String id) {
        return gameObjects.get(id);
    }

    public ArrayList<IGameObject> getAll() {
        return gameObjects.toList();
    }

    public ArrayList<IGameObject> getAllWithTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public ArrayList<IGameObject> getAllWithOnlyTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
