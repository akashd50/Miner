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
                }
                drawable.applyFriction(VectorHelper.multiply(drawable.getVelocity(), -0.01f * drawable.getMass()));
                drawable.update();
            }
        }
    }

    public static Vector3f calculateGravitationalForce(Drawable current, Drawable against) {
        Vector3f tDir = VectorHelper.sub(against.getTranslation(), current.getTranslation());
        float force = (0.0003f * against.getMass() * current.getMass())/(float)(Math.sqrt(tDir.x*tDir.x + tDir.y*tDir.y));
        tDir.normalize();

       //angularAdjustmentDueToGravity(current, against);

        return VectorHelper.multiply(tDir, force);
    }

    private static void angularAdjustmentDueToGravity(Drawable current, Drawable against) {
        Vector3f directionToObjectCenter = VectorHelper.sub(current.getTranslation(), against.getTranslation());
        Vector3f startP = against.getTranslation();
        Vector3f endP = VectorHelper.multiply(directionToObjectCenter, 10);
        float magSumNegSide = 0;
        float magSumPosSide = 0;

        for(Vector3f vector : current.getCollider().asPolygonCollider().getTransformedVertices()) {
            if(VectorHelper.pointOnLine(startP, endP, vector) < 0 ) {
                magSumNegSide += VectorHelper.getMagnitude(vector);
            }else {
                magSumPosSide += VectorHelper.getMagnitude(vector);
            }
        }

        if(magSumNegSide>magSumPosSide) {
            current.getCollider().setAngularAcceleration( - (magSumNegSide - magSumPosSide));
        }else {
            current.getCollider().setAngularAcceleration( (magSumPosSide - magSumNegSide));
        }
    }

    public static void onDestroy() {
        isCollisionDetectionActive = false;
    }
}
