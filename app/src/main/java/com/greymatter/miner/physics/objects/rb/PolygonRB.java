package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.helpers.VectorHelper;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class PolygonRB extends RigidBody {
    private ArrayList<Vector3f> meshVertices, transformedVertices;

    public PolygonRB(ArrayList<Vector3f> mesh) {
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
            Vector3f temp = VectorHelper.copy(vector);
            temp.x = temp.x * getScale().x;
            temp.y = temp.y * getScale().y;
            temp = VectorHelper.rotateAroundZ(temp, (float)Math.toRadians(getRotation().z));
            temp.x += getTranslation().x;
            temp.y += getTranslation().y;

            newTransformedVerts.add(temp);
        }

        synchronized (transformedVertices) {
            transformedVertices = newTransformedVerts;
        }
    }
}
