package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;

import java.util.ArrayList;

public class ActiveLightsContainer {
    private static HashMapE<String, GameLight> gameLights = new HashMapE<String, GameLight>();

    public static synchronized void add(GameLight gameLight) {
        gameLights.put(gameLight.getId(), gameLight);
    }

    public static synchronized void addAll(ArrayList<GameLight> gls) {
        for(GameLight g : gls) {
            add(g);
        }
    }

    public static synchronized void onFrameUpdate() {
        gameLights.toList().forEach(GameObject::onFrameUpdate);
    }

    public static synchronized void remove(String id) {
        GameObject removed = null;
        if(gameLights !=null) {
            removed = gameLights.remove(id);
        }
    }

    public static synchronized void removeAll(ArrayList<GameLight> gls) {
        if(gameLights != null) {
            gls.forEach(light -> {
                gameLights.remove(light.getId());
            });
        }
    }

    public static GameLight get(String id) {
        return gameLights.get(id);
    }

    public static ArrayList<GameLight> getAll() {
        return gameLights != null? gameLights.toList() : null;
    }

    public static ArrayList<GameLight> getAllWithTag(Tag tag) {
        ArrayList<GameLight> toReturn = new ArrayList<>();
        for(GameLight d : getAll()) {
            if(d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }

    public static ArrayList<GameLight> getAllWithOnlyTag(Tag tag) {
        ArrayList<GameLight> toReturn = new ArrayList<>();
        for(GameLight d : getAll()) {
            if(d.getNumTags() == 1 && d.hasTag(tag)) toReturn.add(d);
        }
        return toReturn;
    }
}
