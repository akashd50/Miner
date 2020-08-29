package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;

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
        Transforms transforms = getTransforms();
        ArrayList<Vector3f> newTransformedVerts = new ArrayList<>();
        for(Vector3f vector : meshVertices) {
            Vector3f temp = VectorHelper.copy(vector);
            temp.x = temp.x * transforms.getScale().x;
            temp.y = temp.y * transforms.getScale().y;
            temp = VectorHelper.rotateAroundZ(temp, (float)Math.toRadians(transforms.getRotation().z));
            temp.x += transforms.getTranslation().x;
            temp.y += transforms.getTranslation().y;

            newTransformedVerts.add(temp);
        }

        synchronized (transformedVertices) {
            transformedVertices = newTransformedVerts;
        }
       // transformByMat();
    }

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        return VectorHelper.isPointInPolygon(touchPoint, transformedVertices);
    }

    //    public void transformByMat() {
//        //getTransforms().applyLastTransformationsForced();
//        if(isStaticObject()) {
//            getTransforms().applyTransformationsForced();
//        }
//
//        float[] modelMat = getTransforms().getLastModelMatrix();
//        for(int i=0;i<meshVertices.size();i++) {
//            VectorHelper.multiply(transformedVertices.get(i), meshVertices.get(i), modelMat);
//        }
//    }
}
