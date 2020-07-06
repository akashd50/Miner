package com.greymatter.miner.game.containers;

import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Shader;

import java.util.ArrayList;

public class GameObjectsContainer {
    private static HashMapE<String, GameObject> gameObjects;
    private static HashMapE<String, GameLight> gameLights;

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

    public static void remove(String id) {
        GameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
    }

    public static GameObject get(String id) {
        return gameObjects.get(id);
    }

    public static ArrayList<GameObject> getAll() {
        return gameObjects.toList();
    }

    public static ArrayList<GameLight> getAllGameLights() {
        return gameLights.toList();
    }

    public static ArrayList<GameObject> getAllWithTag(String tag) {
        ArrayList<GameObject> toReturn = new ArrayList<>();
        for(GameObject d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<GameObject> getAllWithOnlyTag(String tag) {
        ArrayList<GameObject> toReturn = new ArrayList<>();
        for(GameObject d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
