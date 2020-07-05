package com.greymatter.miner.physics.collisioncheckers;

import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;
import javax.vecmath.Vector3f;

public class CollisionDetectionSystem {
    private static boolean isCollisionDetectionActive;

    public static void initialize() {
        isCollisionDetectionActive = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isCollisionDetectionActive) {
                    for (Collider collider : CollisionSystemContainer.getAll()) {
                        if(!collider.isStaticObject()) {
                            for (Collider toCheckAgainst : CollisionSystemContainer.getAllExcept(collider)) {
                                CollisionEvent event = CollisionDetectionHelper.checkCollision(collider, toCheckAgainst);

                                assert event != null;
                                if (event.getCollisionStatus() && collider.getCollisionListener() != null) {
                                    collider.getCollisionListener().onCollision(event);
                                }
                                collider.addOrUpdateCollisionEvent(event);
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public static void updateSystemObjectsForces() {
        for(Collider collider : CollisionSystemContainer.getAll()) {
            if(!collider.isStaticObject()) {
                collider.resetGravity();
                collider.resetFriction();
                for (Collider against : CollisionSystemContainer.getAllExcept(collider)) {
                    if(against.isStaticObject()) {
                        collider.applyGravity(calculateGravitationalForce(collider, against));
                        angularAdjustmentDueToGravity(collider,against);
                    }
                }
                collider.applyFriction(VectorHelper.multiply(collider.getVelocity(), -0.01f * collider.getMass()));
                collider.update();
            }
        }
    }

    public static Vector3f calculateGravitationalForce(Collider current, Collider against) {
        Vector3f tDir = VectorHelper.sub(against.getTranslation(), current.getTranslation());
        float force = (0.0000003f * against.getMass() * current.getMass())/(float)(Math.sqrt(tDir.x*tDir.x + tDir.y*tDir.y));
        tDir.normalize();

       //angularAdjustmentDueToGravity(current, against);

        return VectorHelper.multiply(tDir, force);
    }

    private static void angularAdjustmentDueToGravity(Collider current, Collider against) {
        Vector3f directionToObjectCenter = VectorHelper.sub(current.getTranslation(), against.getTranslation());
        Vector3f startP = against.getTranslation();
        Vector3f endP = VectorHelper.multiply(current.getTranslation(), 1);
        float magSumNegSide = 0;
        float magSumPosSide = 0;

        for(Vector3f point : current.asPolygonCollider().getTransformedVertices()) {
            if(VectorHelper.pointOnLine(startP, endP, point) < 0 ) {
                magSumNegSide += VectorHelper.getMagnitude(VectorHelper.sub(against.getTranslation(), point));
            }else {
                magSumPosSide += VectorHelper.getMagnitude(VectorHelper.sub(against.getTranslation(), point));
            }
        }

        if(magSumNegSide<magSumPosSide) {
            current.updateAngularVelocity(0.01f);
        }else{
            current.updateAngularVelocity(-0.01f);
        }
    }

    public static void onDestroy() {
        isCollisionDetectionActive = false;
    }
}
