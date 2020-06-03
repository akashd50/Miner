package com.greymatter.miner.physics.collisioncheckers;

import android.util.Log;
import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.CircleCollider;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CustomCollider;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class CollisionDetectionHelper {
    public static boolean checkCollision(Collider c1, Collider c2) {
        if(c1 instanceof CircleCollider && c2 instanceof  CircleCollider) {
            return circleVCircle((CircleCollider) c1, (CircleCollider) c2);
        }else if(c1 instanceof CircleCollider && c2 instanceof CustomCollider) {
            return circleVCustomAdvanced((CircleCollider) c1, (CustomCollider) c2);
        }else if(c1 instanceof CustomCollider && c2 instanceof CircleCollider) {
            return circleVCustomAdvanced((CircleCollider) c2, (CustomCollider) c1);
        }
        return false;
    }

    //static helper functions
    private static boolean circleVCircle(CircleCollider c1, CircleCollider c2) {
        float marginOfError = 80f;
        float r = c1.getTransformedRadius() + c2.getTransformedRadius();
        r *= r;
        return r < Math.pow((c1.getTranslation().x + c2.getTranslation().x),2)
                + Math.pow((c1.getTranslation().y + c2.getTranslation().y),2) - marginOfError;
    }

    private static boolean circleVCustom(CircleCollider c1, CustomCollider c2) {
        float r = c1.getTransformedRadius();
        r *= r;
        for(Vector3f vector : c2.getTransformedVertices()) {
            if(getDistance(vector, c1.getTranslation()) <= r) {
                return true;
            }
        }
        return false;
    }

    private static boolean circleVCustomAdvanced(CircleCollider c1, CustomCollider c2) {
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
                return true;
            }
        }
        return false;
    }

    public static double getDistance(Vector3f v1, Vector3f v2) {
        return Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2);
    }

    private static boolean checkLineIntersection(Vector3f a1, Vector3f a2, Vector3f b1, Vector3f b2){
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
