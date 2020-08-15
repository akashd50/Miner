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
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.shader.ShaderHelper;

public class RadialGradientRenderer extends Renderer {
    public RadialGradientRenderer() {
        super(ShaderContainer.get(ShaderDef.GRADIENT_SHADER));
    }

    @Override
    public void render(Camera camera, IGameObject gameObject) {
        RadialGradient drawable = gameObject.getDrawable().asRadialGradient();

        ShaderHelper.useProgram(getShader());
        ShaderHelper.setCameraProperties(getShader(), camera);
        ShaderHelper.clearLightProperties(getShader());

        drawable.getMaterial().setShaderProperties(getShader());

        GLBufferHelper.glBindVertexArray(drawable.getVertexArrayObject());

        ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, drawable.getTransforms().getModelMatrix());
        ShaderHelper.setUniformFloat(getShader(), ShaderConst.GRADIENT_MID_POINT, drawable.getMidPoint());
        ShaderHelper.setUniformFloat(getShader(), ShaderConst.GRADIENT_RADIUS, drawable.getRadius());
        ShaderHelper.setUniformVec3(getShader(), ShaderConst.TRANSLATION, drawable.getTransforms().getTranslation());

        GLES30.glDrawArrays(drawable.getShape().getRenderMode(), 0, drawable.getShape().getVerticesList().size());

        GLBufferHelper.glUnbindVertexArray();
    }
}
