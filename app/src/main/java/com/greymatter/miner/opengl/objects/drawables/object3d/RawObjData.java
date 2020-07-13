package com.greymatter.miner.opengl.objects.drawables.object3d;

import com.greymatter.miner.opengl.objects.drawables.Shape;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class RawObjData {
    public ArrayList<Vector3f> vertices;
    //public ArrayList<Vector3f> normals;
    public ArrayList<Vector2f> uvs;
    public ArrayList<Config> faceConfiguration;
    public ArrayList<Vector3f> outerMesh;
    public float[] arrayVertices, /*arrayNormals,*/ arrayUvs;
    public Vector3f top, bottom, left, right;
    public int topIndex;
    
    public RawObjData() {
        vertices = new ArrayList<>();
        //normals = new ArrayList<>();
        uvs = new ArrayList<>();
        faceConfiguration = new ArrayList<>();
        top = new Vector3f(); bottom = new Vector3f();
        left = new Vector3f(); right = new Vector3f();
    }
    
    public void dataToFloatArray() {
        arrayVertices = new float[faceConfiguration.size() * 3 * 3];
        //arrayNormals = new float[faceConfiguration.size() * 3 * 3];
        arrayUvs = new float[faceConfiguration.size() * 6];

        int tempIndex = 0;
        int vertsIndex = 0;
        int uvsIndex = 0;
        for (int i = 0; i < faceConfiguration.size(); i++) {
            Config curr = faceConfiguration.get(i);

            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v1).x;
            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v1).y;
            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v1).z;

//            arrayNormals[tempIndex++] = (float) normals.get(curr.n1).x;
//            arrayNormals[tempIndex++] = (float) normals.get(curr.n1).y;
//            arrayNormals[tempIndex++] = (float) normals.get(curr.n1).z;

            arrayUvs[uvsIndex++] = (float) uvs.get(curr.t1).x;
            arrayUvs[uvsIndex++] = (float) uvs.get(curr.t1).y;

            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v2).x;
            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v2).y;
            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v2).z;

//            arrayNormals[tempIndex++] = (float) normals.get(curr.n2).x;
//            arrayNormals[tempIndex++] = (float) normals.get(curr.n2).y;
//            arrayNormals[tempIndex++] = (float) normals.get(curr.n2).z;

            arrayUvs[uvsIndex++] = (float) uvs.get(curr.t2).x;
            arrayUvs[uvsIndex++] = (float) uvs.get(curr.t2).y;

            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v3).x;
            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v3).y;
            arrayVertices[vertsIndex++] = (float) vertices.get(curr.v3).z;

//            arrayNormals[tempIndex++] = (float) normals.get(curr.n3).x;
//            arrayNormals[tempIndex++] = (float) normals.get(curr.n3).y;
//            arrayNormals[tempIndex++] = (float) normals.get(curr.n3).z;

            arrayUvs[uvsIndex++] = (float) uvs.get(curr.t3).x;
            arrayUvs[uvsIndex++] = (float) uvs.get(curr.t3).y;
        }
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
