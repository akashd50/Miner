package com.greymatter.miner.physics;

import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.objects.CircleCollider;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

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
