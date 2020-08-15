package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.shader.ShaderHelper;

public class TextureEdgedPolygon extends Drawable {
    public TextureEdgedPolygon(ObjId id) {
        super(id);
    }

    public TextureEdgedPolygon build() {
        int vertexArrayObj = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArrayObj);
        int vertexBufferObj = GLBufferHelper.putDataIntoArrayBuffer(getShape().getVerticesArray(), 3, getRenderer().getShader(), ShaderConst.IN_POSITION);
        int uvBufferObj = GLBufferHelper.putDataIntoArrayBuffer(getShape().getUVsArray(),2,getRenderer().getShader(), ShaderConst.IN_UV);
        GLBufferHelper.glUnbindVertexArray();

        super.setVertexArrayObject(vertexArrayObj);
        super.setVertexBufferObject(vertexBufferObj);
        return this;
    }
}
