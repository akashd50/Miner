package com.greymatter.miner.opengl.objects.materials;

import com.greymatter.miner.opengl.objects.Texture;

public abstract class Material {
    private String _id;
    public Material(String id) {
        _id = id;
    }

    public abstract boolean hasDiffuseTexture();
    public abstract boolean hasRoughnessTexture();
    public abstract Texture getActiveDiffuseTexture();
    public abstract Texture getActiveRoughnessTexture();

    public StaticMaterial asStaticMaterial() {
        return (StaticMaterial)this;
    }

    public AnimatedMaterial asAnimatedMaterial() {
        return (AnimatedMaterial) this;
    }

    public String getId() {
        return _id;
    }
}
