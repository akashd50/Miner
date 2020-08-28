package com.greymatter.miner.helpers;

import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class VectorHelper {

    public static Vector3f getNew(float xyz) { return new Vector3f(xyz,xyz,xyz); }
    public static Vector3f copy(Vector3f vector) {
        return new Vector3f(vector);
    }
    public static Vector2f copy(Vector2f vector) {
        return new Vector2f(vector);
    }
    public static Vector2f toVector2f(Vector3f vector3f) {return new Vector2f(vector3f.x, vector3f.y);}
    public static Vector3f toVector3f(Vector2f vector2f) {return new Vector3f(vector2f.x, vector2f.y, 0f);}

    public static Vector3f sub(Vector3f from, Vector3f toSub) {
        Vector3f temp = copy(from);
        temp.sub(toSub);
        return temp;
    }

    public static Vector2f sub(Vector2f from, Vector2f toSub) {
        Vector2f temp = copy(from);
        temp.sub(toSub);
        return temp;
    }

    public static Vector2f abs(Vector2f vec) {
        vec.set(Math.abs(vec.x), Math.abs(vec.y));
        return vec;
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

    public static Vector2f multiply(Vector2f v1, float f) {
        Vector2f toMult = copy(v1);
        toMult.x *= f;
        toMult.y *= f;
        return toMult;
    }

    public static float angle(Vector3f v1, Vector3f v2) {
        float dot = v1.dot(v2);
        float angle = (float)Math.acos(dot/(getMagnitude(v1) * getMagnitude(v2)));
        return angle;
    }

    public static float angleBetween(Drawable d1, Drawable d2) {
        Vector3f d1Tod2 = VectorHelper.sub(d2.getTransforms().getTranslation(), d1.getTransforms().getTranslation());
        return (float)Math.toDegrees(Math.atan2(d1Tod2.y, d1Tod2.x));
    }

    public static float angleBetweenRad(Drawable d1, Drawable d2) {
        Vector3f d1Tod2 = VectorHelper.sub(d2.getTransforms().getTranslation(), d1.getTransforms().getTranslation());
        return (float)Math.atan2(d1Tod2.y, d1Tod2.x);
    }

    public static float angleBetweenRad(Vector3f d1, Vector3f d2) {
        Vector3f d1Tod2 = VectorHelper.sub(d2, d1);
        return (float)Math.atan2(d1Tod2.y, d1Tod2.x);
    }

    public static float getDistanceWithoutSQRT(Vector3f v1, Vector3f v2) {
        return (float)(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    public static float getLength(Vector3f vector3f) {
        return (float)Math.sqrt(vector3f.x * vector3f.x + vector3f.y * vector3f.y + vector3f.z * vector3f.z);
    }

    public static float getLength(Vector2f vector2f) {
        return (float)Math.sqrt(vector2f.x * vector2f.x + vector2f.y * vector2f.y);
    }

    public static double getDistanceWithSQRT(Vector3f v1, Vector3f v2) {
        return Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    public static double getDistanceWithSQRT(Vector2f v1, Vector2f v2) {
        return Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    public static Vector3f getNormal(Vector3f vector) {
        float mag = getMagnitude(vector);
        return new Vector3f(-vector.y/mag,vector.x/mag, 0f);
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

    public static Vector2f rotateAroundZ(Vector2f v1, float angleRad) {
        float[] rmat = {(float)Math.cos(angleRad), -(float)Math.sin(angleRad),
                (float)Math.sin(angleRad), (float)Math.cos(angleRad)};
        Vector2f rotatedAngle = new Vector2f();
        rotatedAngle.x = v1.x * rmat[0] + v1.y * rmat[1];
        rotatedAngle.y = v1.x * rmat[2] + v1.y * rmat[3];
        return rotatedAngle;
    }

    public static Vector3f multiply(Vector3f setTo, Vector3f toMult, float[] mat) {
        setTo.x = mat[0] * toMult.x + mat[4] * toMult.y + mat[8] * toMult.z + mat[12] * 1f;
        setTo.y = mat[1] * toMult.x + mat[5] * toMult.y + mat[9] * toMult.z + mat[13] * 1f;
//        toRet[2] = mat[2] * vec[0] + mat[6] * vec[1] + mat[10] * vec[2] + mat[14] * vec[3];

        return setTo;
    }

    public static Vector3f multiply(Vector3f toMult, float[] mat) {
        Vector3f toRet = new Vector3f();
        toRet.x = mat[0] * toMult.x + mat[4] * toMult.y + mat[8] * toMult.z + mat[12] * 1f;
        toRet.y = mat[1] * toMult.x + mat[5] * toMult.y + mat[9] * toMult.z + mat[13] * 1f;
//        toRet[2] = mat[2] * vec[0] + mat[6] * vec[1] + mat[10] * vec[2] + mat[14] * vec[3];

        return toRet;
    }

    public static float pointOnLine(Vector3f lineA, Vector3f lineB, Vector3f point) {
        return (lineB.x - lineA.x) * (point.y - lineA.y) - (lineB.y - lineA.y) * (point.x - lineA.x);
    }

    public static boolean isPointInPolygon(Vector2f point, ArrayList<Vector3f> polygonVertices) {
        int numIntersections = 0;
        for(int i = 0; i < polygonVertices.size() - 1; i++) {
            Vector2f currC1 = VectorHelper.toVector2f(polygonVertices.get(i));
            Vector2f nextC1 = VectorHelper.toVector2f(polygonVertices.get(i + 1));

            if(VectorHelper.checkIntersection(point, new Vector2f(point.x+1000f,point.y), currC1, nextC1).intersected) {
                numIntersections++;
            }
        }
        return numIntersections % 2 != 0;
    }

    public static IntersectionEvent checkIntersection(Vector2f line1A, Vector2f line1B,
                                                       Vector2f line2A, Vector2f line2B){
        IntersectionEvent intersection = new IntersectionEvent();

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
        }
        return intersection;
    }

    public static IntersectionEvent checkIntersectionWithExtraInfo(Vector3f line1A, Vector3f line1B,
                                                                   Vector3f line2A, Vector3f line2B){
        IntersectionEvent intersection = new IntersectionEvent();

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

    public static Vector3f pointOfIntersection(Vector3f line1A, Vector3f line1B,
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

    public static IntersectionEvent findSnapPlaceOnRBSurface(PolygonRB rb, Vector3f location) {
        Transforms objectTransforms = rb.getTransforms();
        ArrayList<Vector3f> transformedVertices = rb.getTransformedVertices();

        Vector3f fromRbToLoc = sub(location, objectTransforms.getTranslation());
        fromRbToLoc.normalize();

        Vector3f startPt = new Vector3f(objectTransforms.getTranslation());
        Vector3f endPt = new Vector3f(location);//multiply(fromRbToLoc, 100);
        endPt.x += 100 * fromRbToLoc.x;
        endPt.y += 100 * fromRbToLoc.y;

        for(int i=0;i<transformedVertices.size()-1;i++) {
            Vector3f curr = transformedVertices.get(i);
            Vector3f next = transformedVertices.get(i+1);
            IntersectionEvent event = checkIntersectionWithExtraInfo(curr, next, startPt, endPt);
            if(event.intersected) {
                event.intLineA = curr;
                event.intLineB = next;
                return event;
            }
        }
        return null;
    }
}
