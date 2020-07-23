package com.greymatter.miner.opengl;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.objects.drawables.object3d.ObjHelper;
import java.util.ArrayList;
import javax.vecmath.Vector3f;

public class ShapesHelper {
    public static void loadCircle(Shape shape, float radius) {
        shape.addVertex(new Vector3f(0f,0f,0f));

        for(int angle=0;angle<360;angle++) {
            shape.addVertex(new Vector3f(
                    radius * (float)Math.cos(Math.toRadians(angle)),
                    radius * (float)Math.sin(Math.toRadians(angle)),
                    0f));
        }

        shape.addVertex(new Vector3f(
                radius * (float)Math.cos(Math.toRadians(0)),
                radius * (float)Math.sin(Math.toRadians(0)),
                0f));

        shape.setTop(radius);
        shape.setBottom(-radius);
        shape.setLeft(-radius);
        shape.setRight(radius);
    }

    public static void loadPie(Shape shape, float innerAngle, float radius) {
        shape.addVertex(new Vector3f(0f,0f,0f));

        for(float angle=0-innerAngle/2;angle<innerAngle/2;angle++) {
            shape.addVertex(new Vector3f(
                    radius * (float)Math.cos(Math.toRadians(angle)),
                    radius * (float)Math.sin(Math.toRadians(angle)),
                    0f));
        }
        shape.setTop(shape.getVerticesList().get(shape.getVerticesList().size()-1).y);
        shape.setBottom(shape.getVerticesList().get(1).y);
        shape.setLeft(0f);
        shape.setRight(radius);
    }

    public static void loadQuad(Shape shape, float xyRatio) {
        shape.addVertex(1.0f * xyRatio, 1.0f, 0.0f)
                .addVertex(-1.0f * xyRatio, 1.0f, 0.0f)
                .addVertex(-1.0f * xyRatio, -1.0f, 0.0f)
                .addVertex(1.0f * xyRatio, -1.0f, 0.0f);

        shape.addUV(1f,0f).addUV(0f,0f).addUV(0f,1f).addUV(1f,1f);

        shape.setTop(1f);
        shape.setBottom(-1f);
        shape.setLeft(-1f * xyRatio);
        shape.setRight(1f * xyRatio);
    }

    public static void loadShapeOutline(Shape shape, ArrayList<Vector3f> toOutline, float edgeWidth) {
        Vector3f previousEdgeNormal =  null;
        Vector3f currentEdgeNormal = null;
        Vector3f previousNetNormal = null;

        for(int i=0; i < toOutline.size()-1; i++) {
            Vector3f nextB = null;
            Vector3f currA = toOutline.get(i);
            Vector3f currB = toOutline.get(i+1);
            if( i+2< toOutline.size()) nextB = toOutline.get(i+2);
            else nextB = toOutline.get(1);

            if(i==0) {
                Vector3f prevA = toOutline.get(toOutline.size()-2);

                previousEdgeNormal = VectorHelper.getNormal(VectorHelper.sub(currA, prevA));
                currentEdgeNormal = VectorHelper.getNormal(VectorHelper.sub(currB, currA));
                previousEdgeNormal.normalize();
                currentEdgeNormal.normalize();

                previousEdgeNormal.x = edgeWidth*previousEdgeNormal.x;
                previousEdgeNormal.y = edgeWidth*previousEdgeNormal.y;
                currentEdgeNormal.x = edgeWidth*currentEdgeNormal.x;
                currentEdgeNormal.y = edgeWidth*currentEdgeNormal.y;

                previousNetNormal = new Vector3f((previousEdgeNormal.x + currentEdgeNormal.x)/2,
                        (previousEdgeNormal.y + currentEdgeNormal.y)/2,0f);
            }

            Vector3f nextEdgeNormal = VectorHelper.getNormal(VectorHelper.sub(nextB, currB));
            nextEdgeNormal.normalize();
            nextEdgeNormal.x = edgeWidth*nextEdgeNormal.x;
            nextEdgeNormal.y = edgeWidth*nextEdgeNormal.y;

            Vector3f currentNetNormal = new Vector3f((nextEdgeNormal.x + currentEdgeNormal.x)/2,
                    (nextEdgeNormal.y + currentEdgeNormal.y)/2,0f);

            //set up vertices and uvs
            addTriangles(shape, currA, currB, previousNetNormal, currentNetNormal);

            previousEdgeNormal = currentEdgeNormal;
            currentEdgeNormal = nextEdgeNormal;
            previousNetNormal = currentNetNormal;
        }
    }

    public static void loadRawObj(Shape shape, String fileName) {
        shape.setRawObjData(ObjHelper.loadFromFile(shape, fileName));
        shape.getRawObjData().dataToShape(shape);
        shape.setOrderedOuterMesh(ObjHelper.generateRoughMesh2(shape.getRawObjData()));
    }

    private static void addTriangles(Shape shape, Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
        shape.addVertex(b.x + d.x, b.y + d.y, d.z)
                .addVertex(a.x + c.x, a.y + c.y, c.z)
                .addVertex(a.x, a.y, a.z)
                .addUV(1.0f, 0f).addUV(0f, 0f).addUV(0f, 1.0f);

        //second
        shape.addVertex(b.x +d.x, b.y +d.y, d.z)
                .addVertex(a.x, a.y, a.z)
                .addVertex(b.x, b.y, b.z)
                .addUV(1.0f, 0f).addUV(0f, 1.0f).addUV(1.0f, 1.0f);
    }

    public static void loadSimpleOuterMesh(Shape shape) {
        ArrayList<Vector3f> outerMesh = new ArrayList<>();
        float top = shape.getTop();
        float bottom = shape.getBottom();
        float left = shape.getLeft();
        float right = shape.getRight();

        outerMesh.add(new Vector3f(left, top, 0f));
        outerMesh.add(new Vector3f(right, top, 0f));
        outerMesh.add(new Vector3f(right, bottom, 0f));
        outerMesh.add(new Vector3f(left, bottom, 0f));
        outerMesh.add(new Vector3f(left, top, 0f));
    }
}
