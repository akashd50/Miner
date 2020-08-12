package com.greymatter.miner.physics.collisioncheckers;

import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.physics.objects.rb.RigidBody;
import com.greymatter.miner.physics.objects.CollisionEvent;
import javax.vecmath.Vector3f;

public class CollisionDetectionSystem {
    private static boolean isCollisionDetectionActive;
    private static BooleanAnimator systemForcesTimeAnimator;

    public synchronized static void initialize() {
        isCollisionDetectionActive = true;
        systemForcesTimeAnimator = new BooleanAnimator();
        systemForcesTimeAnimator.withFPS(60);

        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                while(isCollisionDetectionActive) {
                    if(systemForcesTimeAnimator.update().getUpdatedBoolean()) {
                        CollisionDetectionSystem.updateSystemObjectsForces();
                        for (RigidBody rigidBody : CollisionSystemContainer.getAll()) {
                            if(!rigidBody.isStaticObject()) {
                                for (RigidBody toCheckAgainst : CollisionSystemContainer.getAllExcept(rigidBody)) {
                                    CollisionEvent event = CollisionDetectionHelper.checkCollision(rigidBody, toCheckAgainst);

                                    assert event != null;
                                    if (event.getCollisionStatus() && rigidBody.getCollisionListener() != null) {
                                        rigidBody.getCollisionListener().onCollision(event);
                                    }
                                    rigidBody.addOrUpdateCollisionEvent(event);
                                }
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public static void updateSystemObjectsForces() {
        for(RigidBody rigidBody : CollisionSystemContainer.getAll()) {
            if(!rigidBody.isStaticObject()) {
                rigidBody.getRBProps().resetGravity().resetFriction();
                for (RigidBody against : CollisionSystemContainer.getAllExcept(rigidBody)) {
                    if(against.isStaticObject()) {
                        rigidBody.getRBProps().applyGravity(calculateGravitationalForce(rigidBody, against));
                        angularAdjustmentDueToGravity(rigidBody,against);
                    }
                }
                CollisionEvent event = rigidBody.getLastCollisionEvent(GameObjectsContainer.get(ObjId.PLANET).getRigidBody());
                if(event!=null && event.getCollisionStatus()) {
                    rigidBody.getRBProps().applyFriction(VectorHelper.multiply(rigidBody.getRBProps().getVelocity(), (-0.01f * rigidBody.getRBProps().getMass())));
                }else{
                    float distanceFromPlanet = (float)VectorHelper.getDistanceWithSQRT(rigidBody.getTransforms().getTranslation(), GameObjectsContainer.get(ObjId.PLANET).getRigidBody().getTransforms().getTranslation());
                    rigidBody.getRBProps().applyFriction(VectorHelper.multiply(rigidBody.getRBProps().getVelocity(), (-0.01f * rigidBody.getRBProps().getMass())/distanceFromPlanet));
                }
                rigidBody.update();
            }
        }
    }

    public static Vector3f calculateGravitationalForce(RigidBody current, RigidBody against) {
        Vector3f tDir = VectorHelper.sub(against.getTransforms().getTranslation(), current.getTransforms().getTranslation());
        float force = (0.0000003f * against.getRBProps().getMass() * current.getRBProps().getMass())/(float)(Math.sqrt(tDir.x*tDir.x + tDir.y*tDir.y));
        tDir.normalize();
        return VectorHelper.multiply(tDir, force);
    }

    private static void angularAdjustmentDueToGravity(RigidBody current, RigidBody against) {
        Vector3f currentTranslation = current.getTransforms().getTranslation();
        Vector3f againstTranslation = against.getTransforms().getTranslation();

        Vector3f startP = againstTranslation;
        Vector3f endP = VectorHelper.multiply(currentTranslation, 1);
        float magSumNegSide = 0;
        float magSumPosSide = 0;

        for(Vector3f point : current.asPolygonRB().getTransformedVertices()) {
            if(VectorHelper.pointOnLine(startP, endP, point) < 0 ) {
                magSumNegSide += VectorHelper.getMagnitude(VectorHelper.sub(againstTranslation, point));
            }else {
                magSumPosSide += VectorHelper.getMagnitude(VectorHelper.sub(againstTranslation, point));
            }
        }

//        if(magSumNegSide<magSumPosSide) {
//            current.getRBProps().updateAngularVelocity(0.01f);
//        }else{
//            current.getRBProps().updateAngularVelocity(-0.01f);
//        }
        if(magSumNegSide<magSumPosSide) {
            current.getRBProps().setAngularAcceleration(0.00001f);
        }else{
            current.getRBProps().setAngularAcceleration(-0.00001f);
        }
    }

    public static void onDestroy() {
        isCollisionDetectionActive = false;
    }
}
