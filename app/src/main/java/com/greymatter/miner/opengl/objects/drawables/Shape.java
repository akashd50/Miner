package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.helpers.BufferHelper;
import java.util.ArrayList;
import javax.vecmath.Vector3f;

public class Shape {
    private String id;
    private ArrayList<Vector3f> shapeVertices;
    private float[] verticesArray;
    public Shape(String id) {
        this.id = id;
        shapeVertices = new ArrayList<>();
    }

    public Shape loadCircle(float radius) {
        shapeVertices.add(new Vector3f(0f,0f,0f));

        for(int angle=0;angle<360;angle++) {
            shapeVertices.add(new Vector3f(
                             radius * (float)Math.cos(Math.toRadians(angle)),
                            radius * (float)Math.sin(Math.toRadians(angle)),
                            0f));
        }

        shapeVertices.add(new Vector3f(
                radius * (float)Math.cos(Math.toRadians(0)),
                radius * (float)Math.sin(Math.toRadians(0)),
                0f));

        return this;
    }

    public Shape loadPie(float innerAngle, float radius) {
        shapeVertices.add(new Vector3f(0f,0f,0f));

        for(float angle=0-innerAngle/2;angle<innerAngle/2;angle++) {
            shapeVertices.add(new Vector3f(
                    radius * (float)Math.cos(Math.toRadians(angle)),
                    radius * (float)Math.sin(Math.toRadians(angle)),
                    0f));
        }

        return this;
    }

    public Shape addVertex(Vector3f vert) {
        shapeVertices.add(vert);
        return this;
    }

    public Shape addVertices(ArrayList<Vector3f> verts) {
        shapeVertices.addAll(verts);
        return this;
    }

    public Shape build() {
        if(verticesArray==null) verticesArray = BufferHelper.vec3AsFloatArray(shapeVertices);
        return this;
    }

    public float[] getVerticesArray() {
        return verticesArray;
    }

    public ArrayList<Vector3f> getVerticesList() {
        return shapeVertices;
    }

    public String getId() {
        return id;
    }
}
