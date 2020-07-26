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
    public float top, bottom, left, right;
    public float radius, innerAngle, xyRatio;
    private ArrayList<Vector3f> shapeVertices, orderedOuterMesh;
    private ArrayList<Vector2f> shapeUVs;
    private RawObjData rawObjData;
    private float[] verticesArray, uvsArray;
    public Shape(ShapeId id) {
        this.id = id;
        shapeVertices = new ArrayList<>();
        shapeUVs = new ArrayList<>();
    }

    public Shape loadCircle(float radius) {
        ShapesHelper.loadCircle(this, radius);
        this.radius = radius;
        return this;
    }

    public Shape loadPie(float innerAngle, float radius) {
        ShapesHelper.loadPie(this, innerAngle, radius);
        this.radius = radius;
        this.innerAngle = innerAngle;
        return this;
    }

    public Shape loadQuad(float xyRatio) {
        ShapesHelper.loadQuad(this, xyRatio);
        this.xyRatio = xyRatio;
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

    public Shape setTop(float top) {
        this.top = top;
        return this;
    }

    public Shape setBottom(float bottom) {
        this.bottom = bottom;
        return this;
    }

    public Shape setLeft(float left) {
        this.left = left;
        return this;
    }

    public Shape setRight(float right) {
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
        if(orderedOuterMesh == null) {
            ShapesHelper.loadSimpleOuterMesh(this);
        }
        return orderedOuterMesh;
    }

    public ArrayList<Vector3f> getOptimizedOuterMesh(float simplificationFactor) {
        getOrderedOuterMesh();

        int reducedVerticesSize = (int)Math.ceil(orderedOuterMesh.size() * simplificationFactor);
        ArrayList<Vector3f> toReturn = new ArrayList<>();
        for(int i=0;i<orderedOuterMesh.size();i+=(int)(orderedOuterMesh.size()/reducedVerticesSize)) {
            toReturn.add(orderedOuterMesh.get(i));
        }

        orderedOuterMesh = toReturn;
        return orderedOuterMesh;
    }

    public ArrayList<Vector3f> getVerticesList() {
        return shapeVertices;
    }

    public ArrayList<Vector2f> getUvsList() {
        return shapeUVs;
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getRadius() {
        return radius;
    }

    public float getInnerAngle() {
        return innerAngle;
    }

    public float getXyRatio() {
        return xyRatio;
    }

    public ShapeId getId() {
        return id;
    }
}
