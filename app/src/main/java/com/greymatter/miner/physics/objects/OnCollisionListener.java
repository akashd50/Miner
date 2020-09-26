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
                RigidBody linkedRB = event.getLinkedObject();
                RigidBody againstRB = event.getAgainstObject();
                RBProps linkedRBProps = linkedRB.getRBProps();
                RBProps againstRBProps = againstRB.getRBProps();

                Vector3f relativeVelocity = VectorHelper.sub(linkedRBProps.getVelocity(), againstRBProps.getVelocity());
                float vectorAlongNormal = VectorHelper.dot(relativeVelocity, event.getCollisionNormal());

                if (vectorAlongNormal > 0) return;

                float e = Math.min(linkedRBProps.getRestitution(), againstRBProps.getRestitution());
                float j = -(1 + e) * vectorAlongNormal;
                j *= (linkedRBProps.getInverseMass() + againstRBProps.getInverseMass());

                Vector3f impulse = VectorHelper.multiply(event.getCollisionNormal(), j);
                impulse.z = 0;
                linkedRBProps.updateVelocity(impulse);
                angularAdjustmentDueToGravity2(event, impulse, event.getLinkedObject());

                if(!againstRB.isStaticObject()) {
                    Vector3f oppImpulse = VectorHelper.multiply(impulse, -1f);
                    againstRBProps.updateVelocity(oppImpulse);
                    angularAdjustmentDueToGravity2(event, oppImpulse, againstRB);
                }
            }
        }
    }

    default void angularAdjustmentDueToGravity2(CollisionEvent event, Vector3f impulse, RigidBody object) {
        RBProps linkedRBProps = object.getRBProps();

        Vector3f impulseCopy = VectorHelper.copy(impulse);
        Vector3f fromCOMtoPoint = VectorHelper.sub(event.getCollisionPoint(), object.getTransforms().getTranslation());
        fromCOMtoPoint.z = 0;
        impulseCopy.cross(impulse, fromCOMtoPoint);

        float pointInertia = VectorHelper.getLength(fromCOMtoPoint);
        //if(linkedRBProps.getAngularVelocity()!=0) pointInertia += linkedRBProps.getAngularVelocity();

        float angularVel = (impulseCopy.z * 60);

//        if((angularVel > 0 && linkedRBProps.getAngularVelocity() > 0) || (angularVel < 0 && linkedRBProps.getAngularVelocity() < 0)) {
//            pointInertia = pointInertia*5;
//        }else{
//            pointInertia *= 10;
//        }
        angularVel = angularVel/Math.max(1f,pointInertia);
        linkedRBProps.updateAngularVelocity(-angularVel);
    }

//    default void slopeCalculation(CollisionEvent event) {
//        RBProps linkedRBProps = event.getLinkedObject().getRBProps();
//        Vector3f directionVectorLinked = event.getLinkedObjectCollisionVector();
//        Vector3f directionVectorAgainst = event.getAgainstObjectCollisionVector();
//
//        float slopeLinked = (directionVectorLinked.y/directionVectorAgainst.x);
//        float slopeAgainst = (directionVectorAgainst.y/directionVectorAgainst.x);
//        if(slopeLinked>slopeAgainst) {
//            linkedRBProps.updateAngularVelocity(-0.01f);
//        }else{
//            linkedRBProps.updateAngularVelocity(0.01f);
//        }
//    }

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
        float percent = 0.1f * scale;
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
