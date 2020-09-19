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
                j *= event.getLinkedObject().getRBProps().getInverseMass() + event.getAgainstObject().getRBProps().getInverseMass();

                Vector3f impulse = VectorHelper.multiply(event.getCollisionNormal(), j);
                impulse.z = 0;
                event.getLinkedObject().getRBProps().updateVelocity(impulse);
                angularAdjustmentDueToGravity2(event, impulse, event.getLinkedObject());

                if(!event.getAgainstObject().isStaticObject()) {
                    Vector3f oppImpulse = VectorHelper.multiply(impulse, -1f);
                    event.getAgainstObject().getRBProps().updateVelocity(oppImpulse);
                    angularAdjustmentDueToGravity2(event, oppImpulse, event.getAgainstObject());
                }
            }
        }
    }

    default void angularAdjustmentDueToGravity(CollisionEvent event, Vector3f impulse) {
        RigidBody linked = event.getLinkedObject();
        Transforms againstTransforms = event.getAgainstObject().getTransforms();
        RBProps linkedRBProps = linked.getRBProps();

        Vector3f directionToObjectCenter = VectorHelper.sub(linked.getTransforms().getTranslation(), event.getCollisionPoint());
        directionToObjectCenter.z = 0f;

        float forceMag = VectorHelper.getMagnitude(impulse);
        float rotDir = (float)Math.atan2(impulse.y, impulse.x);
        float angularForce = (float)Math.sin(rotDir) * forceMag;
        directionToObjectCenter.normalize();
        float angularAcc = angularForce/(linkedRBProps.getMass() * VectorHelper.getMagnitude(directionToObjectCenter));

        Vector3f startP = againstTransforms.getTranslation();
        Vector3f endP = VectorHelper.multiply(linked.getTransforms().getTranslation(), 1);
        float pointOnLine = VectorHelper.pointOnLine(startP, endP, event.getLinkedObjectCollisionPoint());
        //if(angularAcc > 0.005f) {
            if (pointOnLine < 0) {
                linkedRBProps.updateAngularVelocity(angularAcc * 10f);
            } else if (pointOnLine > 0) {
                linkedRBProps.updateAngularVelocity(-angularAcc * 10f);
            }
        //}
    }

    default void angularAdjustmentDueToGravity2(CollisionEvent event, Vector3f impulse, RigidBody object) {
        RBProps linkedRBProps = object.getRBProps();


        if(event.getAgainstObject().isStaticObject()) {
            Vector3f directionToStaticObjCenter = VectorHelper.sub(event.getAgainstObject().getTransforms().getTranslation(), object.getTransforms().getTranslation());
            directionToStaticObjCenter.normalize();
            float additionalAcc = impulse.x * directionToStaticObjCenter.y - impulse.y * directionToStaticObjCenter.x;
            linkedRBProps.updateAngularVelocity(additionalAcc*20);
        }else{
            Vector3f directionToCollisionPoint = VectorHelper.sub(event.getCollisionPoint(), object.getTransforms().getTranslation());
            directionToCollisionPoint.z = 0f;
            directionToCollisionPoint.normalize();
            float angularAcceleration = impulse.x * directionToCollisionPoint.y - impulse.y * directionToCollisionPoint.x;
            linkedRBProps.updateAngularVelocity(angularAcceleration*20);
        }

        //slopeCalculation(event);
    }

    default void slopeCalculation(CollisionEvent event) {
        RBProps linkedRBProps = event.getLinkedObject().getRBProps();
        Vector3f directionVectorLinked = event.getLinkedObjectCollisionVector();
        Vector3f directionVectorAgainst = event.getAgainstObjectCollisionVector();

        float slopeLinked = (directionVectorLinked.y/directionVectorAgainst.x);
        float slopeAgainst = (directionVectorAgainst.y/directionVectorAgainst.x);
        if(slopeLinked>slopeAgainst) {
            linkedRBProps.updateAngularVelocity(-0.01f);
        }else{
            linkedRBProps.updateAngularVelocity(0.01f);
        }
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

    default void defaultImpulseResolution2(CollisionEvent event) {
        RBProps propsA = event.getLinkedObject().getRBProps();
        RBProps propsB = event.getAgainstObject().getRBProps();

        float e = Math.min(propsA.getRestitution(), propsB.getRestitution());
        float ma = propsA.getMass();
        float mb = propsB.getMass();
        //no inertia


    }
}
