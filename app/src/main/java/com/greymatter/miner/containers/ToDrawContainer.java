package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import java.util.ArrayList;
import java.util.Comparator;

public class ToDrawContainer {
    private static HashMapE<String, GameObject> gameObjects;
    private static Comparator<GameObject> comparator = new Comparator<GameObject>() {
        @Override
        public int compare(GameObject o1, GameObject o2) {
            return (int)(o1.getCollider().getTranslation().z - o2.getCollider().getTranslation().z);
        }
    };

    public static synchronized void add(GameObject gameObject) {
        if(gameObjects == null) {
            gameObjects = new HashMapE<>();
        }
        gameObjects.put(gameObject.getId(), gameObject);
        gameObjects.sort(comparator);
    }

    public static synchronized void remove(String id) {
        GameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
    }

    public static synchronized void onDrawFrame(Camera camera) {
        gameObjects.forEach((id, gameObject) -> {
            ShaderHelper.useProgram(gameObject.getDrawable().getShader());
            ShaderHelper.setCameraProperties(gameObject.getDrawable().getShader(), camera);
            gameObject.onDrawFrame();
        });
    }

    public static GameObject get(String id) {
        return gameObjects.get(id);
    }

    public static ArrayList<GameObject> getAll() {
        return gameObjects.toList();
    }

    public static ArrayList<GameObject> getAllWithTag(String tag) {
        ArrayList<GameObject> toReturn = new ArrayList<>();
        for(GameObject d : getAll()) {
            if(d.getDrawable().hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<GameObject> getAllWithOnlyTag(String tag) {
        ArrayList<GameObject> toReturn = new ArrayList<>();
        for(GameObject d : getAll()) {
            if(d.getDrawable().getNumTags() == 1 && d.getDrawable().hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
