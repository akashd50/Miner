package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.opengl.shader.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import java.util.ArrayList;
import java.util.Comparator;

public class ToDrawContainer {
    private static HashMapE<ObjId, IGameObject> gameObjects;
    private static Comparator<IGameObject> comparator = new Comparator<IGameObject>() {
        @Override
        public int compare(IGameObject o1, IGameObject o2) {
            if(o1.getTransforms().getTranslation().z > o2.getTransforms().getTranslation().z) {
                return 1;
            }else if(o2.getTransforms().getTranslation().z > o1.getTransforms().getTranslation().z){
                return -1;
            }else{
                return 0;
            }
        }
    };

    public static synchronized void add(IGameObject gameObject) {
        if(gameObjects == null) {
            gameObjects = new HashMapE<>();
        }
        gameObjects.put(gameObject.getId(), gameObject);
        gameObjects.sort(comparator);
    }

    public static synchronized void remove(ObjId id) {
        IGameObject removed = null;
        if(gameObjects !=null) {
            removed = gameObjects.remove(id);
        }
    }

    public static synchronized void onDrawFrame(Camera camera) {
        gameObjects.toList().forEach((gameObject) -> {
            if(gameObject.shouldDraw()) {
                gameObject.onFrameUpdate();
                gameObject.getTransforms().applyTransformations();
                ShaderHelper.useProgram(gameObject.getDrawable().getShader());
                ShaderHelper.setCameraProperties(gameObject.getDrawable().getShader(), camera);
                ShaderHelper.setLightProperties(gameObject.getDrawable().getShader(), ActiveLightsContainer.getAll());
                gameObject.onDrawFrame();

                gameObject.getChildren().toList().forEach(child -> {
                    onDrawFrame(child, camera);
                });

            }
        });
    }

    private static synchronized void onDrawFrame(IGameObject gameObject, Camera camera) {
        if(gameObject.shouldDraw()) {
            gameObject.onFrameUpdate();
            ShaderHelper.useProgram(gameObject.getDrawable().getShader());
            ShaderHelper.setCameraProperties(gameObject.getDrawable().getShader(), camera);
            ShaderHelper.setLightProperties(gameObject.getDrawable().getShader(), ActiveLightsContainer.getAll());
            gameObject.onDrawFrame();

            gameObject.getChildren().toList().forEach(child -> {
                onDrawFrame(child, camera);
            });
        }
    }

    public static IGameObject get(ObjId id) {
        return gameObjects.get(id);
    }

    public static ArrayList<IGameObject> getAll() {
        return gameObjects.toList();
    }

    public static ArrayList<IGameObject> getAllReversed() {
        return gameObjects.toReversedList();
    }

    public static ArrayList<IGameObject> getAllWithTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<IGameObject> getAllWithOnlyTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        for(IGameObject d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
