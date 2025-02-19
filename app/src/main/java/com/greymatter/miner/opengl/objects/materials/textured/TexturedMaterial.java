package com.greymatter.miner.opengl.objects.materials.textured;

import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.opengl.objects.Texture;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.shader.ShaderHelper;

public abstract class TexturedMaterial extends Material {
    public TexturedMaterial(MaterialDef id) {
        super(id);
    }

    @Override
    protected void setShaderPropertiesHelper(Shader shader) {
        ShaderHelper.setTexturedMaterialProperties(shader, this);
    }

    public abstract boolean hasMainTexture();
    public abstract boolean hasLightTexture();
    public abstract Texture getActiveMainTexture();
    public abstract Texture getActiveLightTexture();

    public StaticTexturedMaterial asStaticMaterial() {
        return (StaticTexturedMaterial)this;
    }

    public AnimatedTexturedMaterial asAnimatedMaterial() {
        return (AnimatedTexturedMaterial) this;
    }
}
