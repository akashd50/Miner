package com.greymatter.miner.opengl.objects.materials.textured;

import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.opengl.objects.Texture;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.shader.ShaderHelper;

public abstract class TexturedMaterial extends Material {
    public TexturedMaterial(MatId id) {
        super(id);
    }

    @Override
    protected void setShaderPropertiesHelper(Shader shader) {
        ShaderHelper.setTexturedMaterialProperties(shader, this);
    }

    public abstract boolean hasDiffuseTexture();
    public abstract boolean hasRoughnessTexture();
    public abstract Texture getActiveDiffuseTexture();
    public abstract Texture getActiveRoughnessTexture();

    public StaticTexturedMaterial asStaticMaterial() {
        return (StaticTexturedMaterial)this;
    }

    public AnimatedTexturedMaterial asAnimatedMaterial() {
        return (AnimatedTexturedMaterial) this;
    }
}
