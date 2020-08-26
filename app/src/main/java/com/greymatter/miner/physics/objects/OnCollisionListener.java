package com.greymatter.miner.physics.objects;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.physics.objects.rb.RBProps;
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
                impulse.z = 0;
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
        Transforms againstTransforms = event.getAgainstObject().getTransforms();
        RBProps linkedRBProps = linked.getRBProps();

        Vector3f directionToObjectCenter = VectorHelper.sub(linked.getTransforms().getTranslation(), event.getAgainstObjectCollisionPoint());
        directionToObjectCenter.z = 0f;
        Vector3f startP = againstTransforms.getTranslation();
        Vector3f endP = VectorHelper.multiply(linked.getTransforms().getTranslation(), 1);

        float forceMag = VectorHelper.getMagnitude(impulse);
        float rotDir = (float)Math.atan2(impulse.y, impulse.x);
        float angularForce = (float)Math.sin(rotDir) * forceMag;
        directionToObjectCenter.normalize();
        float angularAcc = angularForce/(linkedRBProps.getMass() * VectorHelper.getMagnitude(directionToObjectCenter));

        float pointOnLine = VectorHelper.pointOnLine(startP, endP, event.getLinkedObjectCollisionPoint());
        //if(angularAcc > 0.005f) {
            if (pointOnLine < 0) {
                linkedRBProps.updateAngularVelocity(angularAcc * 10f);
            } else if (pointOnLine > 0) {
                linkedRBProps.updateAngularVelocity(-angularAcc * 10f);
            }
        //}
    }

    default void matchSurfaceAngleDefault(CollisionEvent event, Vector3f impulse) {
        if (event.getCollisionStatus() && event.getAgainstObject().isStaticObject()) {
            Transforms linkedTransforms = event.getLinkedObject().getTransforms();
            float rotDir = (float)Math.atan2(event.getCollisionNormal().y, event.getCollisionNormal().x);
            float toDegrees = (float)Math.toDegrees(rotDir);
            linkedTransforms.getRotation().z = toDegrees;
        }
    }

    default void positionalCorrectionDefault(CollisionEvent event) {
        Vector3f linkedScale = event.getLinkedObject().getTransforms().getScale();
        float scale = (linkedScale.x + linkedScale.x)/2;
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
