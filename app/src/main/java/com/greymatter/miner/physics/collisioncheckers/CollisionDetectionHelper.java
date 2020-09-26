package com.greymatter.miner.physics.collisioncheckers;

import com.greymatter.miner.helpers.IntersectionEvent;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.physics.objects.rb.CircularRB;
import com.greymatter.miner.physics.objects.rb.RigidBody;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class CollisionDetectionHelper {
    public static synchronized CollisionEvent checkCollision(RigidBody c1, RigidBody c2) {
        if(c1 instanceof CircularRB && c2 instanceof CircularRB) {
            return circleVCircle(c1.asCircularRB(), c2.asCircularRB());
        }else if(c1 instanceof CircularRB && c2 instanceof PolygonRB) {
            return circleVCustomAdvanced(c1.asCircularRB(), c2.asPolygonRB());
        }else if(c1 instanceof PolygonRB && c2 instanceof CircularRB) {
            return circleVCustomAdvanced(c2.asCircularRB(), c1.asPolygonRB());
        }else if(c1 instanceof PolygonRB && c2 instanceof PolygonRB) {
            return customVCustomAdvanced(c1.asPolygonRB(), c2.asPolygonRB());
        }
        return null;
    }

    //static helper functions
    private static synchronized CollisionEvent circleVCircle(CircularRB c1, CircularRB c2) {
        float marginOfError = 80f;
        float r = c1.getTransformedRadius() + c2.getTransformedRadius();
        Vector3f c1Translation = c1.getTransforms().getTranslation();
        Vector3f c2Translation = c2.getTransforms().getTranslation();
        r *= r;
        if(r < Math.pow((c1Translation.x + c2Translation.x),2)
                + Math.pow((c1Translation.y + c2Translation.y),2) - marginOfError) {
            return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(true);
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }

    private static synchronized CollisionEvent circleVCustom(CircularRB c1, PolygonRB c2) {
        float r = c1.getTransformedRadius();
        r *= r;
        for(Vector3f vector : c2.getTransformedVertices()) {
            if(VectorHelper.getDistanceWithoutSQRT(vector, c1.getTransforms().getTranslation()) <= r) {
                return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(true);
            }
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }

    private static synchronized CollisionEvent circleVCustomAdvanced(CircularRB c1, PolygonRB c2) {
        Vector3f c1Translation = c1.getTransforms().getTranslation();
        Vector3f circleTop = new Vector3f(c1Translation);
        Vector3f circleBottom = new Vector3f(c1Translation);
        Vector3f circleLeft = new Vector3f(c1Translation);
        Vector3f circleRight = new Vector3f(c1Translation);

        circleTop.add(new Vector3f(0f,c1.getTransformedRadius(),0f));
        circleBottom.sub(new Vector3f(0f,c1.getTransformedRadius(),0f));
        circleLeft.sub(new Vector3f(c1.getTransformedRadius(), 0f,0f));
        circleRight.add(new Vector3f(c1.getTransformedRadius(), 0f,0f));

        ArrayList<Vector3f> verts = c2.getTransformedVertices();

        for(int i=0; i<verts.size() - 1; i++) {
            Vector3f curr = verts.get(i);
            Vector3f next = verts.get(i+1);

            if(VectorHelper.checkIntersectionWithExtraInfo(curr, next, circleTop, circleBottom).intersected
            || VectorHelper.checkIntersectionWithExtraInfo(curr, next, circleLeft, circleRight).intersected) {
                return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(true);
            }
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }

    private static synchronized CollisionEvent customVCustomAdvanced(PolygonRB c1, PolygonRB c2) {
        ArrayList<Vector3f> verticesC2 = c2.getTransformedVertices();
        ArrayList<Vector3f> verticesC1 = c1.getTransformedVertices();

        Vector3f averageCollisionPoint = new Vector3f();
        Vector3f averageCollisionNormal = new Vector3f();
        float averagePenDepth = 0f;
        int eventCounter = 0;
        for(int i=0; i<verticesC1.size() - 1; i++) {
            Vector3f currC1 = verticesC1.get(i);
            Vector3f nextC1 = verticesC1.get(i + 1);
            Vector3f prevC1;
            if(i>0) {
                prevC1 = verticesC1.get(i - 1);
            }else{
                prevC1 = verticesC1.get(verticesC1.size()-1);
            }

            for(int j=0; j<verticesC2.size() - 1; j++) {
                Vector3f currC2 = verticesC2.get(j);
                Vector3f nextC2 = verticesC2.get(j + 1);

                IntersectionEvent intersection = VectorHelper.checkIntersectionWithExtraInfo(currC1, nextC1, currC2, nextC2);
                if (intersection.intersected && intersection.intPoint != null) {
                    //calculate pen depth
                    float penDepth = (float)Math.min(VectorHelper.getDistanceWithSQRT(intersection.intPoint, currC1),
                                                            VectorHelper.getDistanceWithSQRT(intersection.intPoint, nextC1));
                    float distToCurrC1 = (float)VectorHelper.getDistanceWithSQRT(intersection.intPoint, currC1);
                    float distToNextC1 = (float)VectorHelper.getDistanceWithSQRT(intersection.intPoint, nextC1);
                    float distToPrevC1 = (float)VectorHelper.getDistanceWithSQRT(intersection.intPoint, prevC1);

                    Vector3f againstObjectCollisionVector = VectorHelper.sub(nextC2, currC2);
                    Vector3f collNormal = VectorHelper.getNormal(againstObjectCollisionVector);

                    averageCollisionNormal.x += collNormal.x;
                    averageCollisionNormal.y += collNormal.y;
                    averageCollisionNormal.z += collNormal.z;

                    averageCollisionPoint.x += intersection.intPoint.x;
                    averageCollisionPoint.y += intersection.intPoint.y;
                    averageCollisionPoint.z += intersection.intPoint.z;

                    averagePenDepth += penDepth;
                    eventCounter++;
//                    CollisionEvent event = new CollisionEvent().withLinkedObject(c1)
//                            .againstObject(c2)
//                            .withCollisionNormal(collNormal)
//                            .withStatus(true)
//                            .withPenDepth(penDepth).withLinkedObjCollLine(currC1, nex tC1).withAgainstObjCollLine(currC2, nextC2)
//                            .withCollisionPoint(intersection.intPoint)
//                            .withLinkedObjectCollisionPoint(distToCurrC1 < distToNextC1 ? currC1 : nextC1);
//
//                    eventsRegistered.add(event);
                }
            }
        }

        if(averagePenDepth!=0) {
            averageCollisionNormal = VectorHelper.multiply(averageCollisionNormal, 1f/(float)eventCounter);
            averageCollisionPoint = VectorHelper.multiply(averageCollisionPoint, 1f/(float)eventCounter);
            averagePenDepth *= 1f/eventCounter;
            return new CollisionEvent().withStatus(true)
                    .withLinkedObject(c1)
                    .againstObject(c2)
                    .withCollisionNormal(averageCollisionNormal)
                    .withPenDepth(averagePenDepth)
                    .withCollisionPoint(averageCollisionPoint);
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }
}
