package com.greymatter.miner.opengl.objects.materials;

import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.opengl.objects.materials.colored.ColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.TexturedMaterial;

public class Material {
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

    public TexturedMaterial asTexturedMaterial() {
        return (TexturedMaterial) this;
    }

    public ColoredMaterial asColoredMaterial() {
        return (ColoredMaterial) this;
    }

    public boolean shouldSetOnDrawFrame() {
        return shouldSetOnDrawFrame;
    }

    public MatId getId() {
        return id;
    }
}
