package com.greymatter.miner.physics.objects;

import com.greymatter.miner.generalhelpers.VectorHelper;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class PolygonCollider extends Collider {
    private ArrayList<Vector3f> meshVertices, transformedVertices;

    public PolygonCollider(ArrayList<Vector3f> mesh) {
        super();
        this.meshVertices = mesh;
        this.transformedVertices = new ArrayList<>();
    }

    public ArrayList<Vector3f> getMeshVertices() {
        return meshVertices;
    }

    public ArrayList<Vector3f> getTransformedVertices() {
        if(!isUpdatedPerMovement()) {
            updateParamsOverride();
        }
        return transformedVertices;
    }

    @Override
    public void updateParamsOverride() {
        ArrayList<Vector3f> newTransformedVerts = new ArrayList<>();
        for(Vector3f vector : meshVertices) {
            Vector3f temp = VectorHelper.rotateAroundZ(vector, (float)Math.toRadians(getRotation().z));
            temp.x = temp.x * getScale().x + getTranslation().x;
            temp.y = temp.y * getScale().y + getTranslation().y;
            newTransformedVerts.add(temp);
        }

        synchronized (transformedVertices) {
            transformedVertices = newTransformedVerts;
        }
    }
}
