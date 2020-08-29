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
    private int totalInstances, translationBuffer, rotationBuffer, scaleBuffer;
    public Instanced(String id) {
        super(id);
        instanceRotation = new ArrayList<>();
        instanceScale = new ArrayList<>();
        instanceTranslation = new ArrayList<>();

        setShape(ShapeDef.create(ShapeDef.SIMPLE_QUAD));
    }

    public Instanced addInstance() {
        instanceTranslation.add(new Vector3f());
        instanceRotation.add(new Vector3f());
        instanceScale.add(new Vector3f(1f,1f,1f));

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
        translationBuffer = GLBufferHelper.putDataIntoArrayBuffer(instanceTranslationArray, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_TRANSLATION);
        rotationBuffer = GLBufferHelper.putDataIntoArrayBuffer(instanceRotationArray, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_ROTATION);
        scaleBuffer = GLBufferHelper.putDataIntoArrayBuffer(instanceScaleArray, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_SCALE);
        int uvBuffer = GLBufferHelper.putDataIntoArrayBuffer(getShape().getUVsArray(), 2, getRenderer().getShader(), ShaderConst.IN_UV);
        GLBufferHelper.glVertexAttributeDivisor(getRenderer().getShader(), ShaderConst.IN_INSTANCE_TRANSLATION, 1);
        GLBufferHelper.glVertexAttributeDivisor(getRenderer().getShader(), ShaderConst.IN_INSTANCE_ROTATION, 1);
        GLBufferHelper.glVertexAttributeDivisor(getRenderer().getShader(), ShaderConst.IN_INSTANCE_SCALE, 1);

        GLBufferHelper.glUnbindVertexArray();
        return this;
    }

    private void updateTranslationBuffer(int startIndex, float[] data) {
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        GLBufferHelper.updateSubBufferData(translationBuffer, startIndex, data);
        GLBufferHelper.glUnbindVertexArray();
    }

    private void updateRotationBuffer(int startIndex, float[] data) {
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        GLBufferHelper.updateSubBufferData(rotationBuffer, startIndex, data);
        GLBufferHelper.glUnbindVertexArray();
    }

    private void updateScaleBuffer(int startIndex, float[] data) {
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        GLBufferHelper.updateSubBufferData(scaleBuffer, startIndex, data);
        GLBufferHelper.glUnbindVertexArray();
    }

    public Instanced translateInstanceBy(int index, float x, float y, float z) {
        int actualIndex = index * 3;
        instanceTranslationArray[actualIndex] += x;
        instanceTranslationArray[actualIndex+1] += y;
        instanceTranslationArray[actualIndex+2] += z;
        updateTranslationBuffer(actualIndex, BufferHelper.subset(instanceTranslationArray, actualIndex, actualIndex + 3));
        return this;
    }

    public Instanced translateInstanceTo(int index, float x, float y, float z) {
        int actualIndex = index * 3;
        instanceTranslationArray[actualIndex] = x;
        instanceTranslationArray[actualIndex+1] = y;
        instanceTranslationArray[actualIndex+2] = z;
        updateTranslationBuffer(actualIndex, BufferHelper.subset(instanceTranslationArray, actualIndex, actualIndex + 3));
        return this;
    }

    public int getTotalInstances() {
        return totalInstances;
    }
}
