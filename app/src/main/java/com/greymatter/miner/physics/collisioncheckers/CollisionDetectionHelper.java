package com.greymatter.miner.physics.collisioncheckers;

import com.greymatter.miner.generalhelpers.IntersectionEvent;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.physics.objects.CircleCollider;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.PolygonCollider;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class CollisionDetectionHelper {
    public static synchronized CollisionEvent checkCollision(Collider c1, Collider c2) {
        if(c1 instanceof CircleCollider && c2 instanceof  CircleCollider) {
            return circleVCircle(c1.asCircleCollider(), c2.asCircleCollider());
        }else if(c1 instanceof CircleCollider && c2 instanceof PolygonCollider) {
            return circleVCustomAdvanced(c1.asCircleCollider(), c2.asPolygonCollider());
        }else if(c1 instanceof PolygonCollider && c2 instanceof CircleCollider) {
            return circleVCustomAdvanced(c2.asCircleCollider(), c1.asPolygonCollider());
        }else if(c1 instanceof PolygonCollider && c2 instanceof PolygonCollider) {
            return customVCustomAdvanced(c1.asPolygonCollider(), c2.asPolygonCollider());
        }
        return null;
    }

    //static helper functions
    private static synchronized CollisionEvent circleVCircle(CircleCollider c1, CircleCollider c2) {
        float marginOfError = 80f;
        float r = c1.getTransformedRadius() + c2.getTransformedRadius();
        r *= r;
        if(r < Math.pow((c1.getTranslation().x + c2.getTranslation().x),2)
                + Math.pow((c1.getTranslation().y + c2.getTranslation().y),2) - marginOfError) {
            return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(true);
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }

    private static synchronized CollisionEvent circleVCustom(CircleCollider c1, PolygonCollider c2) {
        float r = c1.getTransformedRadius();
        r *= r;
        for(Vector3f vector : c2.getTransformedVertices()) {
            if(VectorHelper.getDistance(vector, c1.getTranslation()) <= r) {
                return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(true);
            }
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }

    private static synchronized CollisionEvent circleVCustomAdvanced(CircleCollider c1, PolygonCollider c2) {
        Vector3f circleTop = new Vector3f(c1.getTranslation());
        Vector3f circleBottom = new Vector3f(c1.getTranslation());
        Vector3f circleLeft = new Vector3f(c1.getTranslation());
        Vector3f circleRight = new Vector3f(c1.getTranslation());

        circleTop.add(new Vector3f(0f,c1.getTransformedRadius(),0f));
        circleBottom.sub(new Vector3f(0f,c1.getTransformedRadius(),0f));
        circleLeft.sub(new Vector3f(c1.getTransformedRadius(), 0f,0f));
        circleRight.add(new Vector3f(c1.getTransformedRadius(), 0f,0f));

        ArrayList<Vector3f> verts = c2.getTransformedVertices();

        for(int i=0;i<verts.size();i++) {
            Vector3f curr = verts.get(i);
            Vector3f next = null;

            if(i<verts.size()-1){
                next = verts.get(i+1);
            }else{
                next = verts.get(0);
            }
//            if(checkLineIntersection(curr, next, circleTop, circleBottom)
//            || checkLineIntersection(curr, next, circleLeft, circleRight)) {
//                return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(true);
//            }
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }

    private static synchronized CollisionEvent customVCustomAdvanced(PolygonCollider c1, PolygonCollider c2) {
        ArrayList<Vector3f> vertsC2 = c2.getTransformedVertices();
        ArrayList<Vector3f> vertsC1 = c1.getTransformedVertices();

        for(int i=0;i<vertsC1.size();i++) {
            Vector3f currC1 = vertsC1.get(i);
            Vector3f nextC1 = null;
            if (i < vertsC1.size() - 1) { nextC1 = vertsC1.get(i + 1); }
            else { nextC1 = vertsC1.get(0); }

            for(int j=0;j<vertsC2.size();j++) {
                Vector3f currC2 = vertsC2.get(j);
                Vector3f nextC2 = null;
                if (j < vertsC2.size() - 1) { nextC2 = vertsC2.get(j + 1); }
                else { nextC2 = vertsC2.get(0); }

                IntersectionEvent intersection = VectorHelper.checkIntersectionWithExtraInfo(currC1, nextC1, currC2, nextC2);
                if (intersection.intersected) {

                    //calculate pen depth
                    float linkedObjVertDist = (float)Math.min(VectorHelper.getDistanceWithSQRT(c2.getTranslation(), currC1),
                                                            VectorHelper.getDistanceWithSQRT(c2.getTranslation(), nextC1));
                    float collPointDist = (float)VectorHelper.getDistanceWithSQRT(c2.getTranslation(), intersection.intPoint);

                    float penDepth = 0f;
                    if(collPointDist>linkedObjVertDist) penDepth = collPointDist - linkedObjVertDist;
                    else penDepth = linkedObjVertDist - collPointDist;

                    //calculate linked intersection point
                    float distToCurrC1 = (float)VectorHelper.getDistanceWithSQRT(intersection.intPoint, currC1);
                    float distToNextC1 = (float)VectorHelper.getDistanceWithSQRT(intersection.intPoint, nextC1);

                    Vector3f collNormal = VectorHelper.getNormal(VectorHelper.sub(nextC2, currC2));
                    return new CollisionEvent().withLinkedObject(c1)
                                                .againstObject(c2)
                                                .withCollisionNormal(collNormal)
                                                .withStatus(true)
                                                .withPenDepth(penDepth)
                                                .withLinkedObjCollisionVector(VectorHelper.sub(nextC1, currC1))
                                                .withAgainstObjectCollisionVector(VectorHelper.sub(nextC2, currC2))
                                                .withAgainstObjectCollisionPoint(intersection.intPoint)
                                                .withLinkedObjectCollisionPoint(distToCurrC1 < distToNextC1 ? currC1 : nextC1);
                }
            }
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }
}
