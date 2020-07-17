package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import java.util.ArrayList;
import java.util.Comparator;

public class ToDrawContainer {
    private static HashMapE<String, GameObject> gameObjects;
    private static Comparator<GameObject> comparator = new Comparator<GameObject>() {
        @Override
        public int compare(GameObject o1, GameObject o2) {
            if(o1.getRigidBody().getTranslation().z > o2.getRigidBody().getTranslation().z) {
                return 1;
            }else if(o2.getRigidBody().getTranslation().z > o1.getRigidBody().getTranslation().z){
                return -1;
            }else{
                return 0;
            }
        }
    };

    public static synchronized void add(GameObject gameObject) {
        if(gameObjects == null) {
            gameObjects = new HashMapE<>();
        }
        gameObjects.put(gameObject.getId(), gameObject);
        gameObject.getLinkedObjects().forEach((id, obj) -> {
            gameObjects.put(id, obj);
        });

        gameObjects.sort(comparator);
    }

    public static synchronized void remove(String id) {
        GameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
    }

    public static synchronized void runPostInitialization() {
        gameObjects.toList().forEach(GameObject::runPostInitialization);
    }

    public static synchronized void onDrawFrame(Camera camera) {
        gameObjects.toList().forEach((gameObject) -> {
            if(gameObject.shouldDraw()) {
                gameObject.onFrameUpdate();

                ShaderHelper.useProgram(gameObject.getDrawable().getShader());
                ShaderHelper.setCameraProperties(gameObject.getDrawable().getShader(), camera);
                ShaderHelper.setLightProperties(gameObject.getDrawable().getShader(), ActiveLightsContainer.getAll());
                gameObject.onDrawFrame();
            }
        });
    }

    public static GameObject get(String id) {
        return gameObjects.get(id);
    }

    public static ArrayList<GameObject> getAll() {
        return gameObjects.toList();
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
