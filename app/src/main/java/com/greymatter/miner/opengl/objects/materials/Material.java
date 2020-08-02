package com.greymatter.miner.opengl.objects.materials;

import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.opengl.objects.materials.colored.ColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.TexturedMaterial;
import com.greymatter.miner.opengl.shader.Shader;

public abstract class Material {
    private MatId id;
    private boolean shouldSetOnDrawFrame;
    public Material(MatId id) {
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

    public MatId getId() {
        return id;
    }
}
