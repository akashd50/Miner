package com.greymatter.miner.opengl.objects.materials;

import com.greymatter.miner.opengl.objects.materials.colored.ColorMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.TexturedMaterial;

public class Material {
    private String _id;
    public Material(String id) {
        _id = id;
    }

    public TexturedMaterial asTexturedMaterial() {
        return (TexturedMaterial) this;
    }

    public ColorMaterial asColorMaterial() {
        return (ColorMaterial) this;
    }

    public String getId() {
        return _id;
    }
}
