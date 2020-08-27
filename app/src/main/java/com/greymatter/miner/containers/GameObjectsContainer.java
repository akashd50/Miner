package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.base.IGameObject;

import java.util.ArrayList;

public class GameObjectsContainer {
    private static HashMapE<String, IGameObject> gameObjects = new HashMapE<>();
    private static HashMapE<String, GameLight> gameLights = new HashMapE<>();

    public static void add(IGameObject gameObject) {
        if(gameObject instanceof GameLight) {
            gameLights.put(gameObject.getId(), (GameLight)gameObject);
        }
        gameObjects.put(gameObject.getId(), gameObject);
    }

    public static void add(String id, IGameObject gameObject) {
        if(gameObject instanceof GameLight) {
            gameLights.put(id, (GameLight)gameObject);
        }
        gameObjects.put(id, gameObject);
    }

    public static void remove(String id) {
        IGameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
    }

    public static IGameObject get(String id) {
        return gameObjects.get(id);
    }

    public static ArrayList<IGameObject> getAll() {
        return gameObjects.toList();
    }

    public static ArrayList<GameLight> getAllGameLights() {
        return gameLights.toList();
    }

    public static ArrayList<IGameObject> getAllWithTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<IGameObject> getAllWithOnlyTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
