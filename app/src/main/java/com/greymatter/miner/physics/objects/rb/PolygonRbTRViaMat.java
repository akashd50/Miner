package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.helpers.VectorHelper;
import java.util.ArrayList;
import javax.vecmath.Vector3f;

public class PolygonRbTRViaMat extends PolygonRB {
    public PolygonRbTRViaMat(ObjId id, ArrayList<Vector3f> mesh) {
        super(id, mesh);
    }

    @Override
    public void updateParamsOverride() {
        float[] modelMat = getTransforms().getModelMatrix();
        ArrayList<Vector3f> meshVertices = getMeshVertices();
        ArrayList<Vector3f> transformedVertices = getTransformedVertices();
        for(int i=0;i<meshVertices.size();i++) {
            VectorHelper.multiply(transformedVertices.get(i), meshVertices.get(i), modelMat);
        }
    }
}
