package com.greymatter.miner.physics.collisioncheckers;

import android.util.Log;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
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
            if(!drawable.getCollider().isStaticObject()) {
                drawable.getCollider().resetGravity();
                drawable.getCollider().resetFriction();
                for (Drawable against : getSystemObjectsExcept(drawable)) {
                    if(against.getCollider().isStaticObject()) {
                        drawable.getCollider().applyGravity(calculateGravitationalForce(drawable, against));
                        angularAdjustmentDueToGravity(drawable,against);
                    }
                }
                drawable.getCollider().applyFriction(VectorHelper.multiply(drawable.getCollider().getVelocity(), -0.01f * drawable.getCollider().getMass()));
                drawable.getCollider().update();
            }
        }
    }

    public static Vector3f calculateGravitationalForce(Drawable current, Drawable against) {
        Vector3f tDir = VectorHelper.sub(against.getCollider().getTranslation(), current.getCollider().getTranslation());
        float force = (0.0003f * against.getCollider().getMass() * current.getCollider().getMass())/(float)(Math.sqrt(tDir.x*tDir.x + tDir.y*tDir.y));
        tDir.normalize();

       //angularAdjustmentDueToGravity(current, against);

        return VectorHelper.multiply(tDir, force);
    }

    private static void angularAdjustmentDueToGravity(Drawable current, Drawable against) {
        Vector3f directionToObjectCenter = VectorHelper.sub(current.getCollider().getTranslation(), against.getCollider().getTranslation());
        Vector3f startP = against.getCollider().getTranslation();
        Vector3f endP = VectorHelper.multiply(current.getCollider().getTranslation(), 1);
        float magSumNegSide = 0;
        float magSumPosSide = 0;

        for(Vector3f point : current.getCollider().asPolygonCollider().getTransformedVertices()) {
            if(VectorHelper.pointOnLine(startP, endP, point) < 0 ) {
                magSumNegSide += VectorHelper.getMagnitude(VectorHelper.sub(against.getCollider().getTranslation(), point));
            }else {
                magSumPosSide += VectorHelper.getMagnitude(VectorHelper.sub(against.getCollider().getTranslation(), point));
            }
        }

        if(magSumNegSide<magSumPosSide) {
            current.getCollider().updateAngularVelocity(0.01f);
        }else{
            current.getCollider().updateAngularVelocity(-0.01f);
        }
    }

    public static void onDestroy() {
        isCollisionDetectionActive = false;
    }
}
