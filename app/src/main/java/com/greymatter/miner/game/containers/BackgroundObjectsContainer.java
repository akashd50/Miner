package com.greymatter.miner.game.containers;

import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Shader;

import java.util.ArrayList;

public class BackgroundObjectsContainer {
    private static HashMapE<String, Static> staticObjects;
    private static GroupMap<String, Static> groupedByShader;

    public static void add(Static object) {
        if(staticObjects == null) {
            staticObjects = new HashMapE<>();
        }

        if(groupedByShader == null) {
            groupedByShader = new GroupMap<>();
        }

        staticObjects.put(object.getId(), object);
        groupedByShader.add(object.getDrawable().getShader().getId(), object);
    }

    public static void remove(String id) {
        Static removed = null;
        if(staticObjects !=null) {
            removed = staticObjects.remove(id);
        }

        if(groupedByShader != null && removed != null) {
            groupedByShader.delete(id,removed);
        }
    }

    public static void onDrawFrame() {
        staticObjects.forEach((id, object) -> {
            object.onDrawFrame();
        });
    }

    public static void onDrawFrameByShader(Camera camera) {
        groupedByShader.forEach((shaderId, objects) -> {
            Shader toUse = ShaderContainer.get(shaderId);
            ShaderHelper.useProgram(toUse);
            ShaderHelper.setCameraProperties(toUse, camera);

            objects.forEach(Static::onDrawFrame);
        });
    }

    public static Static get(String id) {
        return staticObjects.get(id);
    }

    public static ArrayList<Static> getAll() {
        return staticObjects.toList();
    }
}
