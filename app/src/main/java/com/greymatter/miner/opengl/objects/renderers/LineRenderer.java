package com.greymatter.miner.opengl.objects.renderers;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.loaders.enums.definitions.ShaderDef;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.shader.ShaderHelper;

public class LineRenderer extends Renderer {
    public LineRenderer() {
        super(ShaderContainer.get(ShaderDef.LINE_SHADER));
    }

    @Override
    public void render(Camera camera, IGameObject gameObject) {
        Drawable drawable = gameObject.getDrawable();

        ShaderHelper.useProgram(getShader());
        ShaderHelper.setCameraProperties(getShader(), camera);
        drawable.getMaterial().setShaderProperties(getShader());

        GLBufferHelper.glBindVertexArray(drawable.getVertexArrayObject());
        ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, drawable.getTransforms().getModelMatrix());
        GLES30.glLineWidth(10f);
        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, drawable.getShape().getVerticesList().size());
        GLBufferHelper.glUnbindVertexArray();
    }
}
