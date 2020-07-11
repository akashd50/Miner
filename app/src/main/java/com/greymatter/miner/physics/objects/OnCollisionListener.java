package com.greymatter.miner.physics.objects;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.physics.objects.rb.RigidBody;

import javax.vecmath.Vector3f;

public interface OnCollisionListener {
    default void onCollision(CollisionEvent event) {
        impulseResolution(event);
        positionalCorrection(event);
    }

    default void impulseResolutionDefault(CollisionEvent event) {
        if(event.getCollisionStatus()) {
            if (event.getCollisionNormal() != null) {
                //Log.v("Default Impulse resolution - Collision Normal: ", event.getCollisionNormal().toString());

                //resolve collision
                Vector3f relativeVelocity = VectorHelper.sub(event.getLinkedObject().getRBProps().getVelocity(),
                        event.getAgainstObject().getRBProps().getVelocity());
                float vectorAlongNormal = VectorHelper.dot(relativeVelocity, event.getCollisionNormal());

                if (vectorAlongNormal > 0) return;

                float e = Math.min(event.getLinkedObject().getRBProps().getRestitution(),
                        event.getAgainstObject().getRBProps().getRestitution());
                float j = -(1 + e) * vectorAlongNormal;
                j *= 1 / event.getLinkedObject().getRBProps().getMass() + 1 / event.getAgainstObject().getRBProps().getMass();

                Vector3f impulse = VectorHelper.multiply(event.getCollisionNormal(), j);

                event.getLinkedObject().getRBProps().updateVelocity(impulse);
                if(!event.getAgainstObject().isStaticObject()) {
                    event.getAgainstObject().getRBProps().updateVelocity(VectorHelper.multiply(impulse, -1f));
                }

                angularAdjustmentDueToGravity(event, impulse);
            }
        }
    }

    default void angularAdjustmentDueToGravity(CollisionEvent event, Vector3f impulse) {
        RigidBody linked = event.getLinkedObject();

        Vector3f directionToObjectCenter = VectorHelper.sub(event.getLinkedObjectCollisionPoint(), event.getAgainstObject().getTranslation());
        Vector3f startP = event.getAgainstObject().getTranslation();
        Vector3f endP = VectorHelper.multiply(linked.getTranslation(), 1);

        float forceMag = VectorHelper.getMagnitude(impulse);
        float rotDir = (float)Math.atan2(impulse.y, impulse.x);
        float angularForce = (float)Math.sin(rotDir) * forceMag;
        directionToObjectCenter.normalize();
        float angularAcc = angularForce/(linked.getRBProps().getMass() * VectorHelper.getMagnitude(directionToObjectCenter));

        float pointOnLine = VectorHelper.pointOnLine(startP, endP, event.getLinkedObjectCollisionPoint());
        if(pointOnLine < 0 ) {
            linked.getRBProps().updateAngularVelocity( angularAcc * 10f);
        }else if (pointOnLine > 0 ){
            linked.getRBProps().updateAngularVelocity(- angularAcc * 10f);
        }
    }

    default void matchSurfaceAngleDefault(CollisionEvent event, Vector3f impulse) {
        if (event.getCollisionStatus() && event.getAgainstObject().isStaticObject()) {
            RigidBody linked = event.getLinkedObject();
            float rotDir = (float)Math.atan2(event.getCollisionNormal().y, event.getCollisionNormal().x);
            float toDegrees = (float)Math.toDegrees(rotDir);
            linked.getRotation().z = toDegrees;
        }
    }

    default void positionalCorrectionDefault(CollisionEvent event) {
        //Log.v("Default Positional Correction", "");
        float scale = (event.getLinkedObject().getScale().x + event.getLinkedObject().getScale().x)/2;
        float percent = 0.2f * scale;
        float slop = 0.01f * scale;
        float correction = Math.max( event.getPenDepth() - slop, 0.0f )
                / (1/event.getLinkedObject().getRBProps().getMass() + 1/event.getAgainstObject().getRBProps().getMass()) * percent;
        Vector3f correctionVector = VectorHelper.multiply(event.getCollisionNormal(), correction);
        correctionVector = VectorHelper.multiply(correctionVector, 1/event.getLinkedObject().getRBProps().getMass());
        event.getLinkedObject().getTransforms().translateBy(correctionVector);
    }

    void impulseResolution(CollisionEvent event);
    void positionalCorrection(CollisionEvent event);
}
