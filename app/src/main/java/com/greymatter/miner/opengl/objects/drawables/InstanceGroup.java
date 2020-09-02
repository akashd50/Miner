package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.helpers.BufferHelper;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.loaders.enums.definitions.ShapeDef;
import com.greymatter.miner.opengl.objects.Transforms;

import java.util.ArrayList;
import javax.vecmath.Vector3f;

public class InstanceGroup extends Drawable {
    private ArrayList<Vector3f> instanceTranslation, instanceRotation, instanceScale;
    private float[] instanceTranslationArray, instanceRotationArray, instanceScaleArray;
    private int totalInstances, translationBuffer, rotationBuffer, scaleBuffer;
    private ArrayList<Transforms> instanceTransforms;
    private ArrayList<Instance> instances;
    private boolean transformsUpdated;
    public InstanceGroup(String id) {
        super(id);
        instanceRotation = new ArrayList<>();
        instanceScale = new ArrayList<>();
        instanceTranslation = new ArrayList<>();
        instanceTransforms = new ArrayList<>();
        instances = new ArrayList<>();

        setShape(ShapeDef.create(ShapeDef.SIMPLE_QUAD));
    }

    public InstanceGroup addInstance(Instance instance) {
        instance.setShape(getShape());

        instances.add(instance);
        addTransform(instance.getTransforms());

        totalInstances++;
        return this;
    }

    private void addTransform(Transforms transforms) {
        instanceTranslation.add(transforms.getTranslation());
        instanceRotation.add(transforms.getRotation());
        instanceScale.add(transforms.getScale());

        instanceTranslationArray = BufferHelper.vec3AsFloatArray(instanceTranslation);
        instanceRotationArray = BufferHelper.vec3AsFloatArray(instanceRotation);
        instanceScaleArray = BufferHelper.vec3AsFloatArray(instanceScale);

        int actualIndex = totalInstances * 3;
        updateTranslationBuffer(actualIndex, BufferHelper.subset(instanceTranslationArray, actualIndex, actualIndex + 3));
        updateRotationBuffer(actualIndex, BufferHelper.subset(instanceRotationArray, actualIndex, actualIndex + 3));
        updateScaleBuffer(actualIndex, BufferHelper.subset(instanceScaleArray, actualIndex, actualIndex + 3));
    }

    @Override
    public InstanceGroup build() {
        super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(getShape().getVerticesArray(), 3, getRenderer().getShader(), ShaderConst.IN_POSITION);
        int uvBuffer = GLBufferHelper.putDataIntoArrayBuffer(getShape().getUVsArray(), 2, getRenderer().getShader(), ShaderConst.IN_UV);

        translationBuffer = GLBufferHelper.setUpEmptyArrayBuffer(30, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_TRANSLATION);
        rotationBuffer = GLBufferHelper.setUpEmptyArrayBuffer(30, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_ROTATION);
        scaleBuffer = GLBufferHelper.setUpEmptyArrayBuffer(30, 3, getRenderer().getShader(), ShaderConst.IN_INSTANCE_SCALE);

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

    public InstanceGroup translateInstanceBy(int index, float x, float y, float z) {
        int actualIndex = index * 3;
        instanceTranslationArray[actualIndex] += x;
        instanceTranslationArray[actualIndex+1] += y;
        instanceTranslationArray[actualIndex+2] += z;
        //instanceTransforms.get(index).translateBy(x,y,z);
        updateTranslationBuffer(actualIndex, BufferHelper.subset(instanceTranslationArray, actualIndex, actualIndex + 3));
        return this;
    }

    public InstanceGroup translateInstanceTo(int index, float x, float y, float z) {
        int actualIndex = index * 3;
        instanceTranslationArray[actualIndex] = x;
        instanceTranslationArray[actualIndex+1] = y;
        instanceTranslationArray[actualIndex+2] = z;
        //instanceTransforms.get(index).translateTo(x,y,z);
        //instances.get(index).getTransforms().tra
        updateTranslationBuffer(actualIndex, BufferHelper.subset(instanceTranslationArray, actualIndex, actualIndex + 3));
        return this;
    }

    public InstanceGroup rotateInstanceBy(int index, float x, float y, float z) {
        int actualIndex = index * 3;
        instanceRotationArray[actualIndex] += x;
        instanceRotationArray[actualIndex+1] += y;
        instanceRotationArray[actualIndex+2] += z;
        //instanceTransforms.get(index).translateBy(x,y,z);
        updateRotationBuffer(actualIndex, BufferHelper.subset(instanceRotationArray, actualIndex, actualIndex + 3));
        return this;
    }

    public InstanceGroup rotateInstanceTo(int index, float x, float y, float z) {
        int actualIndex = index * 3;
        instanceRotationArray[actualIndex] = x;
        instanceRotationArray[actualIndex+1] = y;
        instanceRotationArray[actualIndex+2] = z;
        //instanceTransforms.get(index).translateTo(x,y,z);
        updateRotationBuffer(actualIndex, BufferHelper.subset(instanceRotationArray, actualIndex, actualIndex + 3));
        return this;
    }

    public InstanceGroup scaleInstanceBy(int index, float x, float y, float z) {
        int actualIndex = index * 3;
        instanceScaleArray[actualIndex] += x;
        instanceScaleArray[actualIndex+1] += y;
        instanceScaleArray[actualIndex+2] += z;
        //instanceTransforms.get(index).translateBy(x,y,z);
        updateScaleBuffer(actualIndex, BufferHelper.subset(instanceScaleArray, actualIndex, actualIndex + 3));
        return this;
    }

    public InstanceGroup scaleInstanceTo(int index, float x, float y, float z) {
        int actualIndex = index * 3;
        instanceScaleArray[actualIndex] = x;
        instanceScaleArray[actualIndex+1] = y;
        instanceScaleArray[actualIndex+2] = z;
        //instanceTransforms.get(index).translateTo(x,y,z);
        updateScaleBuffer(actualIndex, BufferHelper.subset(instanceScaleArray, actualIndex, actualIndex + 3));
        return this;
    }

    public InstanceGroup setTransforms(int index, Transforms transforms) {
        instanceTransforms.add(index, transforms);
        return this;
    }

    @Override
    public void onTransformsChanged() {
        transformsUpdated = true;
    }

    public void refreshBuffers() {
        if(transformsUpdated) {
            instanceTranslationArray = BufferHelper.vec3AsFloatArray(instanceTranslation);
            instanceRotationArray = BufferHelper.vec3AsFloatArray(instanceRotation);
            instanceScaleArray = BufferHelper.vec3AsFloatArray(instanceScale);

            updateTranslationBuffer(0, instanceTranslationArray);
            updateRotationBuffer(0, instanceRotationArray);
            updateScaleBuffer(0, instanceScaleArray);
            transformsUpdated = false;
        }
    }

    public Instance getInstance(int index) {
        return this.instances.get(index);
    }

    public int getTotalInstances() {
        return totalInstances;
    }
}
