package com.greymatter.miner.opengl.objects.materials.textured;

import android.opengl.GLES30;

import com.greymatter.miner.enums.definitions.MaterialDef;
import com.greymatter.miner.helpers.TextureBuilder;
import com.greymatter.miner.opengl.objects.Texture;

public class StaticTexturedMaterial extends TexturedMaterial {
    private Texture mainTexture;
    private Texture lightTexture;

    public StaticTexturedMaterial(MaterialDef id) {
        super(id);
    }

    public StaticTexturedMaterial attachMainTexture(String diffTexName) {
        if (diffTexName.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, diffTexName);
            TextureBuilder.finish(texture);
            mainTexture = texture;
        }
        return this;
    }

    public StaticTexturedMaterial attachLightTexture(String roughTexName) {
        if (roughTexName.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, roughTexName);
            TextureBuilder.finish(texture);
            lightTexture = texture;
        }
        return this;
    }

    @Override
    public Texture getActiveMainTexture() {
        return mainTexture;
    }

    @Override
    public Texture getActiveLightTexture() {
        return lightTexture;
    }

    @Override
    public boolean hasMainTexture() {
        return this.mainTexture != null;
    }

    @Override
    public boolean hasLightTexture() {
        return this.lightTexture != null;
    }
}

