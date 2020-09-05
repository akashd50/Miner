package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.helpers.VectorHelper;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class PolygonRB extends RigidBody {
    private ArrayList<Vector3f> meshVertices, transformedVertices;

    public PolygonRB(String id, ArrayList<Vector3f> mesh) {
        super(id);
        this.meshVertices = mesh;
        this.transformedVertices = new ArrayList<>();
        for(Vector3f vec : mesh) {
            transformedVertices.add(new Vector3f(vec));
        }
    }

    public ArrayList<Vector3f> getMeshVertices() {
        return meshVertices;
    }

    public ArrayList<Vector3f> getTransformedVertices() {
        if(!isDynamicallyUpdated()) {
            updateParamsOverride();
        }
        return transformedVertices;
    }

    @Override
    public void updateParamsOverride() {
        ArrayList<Vector3f> newTransformedVerts = getTransforms().getTransformedVertices(meshVertices);
        synchronized (transformedVertices) {
            transformedVertices = newTransformedVerts;
        }
    }

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        return VectorHelper.isPointInPolygon(touchPoint, transformedVertices);
    }
}
