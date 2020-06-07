package com.greymatter.miner.physics.objects;

import android.util.Log;

import com.greymatter.miner.physics.generalhelpers.VectorHelper;

import javax.vecmath.Vector3f;

public interface OnCollisionListener {
    default void onCollision(CollisionEvent event) {
        impulseResolution(event);
        positionalCorrection(event);
    }

    default void impulseResolutionDefault(CollisionEvent event) {
        if(event.getCollisionStatus()) {
            if (event.getCollisionNormal() != null) {
                Log.v("Default Impulse resolution - Collision Normal: ", event.getCollisionNormal().toString());

                //resolve collision
                Vector3f relativeVelocity = VectorHelper.sub(event.getLinkedObject().getVelocity(),
                        event.getAgainstObject().getVelocity());
                float vectorAlongNormal = VectorHelper.dot(relativeVelocity, event.getCollisionNormal());

                if (vectorAlongNormal > 0) return;

                float e = Math.min(event.getLinkedObject().getRestitution(),
                        event.getAgainstObject().getRestitution());
                float j = -(1 + e) * vectorAlongNormal;
                j /= 1 / event.getLinkedObject().getMass() + 1 / event.getAgainstObject().getMass();

                Vector3f impulse = VectorHelper.multiply(event.getCollisionNormal(), j);

                event.getLinkedObject().updateVelocity(impulse);
            }
        }
    }

    default void positionalCorrectionDefault(CollisionEvent event) {
        Log.v("Default Positional Correction", "");

        float percent = 0.2f;
        float slop = 0.02f;
        float correction = Math.max( event.getPenDepth() - slop, 0.0f )
                / (1/event.getLinkedObject().getMass() + 1/event.getAgainstObject().getMass()) * percent;
        Vector3f correctionVector = VectorHelper.multiply(event.getCollisionNormal(), correction);
        correctionVector = VectorHelper.multiply(correctionVector, 1/event.getLinkedObject().getMass());
        event.getLinkedObject().translateBy(correctionVector);
    }

    void impulseResolution(CollisionEvent event);
    void positionalCorrection(CollisionEvent event);
}
