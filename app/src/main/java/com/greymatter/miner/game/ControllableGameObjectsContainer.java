package com.greymatter.miner.game;

import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.ControllableGameObject;
import com.greymatter.miner.game.objects.GameBuilding;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllableGameObjectsContainer {
    private static HashMapE<String, ControllableGameObject> controllableObjects;
    private static GroupMap<String, ControllableGameObject> groupedByShader;

    public static void add(ControllableGameObject controllableObject) {
        if(controllableObjects == null) {
            controllableObjects = new HashMapE<>();
        }

        if(groupedByShader == null) {
            groupedByShader = new GroupMap<>();
        }

        controllableObjects.put(controllableObject.getId(), controllableObject);
        groupedByShader.add(controllableObject.getObjectDrawable().getShader().getId(), controllableObject);
    }

    public static void remove(String id) {
        ControllableGameObject removed = null;
        if(controllableObjects !=null) {
            removed = controllableObjects.remove(id);
        }

        if(groupedByShader != null && removed != null) {
            groupedByShader.delete(id,removed);
        }
    }

    public static void onDrawFrame() {
        controllableObjects.forEach((id, controllableObject) -> {
            controllableObject.onDrawFrame();
        });
    }

    public static ControllableGameObject get(String id) {
        return controllableObjects.get(id);
    }

    public static ArrayList<ControllableGameObject> getAll() {
        return controllableObjects.toList();
    }
}
