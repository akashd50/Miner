package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;

import java.util.ArrayList;

public class ActiveLightsContainer {
    private static HashMapE<ObjId, GameLight> gameLights = new HashMapE<ObjId, GameLight>();

    public static synchronized void add(GameLight gameLight) {
        gameLights.put(gameLight.getId(), gameLight);
    }

    public static synchronized void addAll(ArrayList<GameLight> gameLight) {
        for(GameLight g : gameLight) {
            add(g);
        }
    }

    public static synchronized void onFrameUpdate() {
        gameLights.toList().forEach(GameObject::onFrameUpdate);
    }

    public static synchronized void remove(ObjId id) {
        GameObject removed = null;
        if(gameLights !=null) {
            removed = gameLights.remove(id);
        }
    }

    public static GameLight get(ObjId id) {
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
