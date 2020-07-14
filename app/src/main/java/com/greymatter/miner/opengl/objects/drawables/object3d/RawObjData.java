package com.greymatter.miner.opengl.objects.drawables.object3d;

import com.greymatter.miner.opengl.objects.drawables.Shape;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class RawObjData {
    public ArrayList<Vector3f> vertices;
    public ArrayList<Vector2f> uvs;
    public ArrayList<Config> faceConfiguration;
    public int topIndex;
    
    public RawObjData() {
        vertices = new ArrayList<>();
        uvs = new ArrayList<>();
        faceConfiguration = new ArrayList<>();
    }

    public void dataToShape(Shape shape) {
        for (int i = 0; i < faceConfiguration.size(); i++) {
            Config curr = faceConfiguration.get(i);

            shape.addVertex(vertices.get(curr.v1).x, vertices.get(curr.v1).y, vertices.get(curr.v1).z);
            shape.addVertex(vertices.get(curr.v2).x, vertices.get(curr.v2).y, vertices.get(curr.v2).z);
            shape.addVertex(vertices.get(curr.v3).x, vertices.get(curr.v3).y, vertices.get(curr.v3).z);

            shape.addUV(uvs.get(curr.t1).x, uvs.get(curr.t1).y);
            shape.addUV(uvs.get(curr.t2).x, uvs.get(curr.t2).y);
            shape.addUV(uvs.get(curr.t3).x, uvs.get(curr.t3).y);
        }
    }
}
