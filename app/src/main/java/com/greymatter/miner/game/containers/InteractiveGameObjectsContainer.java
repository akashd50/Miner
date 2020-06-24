package com.greymatter.miner.game.containers;

import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameBuilding;
import com.greymatter.miner.game.objects.InteractiveGameObject;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Shader;

import java.util.ArrayList;

public class InteractiveGameObjectsContainer {
    private static HashMapE<String, InteractiveGameObject> controllableObjects;
    private static GroupMap<String, InteractiveGameObject> groupedByShader;

    public static void add(InteractiveGameObject controllableObject) {
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
        InteractiveGameObject removed = null;
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

    public static void onDrawFrameByShader(Camera camera) {
        groupedByShader.forEach((shaderId, intGO) -> {
            Shader toUse = ShaderContainer.get(shaderId);
            ShaderHelper.useProgram(toUse);
            ShaderHelper.setCameraProperties(toUse, camera);

            intGO.forEach(InteractiveGameObject::onDrawFrame);
        });
    }

    public static InteractiveGameObject get(String id) {
        return controllableObjects.get(id);
    }

    public static ArrayList<InteractiveGameObject> getAll() {
        return controllableObjects.toList();
    }
}
