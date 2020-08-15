package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.opengl.objects.Camera;

import java.util.ArrayList;

public class ActiveResourcesContainer {
    private static HashMapE<String, ResourceBlock> gameResources;

    public static void add(String id, ResourceBlock gameObject) {
        if(gameResources == null) {
            gameResources = new HashMapE<>();
        }
        gameResources.put(id, gameObject);
    }

    public static void remove(String id) {
        GameObject removed = null;
        if(gameResources !=null) {
            removed = gameResources.remove(id);
        }
    }

    public static synchronized void applyTransformations() {
        gameResources.toList().forEach(object -> {object.getTransforms().applyTransformations();});
    }

    public static synchronized void onDrawFrame(Camera camera) {
        applyTransformations();
        gameResources.toList().forEach((gameObject) -> {
            gameObject.onFrameUpdate();
            if(gameObject.shouldDraw()) {
                //gameObject.setShaderProperties(camera);
                gameObject.getDrawable().getRenderer().render(camera, gameObject);
            }
        });
    }

    private static synchronized void onDrawFrame(ResourceBlock gameObject, Camera camera) {

    }

    public static ResourceBlock get(String id) {
        return gameResources.get(id);
    }

    public static ArrayList<ResourceBlock> getAll() {
        return gameResources.toList();
    }

    public static ArrayList<ResourceBlock> getAllWithTag(Tag tag) {
        ArrayList<ResourceBlock> toReturn = new ArrayList<>();
        for(ResourceBlock d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<ResourceBlock> getAllWithOnlyTag(Tag tag) {
        ArrayList<ResourceBlock> toReturn = new ArrayList<>();
        for(ResourceBlock d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
