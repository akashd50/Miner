package com.greymatter.miner.physics.collisioncheckers;

import android.util.Log;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

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
                                linkedCollider.addOrUpdateCollisionEvent(event);
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public static void updateSystemObjectsForces() {
        for(Drawable drawable : systemObjects) {
            if(!drawable.isStaticObject()) {
                drawable.resetGravity();
                drawable.resetFriction();
                for (Drawable against : getSystemObjectsExcept(drawable)) {
                    drawable.applyGravity(calculateGravitationalForce(drawable, against));
                    drawable.applyFriction(VectorHelper.multiply(drawable.getVelocity(), -0.001f));
                    /*if(against.isStaticObject()) {
                        Vector3f frictionalForce = calculateFrictionalForce(drawable, against);
                        if (frictionalForce != null) {
                            //if (frictionalForce.x > 0.00001f || frictionalForce.y > 0.00001f) {
                                drawable.applyFriction(frictionalForce);
                            //} else {
                            //     drawable.setVelocity(new Vector3f());
                            //}
                        }
                    }*/
                }
                drawable.update();
            }
        }
    }

    public static Vector3f calculateGravitationalForce(Drawable current, Drawable against) {
        Vector3f tDir = VectorHelper.sub(against.getTranslation(), current.getTranslation());
        float force = (0.0001f * against.getMass() * current.getMass())/(float)(Math.sqrt(tDir.x*tDir.x + tDir.y*tDir.y));
        tDir.normalize();
        return VectorHelper.multiply(tDir, force);
    }

    public static Vector3f calculateFrictionalForce(Drawable current, Drawable against) {
        float c = 0.0001f;
        CollisionEvent lastEvent = current.getCollider().getLastCollisionEvent(against);
        if(lastEvent!=null && lastEvent.getCollisionStatus()) {
            Vector3f frictionMag = VectorHelper.multiply(lastEvent.getCollisionNormal(),c);
            Vector3f friction = VectorHelper.multiply(current.getVelocity(), -1);
            friction.normalize();
            friction.cross(frictionMag, new Vector3f(1f,1f,0f));

            friction.x = Math.min(0.1f, friction.x);
            friction.y = Math.min(0.1f, friction.y);
            return friction;
        }
        return null;
    }

    public static void onDestroy() {
        isCollisionDetectionActive = false;
    }
}
