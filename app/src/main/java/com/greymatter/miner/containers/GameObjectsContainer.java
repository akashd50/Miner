package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;

import java.util.ArrayList;

public class GameObjectsContainer {
    private static HashMapE<ObjId, GameObject> gameObjects;
    private static HashMapE<ObjId, GameLight> gameLights;

    public static void add(GameObject gameObject) {
        if(gameObjects == null) {
            gameObjects = new HashMapE<>();
        }

        if(gameLights == null) {
            gameLights = new HashMapE<>();
        }

        if(gameObject instanceof GameLight) {
            gameLights.put(gameObject.getId(), (GameLight)gameObject);
        }

        gameObjects.put(gameObject.getId(), gameObject);
    }

    public static void remove(ObjId id) {
        GameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
    }

    public static synchronized void runPostInitialization() {
        gameObjects.toList().forEach(GameObject::runPostInitialization);
    }

    public static GameObject get(ObjId id) {
        return gameObjects.get(id);
    }

    public static ArrayList<GameObject> getAll() {
        return gameObjects.toList();
    }

    public static ArrayList<GameLight> getAllGameLights() {
        return gameLights.toList();
    }

    public static ArrayList<GameObject> getAllWithTag(Tag tag) {
        ArrayList<GameObject> toReturn = new ArrayList<>();
        for(GameObject d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<GameObject> getAllWithOnlyTag(Tag tag) {
        ArrayList<GameObject> toReturn = new ArrayList<>();
        for(GameObject d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
