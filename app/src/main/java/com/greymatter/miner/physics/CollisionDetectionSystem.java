package com.greymatter.miner.physics;

import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.objects.CircleCollider;

import java.util.ArrayList;

public class CollisionDetectionSystem {
    private static ArrayList<Drawable> systemObjects;

    public static void addObject(Drawable drawable) {
        if(systemObjects==null) {
            systemObjects = new ArrayList<>();
        }
        systemObjects.add(drawable);
    }

    public static ArrayList<Drawable> getSystemObjects() {
        return systemObjects;
    }

    public static ArrayList<Drawable> getSystemObjectsExcept(Drawable drawable) {
        ArrayList<Drawable> toReturn = new ArrayList<Drawable>(systemObjects);
        toReturn.remove(drawable);
        return toReturn;
    }

    //static helper functions
    public static boolean circleVCircle(CircleCollider c1, CircleCollider c2) {
        float marginOfError = 80f;
        float r = c1.getTransformedRadius() + c2.getTransformedRadius();
        r *= r;
        return r < Math.pow((c1.getTranslation().x + c2.getTranslation().x),2)
                + Math.pow((c1.getTranslation().y + c2.getTranslation().y),2) - marginOfError;
    }

//    public static boolean rectVRect(  a, AABB b )
//    {
//        // Exit with no intersection if found separated along an axis
//        if(a.max.x < b.min.x or a.min.x > b.max.x) return false
//        if(a.max.y < b.min.y or a.min.y > b.max.y) return false
//
//        // No separating axis found, therefor there is at least one overlapping axis
//        return true
//    }
}
