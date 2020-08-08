package com.greymatter.miner.opengl.objects.materials;

import com.greymatter.miner.enums.definitions.MaterialDef;
import com.greymatter.miner.opengl.objects.materials.colored.ColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.TexturedMaterial;
import com.greymatter.miner.opengl.shader.Shader;

public abstract class Material {
    private MaterialDef id;
    private boolean shouldSetOnDrawFrame;
    public Material(MaterialDef id) {
        this.id = id;
        shouldSetOnDrawFrame = true;
    }

    public Material setShouldSetOnDrawFrame(boolean shouldSetOnDrawFrame) {
        this.shouldSetOnDrawFrame = shouldSetOnDrawFrame;
        return this;
    }

    public void setShaderProperties(Shader shader) {
        if(shouldSetOnDrawFrame) {
            setShaderPropertiesHelper(shader);
        }
    }

    public TexturedMaterial asTexturedMaterial() {
        return (TexturedMaterial) this;
    }

    public AnimatedTexturedMaterial asAnimatedTexturedMaterial() {
        return (AnimatedTexturedMaterial) this;
    }

    public ColoredMaterial asColoredMaterial() {
        return (ColoredMaterial) this;
    }

    public boolean shouldSetOnDrawFrame() {
        return shouldSetOnDrawFrame;
    }

    protected abstract void setShaderPropertiesHelper(Shader shader);

    public MaterialDef getId() {
        return id;
    }
}
