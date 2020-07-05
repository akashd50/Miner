package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.Collider;

import java.util.ArrayList;

public class CollisionSystemContainer {
    private static HashMapE<String, Collider> colliders;

    public static void add(Collider collider) {
        if(colliders == null) {
            colliders = new HashMapE<>();
        }
        colliders.put(collider.getDrawable().getId(), collider);
    }

    public static void remove(String id) {
        Collider removed = null;
        if(colliders !=null) {
            removed = colliders.remove(id);
        }
    }

    public static ArrayList<Collider> getAllExcept(Collider collider) {
        ArrayList<Collider> toReturn = new ArrayList<Collider>(colliders.toList());
        toReturn.remove(collider);
        return toReturn;
    }

    public static Collider get(String id) {
        return colliders.get(id);
    }

    public static ArrayList<Collider> getAll() {
        return colliders.toList();
    }
}
