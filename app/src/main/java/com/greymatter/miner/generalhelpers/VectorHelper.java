package com.greymatter.miner.generalhelpers;

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

    public static float angle(Vector3f v1, Vector3f v2) {
        float dot = v1.dot(v2);
        float angle = (float)Math.acos(dot/(getMagnitude(v1) * getMagnitude(v2)));
        return angle;
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

    public static Vector3f rotateAroundZ(Vector3f v1, float angleRad) {
        float[] rmat = {(float)Math.cos(angleRad), -(float)Math.sin(angleRad),
                        (float)Math.sin(angleRad), (float)Math.cos(angleRad)};
        Vector3f rotatedAngle = new Vector3f();
        rotatedAngle.x = v1.x * rmat[0] + v1.y * rmat[1];
        rotatedAngle.y = v1.x * rmat[2] + v1.y * rmat[3];
        return rotatedAngle;
    }

    public static float pointOnLine(Vector3f lineA, Vector3f lineB, Vector3f point) {
        //(Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)
        return (lineB.x - lineA.x) * (point.y - lineA.y) - (lineB.y - lineA.y) * (point.x - lineA.x);
    }
}
