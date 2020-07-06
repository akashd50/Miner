package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;

import java.util.ArrayList;
import java.util.Comparator;

public class ActiveLightsContainer {
    private static HashMapE<String, GameLight> gameLights;

    public static synchronized void add(GameLight gameLight) {
        if(gameLights == null) {
            gameLights = new HashMapE<>();
        }

        gameLights.put(gameLight.getId(), gameLight);
    }

    public static synchronized void addAll(ArrayList<GameLight> gameLight) {
        for(GameLight g : gameLight) {
            add(g);
        }
    }

    public static synchronized void remove(String id) {
        GameObject removed = null;
        if(gameLights !=null) {
            removed = gameLights.remove(id);
        }
    }

    public static synchronized void onDrawFrame(Camera camera) {
        gameLights.toList().forEach((gameObject) -> {
            if(gameObject.shouldDraw()) {
                ShaderHelper.useProgram(gameObject.getDrawable().getShader());
                ShaderHelper.setCameraProperties(gameObject.getDrawable().getShader(), camera);
                ShaderHelper.setLightProperties(gameObject.getDrawable().getShader(), gameLights.toList());
                gameObject.onDrawFrame();
            }
        });
    }

    public static GameLight get(String id) {
        return gameLights.get(id);
    }

    public static ArrayList<GameLight> getAll() {
        return gameLights.toList();
    }

    public static ArrayList<GameLight> getAllWithTag(String tag) {
        ArrayList<GameLight> toReturn = new ArrayList<>();
        for(GameLight d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<GameLight> getAllWithOnlyTag(String tag) {
        ArrayList<GameLight> toReturn = new ArrayList<>();
        for(GameLight d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
