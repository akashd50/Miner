package com.greymatter.miner.game.containers;

import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Shader;

import java.util.ArrayList;

public class GameObjectsContainer {
    private static HashMapE<String, GameObject> gameObjects;
    private static GroupMap<String, GameObject> groupedByShader;

    public static void add(GameObject gameObject) {
        if(gameObjects == null) {
            gameObjects = new HashMapE<>();
        }

        if(groupedByShader == null) {
            groupedByShader = new GroupMap<>();
        }

        gameObjects.put(gameObject.getId(), gameObject);
        groupedByShader.add(gameObject.getDrawable().getShader().getId(), gameObject);
    }

    public static void remove(String id) {
        GameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }

        if(groupedByShader != null && removed != null) {
            groupedByShader.delete(id,removed);
        }
    }

    public static void onDrawFrame() {
        gameObjects.forEach((id, gameObject) -> {
            gameObject.onDrawFrame();
        });
    }

    public static void onDrawFrameByShader(Camera camera) {
        groupedByShader.forEach((shaderId, intGO) -> {
            Shader toUse = ShaderContainer.get(shaderId);
            ShaderHelper.useProgram(toUse);
            ShaderHelper.setCameraProperties(toUse, camera);

            intGO.forEach(GameObject::onDrawFrame);
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
