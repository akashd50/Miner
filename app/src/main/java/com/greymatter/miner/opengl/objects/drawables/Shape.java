package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.Path;
import com.greymatter.miner.enums.ShapeId;
import com.greymatter.miner.helpers.BufferHelper;
import com.greymatter.miner.opengl.ShapesHelper;
import com.greymatter.miner.opengl.objects.drawables.object3d.RawObjData;
import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Shape {
    private ShapeId id;
    public Vector3f top, bottom, left, right;
    private ArrayList<Vector3f> shapeVertices, orderedOuterMesh;
    private ArrayList<Vector2f> shapeUVs;
    private RawObjData rawObjData;
    private float[] verticesArray, uvsArray;
    public Shape(ShapeId id) {
        this.id = id;
        shapeVertices = new ArrayList<>();
        shapeUVs = new ArrayList<>();
        top = new Vector3f(); bottom = new Vector3f();
        left = new Vector3f(); right = new Vector3f();
    }

    public Shape loadCircle(float radius) {
        ShapesHelper.loadCircle(this, radius);
        return this;
    }

    public Shape loadPie(float innerAngle, float radius) {
        ShapesHelper.loadPie(this, innerAngle, radius);
        return this;
    }

    public Shape loadQuad(float xyRatio) {
        ShapesHelper.loadQuad(this, xyRatio);
        return this;
    }

    public Shape loadObj(String fileName) {
        ShapesHelper.loadRawObj(this, Path.OBJECTS_F + fileName);
        return this;
    }

    public Shape loadEdgeOutline(Shape toOutline, float edgeWidth) {
        ShapesHelper.loadShapeOutline(this, toOutline.getOrderedOuterMesh(), edgeWidth);
        return this;
    }

    public Shape addVertex(Vector3f vert) {
        shapeVertices.add(vert);
        return this;
    }

    public Shape addVertex(float x, float y, float z) {
        shapeVertices.add(new Vector3f(x,y,z));
        return this;
    }

    public Shape addUV(Vector2f uv) {
        shapeUVs.add(uv);
        return this;
    }

    public Shape addUV(float x, float y) {
        shapeUVs.add(new Vector2f(x,y));
        return this;
    }

    public Shape addVertices(ArrayList<Vector3f> verts) {
        shapeVertices.addAll(verts);
        return this;
    }

    public Shape setRawObjData(RawObjData data) {
        this.rawObjData = data;
        return this;
    }

    public Shape setOrderedOuterMesh(ArrayList<Vector3f> orderedOuterMesh) {
        this.orderedOuterMesh = orderedOuterMesh;
        return this;
    }

    public Shape setTop(Vector3f top) {
        this.top = top;
        return this;
    }

    public Shape setBottom(Vector3f bottom) {
        this.bottom = bottom;
        return this;
    }

    public Shape setLeft(Vector3f left) {
        this.left = left;
        return this;
    }

    public Shape setRight(Vector3f right) {
        this.right = right;
        return this;
    }

    public Shape build() {
        if(verticesArray == null) verticesArray = BufferHelper.vec3AsFloatArray(shapeVertices);
        if(uvsArray == null) uvsArray = BufferHelper.vec2AsFloatArray(shapeUVs);
        return this;
    }

    public float[] getVerticesArray() {
        return verticesArray;
    }

    public float[] getUVsArray() {
        return uvsArray;
    }

    public RawObjData getRawObjData() {
        return rawObjData;
    }

    public ArrayList<Vector3f> getOrderedOuterMesh() {
        return orderedOuterMesh;
    }

    public ArrayList<Vector3f> getVerticesList() {
        return shapeVertices;
    }

    public ArrayList<Vector2f> getUvsList() {
        return shapeUVs;
    }

    public Vector3f getTop() {
        return top;
    }

    public Vector3f getBottom() {
        return bottom;
    }

    public Vector3f getLeft() {
        return left;
    }

    public Vector3f getRight() {
        return right;
    }

    public ShapeId getId() {
        return id;
    }
}
