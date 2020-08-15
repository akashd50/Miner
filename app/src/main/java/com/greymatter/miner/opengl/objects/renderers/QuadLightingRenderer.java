package com.greymatter.miner.opengl.objects.renderers;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.loaders.enums.definitions.ShaderDef;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.shader.ShaderHelper;

public class QuadLightingRenderer extends Renderer {
    public QuadLightingRenderer() {
        super(ShaderContainer.get(ShaderDef.LIGHTING_SHADER));
    }

    @Override
    public void render(Camera camera, IGameObject gameObject) {
        Drawable drawable = gameObject.getDrawable();

        ShaderHelper.useProgram(getShader());
        ShaderHelper.setCameraProperties(getShader(), camera);
        ShaderHelper.setLightProperties(getShader(), gameObject.asGameObjectWGL().getAllLights());

        drawable.getMaterial().setShaderProperties(getShader());

        GLBufferHelper.glBindVertexArray(drawable.getVertexArrayObject());
        ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, drawable.getTransforms().getModelMatrix());
        GLES30.glDrawArrays(drawable.getShape().getRenderMode(), 0, drawable.getShape().getVerticesList().size());
        GLBufferHelper.glUnbindVertexArray();
    }
}
