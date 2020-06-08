package com.greymatter.miner.physics.collisioncheckers;

import android.util.Log;

import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;

import java.util.ArrayList;

public class CollisionDetectionSystem {
    private static ArrayList<Drawable> systemObjects;
    private static boolean isCollisionDetectionActive;
    public static void addObject(Drawable drawable) {
        if(systemObjects==null) {
            systemObjects = new ArrayList<>();
        }
        systemObjects.add(drawable);
    }

    public static ArrayList<Drawable> getSystemObjects() {
        return systemObjects;
    }

    public static ArrayList<Drawable> getSystemObjectsExcept(Drawable drawable) {
        ArrayList<Drawable> toReturn = new ArrayList<Drawable>(systemObjects);
        toReturn.remove(drawable);
        return toReturn;
    }

    public static void initializeWorldCollisionDetectionSystem() {
        isCollisionDetectionActive = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isCollisionDetectionActive) {
                    for (Drawable drawable : systemObjects) {
                        if(!drawable.getCollider().isStaticObject()) {
                            for (Drawable toCheckAgainst : CollisionDetectionSystem.getSystemObjectsExcept(drawable)) {
                                Collider linkedCollider = drawable.getCollider();
                                CollisionEvent event = CollisionDetectionHelper.checkCollision(linkedCollider, toCheckAgainst.getCollider());

                                assert event != null;
                                if (event.getCollisionStatus() && linkedCollider.getCollisionListener() != null) {
                                    Log.v("CollisionListener", "Collision Detected");
                                    linkedCollider.getCollisionListener().onCollision(event);
                                }
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public static void onDestroy() {
        isCollisionDetectionActive = false;
    }
}
