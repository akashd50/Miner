package com.greymatter.miner.opengl.objects.drawables.textureedged;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class TextureEdgedPolygon extends Drawable {
    public TextureEdgedPolygon(String id) {
        super(id);
    }

    public void onDrawFrame() {
        super.onDrawFrame();

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, getTransforms().getModelMatrix());
        ShaderHelper.setMaterialProperties(getShader(), getMaterial());

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, getShape().getVerticesList().size());

        GLBufferHelper.glUnbindVertexArray();
    }

    public TextureEdgedPolygon build() {
        int vertexArrayObj = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArrayObj);
        int vertexBufferObj = GLBufferHelper.putDataIntoArrayBuffer(getShape().getVerticesArray(), 3, super.getShader(), ShaderConst.IN_POSITION);
        int uvBufferObj = GLBufferHelper.putDataIntoArrayBuffer(getShape().getUVsArray(),2,super.getShader(), ShaderConst.IN_UV);
        GLBufferHelper.glUnbindVertexArray();

        super.setVertexArrayObject(vertexArrayObj);
        super.setVertexBufferObject(vertexBufferObj);
        return this;
    }

    @Override
    public TextureEdgedPolygon attachPolygonTouchChecker() {
        return this;
    }

    @Override
    public TextureEdgedPolygon attachPolygonCollider() {
        return this;
    }
}
