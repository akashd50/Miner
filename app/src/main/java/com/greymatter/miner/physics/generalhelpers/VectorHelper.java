package com.greymatter.miner.physics.generalhelpers;

import javax.vecmath.Vector3f;

public class VectorHelper {

    public static Vector3f copy(Vector3f vector) {
        return new Vector3f(vector);
    }

    public static Vector3f sub(Vector3f from, Vector3f toSub) {
        Vector3f temp = copy(from);
        temp.sub(toSub);
        return temp;
    }

    public static float dot(Vector3f v1, Vector3f v2) {
        return v1.dot(v2);
    }

    public static Vector3f multiply(Vector3f v1, float f) {
        Vector3f toMult = copy(v1);
        toMult.x *= f;
        toMult.y *= f;
        return toMult;
    }

    public static double getDistance(Vector3f v1, Vector3f v2) {
        return Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2);
    }

    public static double getDistanceWithSQRT(Vector3f v1, Vector3f v2) {
        return Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    public static Vector3f getNormal(Vector3f vector) {
        float mag = getMagnitude(vector);
        return new Vector3f(-vector.y/mag,vector.x/mag, vector.z);
    }

    public static float getMagnitude(Vector3f vector) {
        return (float)Math.sqrt(vector.x*vector.x + vector.y*vector.y);
    }
}
