package com.greymatter.miner.physics.collisioncheckers;

import android.util.Log;
import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.CircleCollider;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CustomCollider;

import javax.vecmath.Vector3f;

public class CollisionDetector {
    public static boolean checkCollision(Collider c1, Collider c2) {
        if(c1 instanceof CircleCollider && c2 instanceof  CircleCollider) {
            return circleVCircle((CircleCollider) c1, (CircleCollider) c2);
        }else if(c1 instanceof CircleCollider && c2 instanceof CustomCollider) {

        }else if(c2 instanceof CircleCollider && c1 instanceof CustomCollider) {

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
