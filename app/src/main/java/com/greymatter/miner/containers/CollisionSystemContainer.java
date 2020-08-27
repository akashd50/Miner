package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.physics.objects.rb.RigidBody;

import java.util.ArrayList;

public class CollisionSystemContainer {
    private static HashMapE<String, RigidBody> colliders;

    public synchronized static void add(RigidBody rigidBody) {
        if(colliders == null) {
            colliders = new HashMapE<>();
        }
        colliders.put(rigidBody.getId(), rigidBody);
    }

    public synchronized static void remove(String id) {
        RigidBody removed = null;
        if(colliders !=null) {
            removed = colliders.remove(id);
        }
    }

    public synchronized static ArrayList<RigidBody> getAllExcept(RigidBody rigidBody) {
        ArrayList<RigidBody> toReturn = new ArrayList<RigidBody>(colliders.toList());
        toReturn.remove(rigidBody);
        return toReturn;
    }

    public synchronized static RigidBody get(String id) {
        return colliders.get(id);
    }

    public synchronized static ArrayList<RigidBody> getAll() {
        return colliders.toList();
    }
}
