package com.greymatter.miner.physics.objects;

import android.util.Log;

import com.greymatter.miner.generalhelpers.VectorHelper;

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
                //rotational resolution
                matchSurfaceAngleDefault(event, impulse);
               // angularImpulseResolutionDefault(event, impulse);
            }
        }
    }

    default void angularImpulseResolutionDefault(CollisionEvent event, Vector3f impulse) {
        if (event.getCollisionStatus()) {
            Collider linked = event.getLinkedObject();
            Vector3f directionToLinkedObjCenter = VectorHelper.sub(linked.getTranslation(), event.getLinkedObjectCollisionPoint());
            //float directionToCenterAngle = (float)Math.atan2(directionToLinkedObjCenter.y, directionToLinkedObjCenter.x);
            float angleBWDTCAndAgVec = VectorHelper.angle(directionToLinkedObjCenter, event.getAgainstObjectCollisionVector());
            float forceMag = VectorHelper.getMagnitude(impulse);
            float rotDir = (float)Math.atan2(impulse.y, impulse.x);
            float angularForce = (float)Math.sin(rotDir) * forceMag;
            float angularAcc = angularForce/(linked.getMass() * VectorHelper.getMagnitude(directionToLinkedObjCenter));

            float cosOfImpulse = (float)Math.cos(angleBWDTCAndAgVec);
            if(cosOfImpulse >= 0) { rotDir = -rotDir; }

            float toDegrees = (float)Math.toDegrees(angularAcc*0.05f);
            linked.setAngularAcceleration(toDegrees*rotDir);
        }
    }

    default void matchSurfaceAngleDefault(CollisionEvent event, Vector3f impulse) {
        if (event.getCollisionStatus() && event.getAgainstObject().isStaticObject()) {
            Collider linked = event.getLinkedObject();
            float rotDir = (float)Math.atan2(event.getCollisionNormal().y, event.getCollisionNormal().x);
            float toDegrees = (float)Math.toDegrees(rotDir);

//            if(linked.getRotation().z < rotDir) {
//                linked.setAngularVelocity(toDegrees*0.01f);
//            }else{
//                linked.setAngularVelocity(-toDegrees*0.01f);
//            }
            linked.getRotation().z = toDegrees;
        }
    }

    default void positionalCorrectionDefault(CollisionEvent event) {
        //Log.v("Default Positional Correction", "");
        float scale = (event.getLinkedObject().getScale().x + event.getLinkedObject().getScale().x)/2;
        float percent = 0.2f * scale;
        float slop = 0.01f * scale;
        float correction = Math.max( event.getPenDepth() - slop, 0.0f )
                / (1/event.getLinkedObject().getMass() + 1/event.getAgainstObject().getMass()) * percent;
        Vector3f correctionVector = VectorHelper.multiply(event.getCollisionNormal(), correction);
        correctionVector = VectorHelper.multiply(correctionVector, 1/event.getLinkedObject().getMass());
        event.getLinkedObject().translateBy(correctionVector);
    }

    void impulseResolution(CollisionEvent event);
    void positionalCorrection(CollisionEvent event);
}
