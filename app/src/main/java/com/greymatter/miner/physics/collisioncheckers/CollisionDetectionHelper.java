package com.greymatter.miner.physics.collisioncheckers;

import com.greymatter.miner.physics.generalhelpers.VectorHelper;
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
            if(checkLineIntersection(curr, next, circleTop, circleBottom)
            || checkLineIntersection(curr, next, circleLeft, circleRight)) {
                return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(true);
            }
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

                if (checkLineIntersection(currC1, nextC1, currC2, nextC2)) {

                    float againstObjVertDist = (float)Math.min(VectorHelper.getDistanceWithSQRT(c2.getTranslation(), currC2),
                                                            VectorHelper.getDistanceWithSQRT(c2.getTranslation(), nextC2));
                    float linkedObjVertDist = (float)Math.min(VectorHelper.getDistanceWithSQRT(c2.getTranslation(), currC1),
                                                            VectorHelper.getDistanceWithSQRT(c2.getTranslation(), nextC1));
                    float penDepth = 0f;
                    if(againstObjVertDist>linkedObjVertDist) penDepth = againstObjVertDist - linkedObjVertDist;
                    else penDepth = linkedObjVertDist - againstObjVertDist;

                    Vector3f collNormal = VectorHelper.getNormal(VectorHelper.sub(nextC2, currC2));
                    return new CollisionEvent().withLinkedObject(c1)
                                                .againstObject(c2)
                                                .withCollisionNormal(collNormal)
                                                .withStatus(true)
                                                .withPenDepth(penDepth);
                }
            }
        }
        return new CollisionEvent().withLinkedObject(c1).againstObject(c2).withStatus(false);
    }

    private static synchronized boolean checkLineIntersection(Vector3f a1, Vector3f a2, Vector3f b1, Vector3f b2){
        if(a1==null || a2==null || b1==null || b2==null) {
            return false;
        }

        float x1 = a1.x; float x2 = a2.x; float x3 = b1.x; float x4 = b2.x;
        float y1 = a1.y; float y2 = a2.y; float y3 = b1.y; float y4 = b2.y;

        float den = (y4-y3)*(x2-x1) - (x4-x3)*(y2-y1);
        if (den == 0.0f) {
            return false;
        }

        float ta = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3))/den;
        float tb = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3))/den;

        return ta >= 0 && ta <= 1f && tb >= 0 && tb <= 1f;
    }
}
