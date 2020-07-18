package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.shader.ShaderHelper;

public class TextureEdgedPolygon extends Drawable {
    public TextureEdgedPolygon(ObjId id) {
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
