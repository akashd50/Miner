package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.Path;
import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.helpers.BufferHelper;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.loaders.enums.definitions.ShapeDef;
import com.greymatter.miner.opengl.objects.materials.colored.StaticColoredMaterial;
import com.greymatter.miner.opengl.objects.renderers.LineRenderer;

import java.util.ArrayList;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Line extends Drawable {
    private static final int maxVertices = 30;
    public Line(String id) {
        super(id);
        setMaterial(new StaticColoredMaterial(MaterialDef.DEFAULT_COLORED));
        getMaterial().asColoredMaterial().addColor(ShaderConst.U_COLOR, new Vector4f(1f,0f,1f,1f));
        setRenderer(new LineRenderer());
        setShape(new Shape(ShapeDef.DEFAULT).build());
    }

    public Line addVertex(Vector3f toAdd) {
        getShape().addVertex(toAdd).build();
        updateVertexBufferData(0,getShape().getVerticesArray());
        return this;
    }

    public Line setVertex(int i, Vector3f toAdd) {
        getShape().getVerticesList().set(i,toAdd);
        getShape().build();
        updateVertexBufferData(0,getShape().getVerticesArray());
        return this;
    }

    public Line setColor(Vector4f color) {
        getMaterial().asColoredMaterial().addColor(ShaderConst.U_COLOR, color);
        return this;
    }

    public Line addVertices(ArrayList<Vector3f> toAdd) {
        getShape().addVertices(toAdd);
        getShape().build();
        updateVertexBufferData(0,getShape().getVerticesArray());
        return this;
    }

    public Line setVertices(ArrayList<Vector3f> newData) {
        getShape().getVerticesList().clear();
        getShape().addVertices(newData);
        getShape().build();
        updateVertexBufferData(0,getShape().getVerticesArray());
        return this;
    }

    public Line build() {
        super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        int vertexBuffer = GLBufferHelper.setUpEmptyArrayBuffer(maxVertices  * Path.SIZE_OF_FLOAT * 3, 3, getRenderer().getShader(), ShaderConst.IN_POSITION);
        setVertexBufferObject(vertexBuffer);

        GLBufferHelper.glUnbindVertexArray();

        updateVertexBufferData(0,getShape().getVerticesArray());
        return this;
    }

    private void updateVertexBufferData(int startIndex, float[] data) {
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        GLBufferHelper.updateSubBufferData(getVertexBufferObject(), startIndex, data);
        GLBufferHelper.glUnbindVertexArray();
    }
}
