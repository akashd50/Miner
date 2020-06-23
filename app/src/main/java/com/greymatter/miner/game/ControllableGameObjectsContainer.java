package com.greymatter.miner.game;

import com.greymatter.miner.game.objects.ControllableGameObject;
import com.greymatter.miner.game.objects.GameBuilding;

import java.util.HashMap;

public class ControllableGameObjectsContainer {
    private static HashMap<String, ControllableGameObject> controllableObjects;

    public static void addObject(ControllableGameObject controllableObject) {
        if(controllableObjects == null) {
            controllableObjects = new HashMap<>();
        }
        controllableObjects.put(controllableObject.getId(), controllableObject);
    }

    public static void removeObject(String id) {
        if(controllableObjects !=null) {
            controllableObjects.remove(id);
        }
    }

    public static void onDrawFrame() {
        controllableObjects.forEach((id, controllableObject) -> {
            controllableObject.onDrawFrame();
        });
    }

    public static ControllableGameObject getObject(String id) {
        return controllableObjects.get(id);
    }
}
