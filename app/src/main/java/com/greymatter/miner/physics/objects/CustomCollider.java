package com.greymatter.miner.physics.objects;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class CustomCollider extends Collider {
    private ArrayList<Vector3f> meshVertices, transformedVertices;

    public CustomCollider(ArrayList<Vector3f> mesh) {
        super();
        this.meshVertices = mesh;
        transformedVertices = new ArrayList<>();
    }

    @Override
    public void updateParams() {
        transformedVertices.clear();
        for(Vector3f vector : meshVertices) {
            Vector3f temp = new Vector3f(vector);
            temp.x = temp.x * getScale().x + getTranslation().x;
            temp.y = temp.y * getScale().y + getTranslation().y;
            transformedVertices.add(temp);
        }
    }
}
