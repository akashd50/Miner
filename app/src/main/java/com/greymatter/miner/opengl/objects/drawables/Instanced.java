package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.helpers.BufferHelper;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.loaders.enums.definitions.ShapeDef;
import java.util.ArrayList;
import javax.vecmath.Vector3f;

public class Instanced extends Drawable {
    private ArrayList<Vector3f> instanceTranslation, instanceRotation, instanceScale;
    private float[] instanceTranslationArray, instanceRotationArray, instanceScaleArray;
    private int totalInstances;
    public Instanced(String id) {
        super(id);
        instanceRotation = new ArrayList<>();
        instanceScale = new ArrayList<>();
        instanceTranslation = new ArrayList<>();

        setShape(ShapeDef.create(ShapeDef.SIMPLE_QUAD));
    }

    public Instanced addSquare() {
        if(totalInstances>0) {
            Vector3f prevTrans = instanceTranslation.get(totalInstances-1);
            instanceTranslation.add(new Vector3f(prevTrans.x+2.1f,prevTrans.y, prevTrans.z));
        }else{
            instanceTranslation.add(new Vector3f(0f,5f,17f));
        }
        instanceRotation.add(new Vector3f(0f,0f,0f));
        instanceScale.add(new Vector3f((float)Math.random(),(float)Math.random(),1f));

        instanceTranslationArray = BufferHelper.vec3AsFloatArray(instanceTranslation);
        instanceRotationArray = BufferHelper.vec3AsFloatArray(instanceRotation);
        instanceScaleArray = BufferHelper.vec3AsFloatArray(instanceScale);

        totalInstances++;
        return this;
    }

    @Override
    public Instanced build() {
        super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(getShape().getVerticesArray(), 3, getRenderer().getShader(), ShaderConst.IN_POSITION);
        int instancedTranslation = GLBufferHelper.putDataIntoArrayBuffer(instanceTranslationArray, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_TRANSLATION);
        int instancedRotation = GLBufferHelper.putDataIntoArrayBuffer(instanceRotationArray, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_ROTATION);
        int instancedScale = GLBufferHelper.putDataIntoArrayBuffer(instanceScaleArray, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_SCALE);
        int uvBuffer = GLBufferHelper.putDataIntoArrayBuffer(getShape().getUVsArray(), 2, getRenderer().getShader(), ShaderConst.IN_UV);
        GLBufferHelper.glVertexAttributeDivisor(getRenderer().getShader(), ShaderConst.IN_INSTANCE_TRANSLATION, 1);
        GLBufferHelper.glVertexAttributeDivisor(getRenderer().getShader(), ShaderConst.IN_INSTANCE_ROTATION, 1);
        GLBufferHelper.glVertexAttributeDivisor(getRenderer().getShader(), ShaderConst.IN_INSTANCE_SCALE, 1);

        GLBufferHelper.glUnbindVertexArray();
        return this;
    }

    public int getTotalInstances() {
        return totalInstances;
    }
}
