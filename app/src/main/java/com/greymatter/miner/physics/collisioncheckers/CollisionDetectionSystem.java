package com.greymatter.miner.physics.collisioncheckers;

import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.game.manager.GameManager;
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
                                    toCheckAgainst.addOrUpdateAgainstCollisionEvent(event);
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
                        Vector3f gravitationalForce = calculateGravitationalForce(rigidBody, against);
                        rigidBody.getRBProps().applyGravity(gravitationalForce);
                        //angularAdjustmentDueToGravity(gravitationalForce, rigidBody,against);
                    }
                }
                CollisionEvent event = rigidBody.getLastCollisionEvent(GameManager.getCurrentPlanet().getRigidBody());
                if(event!=null && event.getCollisionStatus()) {
                    rigidBody.getRBProps().applyFriction(VectorHelper.multiply(rigidBody.getRBProps().getVelocity(), (-0.01f * rigidBody.getRBProps().getMass())));
                }else{
                    float distanceFromPlanet = (float)VectorHelper.getDistanceWithSQRT(rigidBody.getTransforms().getTranslation(), GameManager.getCurrentPlanet().getRigidBody().getTransforms().getTranslation());
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

    private static void angularAdjustmentDueToGravity(Vector3f gravitationalForce, RigidBody current, RigidBody against) {
        Vector3f currentTranslation = current.getTransforms().getTranslation();
        Vector3f againstTranslation = against.getTransforms().getTranslation();

        for(Vector3f point : current.asPolygonRB().getTransformedVertices()) {
            Vector3f impulseCopy = VectorHelper.copy(gravitationalForce);
            Vector3f fromCOMtoPoint = VectorHelper.sub(point, currentTranslation);
            fromCOMtoPoint.z = 0;
            impulseCopy.cross(gravitationalForce, fromCOMtoPoint);

            float angularVel = (impulseCopy.z);
            float pointInertia = VectorHelper.getLength(fromCOMtoPoint);
            if(current.getRBProps().getAngularVelocity()!=0) pointInertia += current.getRBProps().getAngularVelocity();

            current.getRBProps().updateAngularVelocity(angularVel/pointInertia);
        }
    }

    public static void onDestroy() {
        isCollisionDetectionActive = false;
    }
}
