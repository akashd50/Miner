package com.greymatter.miner.physics.collisioncheckers;

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

                Intersection intersection = checkLineIntersection(currC1, nextC1, currC2, nextC2);
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

    private static synchronized Intersection checkLineIntersection(Vector3f line1A, Vector3f line1B,
                                                                   Vector3f line2A, Vector3f line2B){
        Intersection intersection = new Intersection();

        if(line1A==null || line1B==null || line2A==null || line2B==null) return intersection;

        float y4_y3 = line2B.y-line2A.y; //a2
        float x2_x1 = line1B.x-line1A.x;
        float x4_x3 = line2B.x-line2A.x;
        float y2_y1 = line1B.y-line1A.y; //a1

        float den = (y4_y3)*(x2_x1) - (x4_x3)*(y2_y1);
        if (den == 0.0f) return intersection;

        float y1_y3 = line1A.y-line2A.y;
        float x1_x3 = line1A.x-line2A.x;

        float ta = ((x4_x3)*(y1_y3) - (y4_y3)*(x1_x3))/den;
        float tb = ((x2_x1)*(y1_y3) - (y2_y1)*(x1_x3))/den;

        if(ta >= 0 && ta <= 1f && tb >= 0 && tb <= 1f) {
            intersection.intersected = true;
            intersection.intPoint = pointOfIntersection(line1A,line1B,line2A,line2B);
        }
        return intersection;
    }

    private static Vector3f pointOfIntersection(Vector3f line1A, Vector3f line1B,
                                                Vector3f line2A, Vector3f line2B) {
        // Line AB represented as a1x + b1y = c1
        float a1 = line1B.y - line1A.y;
        float b1 = line1A.x - line1B.x;
        float c1 = a1*(line1A.x) + b1*(line1A.y);

        // Line CD represented as a2x + b2y = c2
        float a2 = line2B.y - line2A.y;
        float b2 = line2A.x - line2B.x;
        float c2 = a2*(line2A.x)+ b2*(line2A.y);

        float determinant = a1*b2 - a2*b1;

        if (determinant == 0) {
            return new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        } else {
            float x = (b2*c1 - b1*c2)/determinant;
            float y = (a1*c2 - a2*c1)/determinant;
            return new Vector3f(x, y,0f);
        }
    }
}

class Intersection {
    Vector3f intPoint;
    boolean intersected = false;
}
