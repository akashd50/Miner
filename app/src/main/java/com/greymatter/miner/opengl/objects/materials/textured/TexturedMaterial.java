package com.greymatter.miner.opengl.objects.materials.textured;

import com.greymatter.miner.opengl.objects.Texture;
import com.greymatter.miner.opengl.objects.materials.Material;

public abstract class TexturedMaterial extends Material {
    public TexturedMaterial(String id) {
        super(id);
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
