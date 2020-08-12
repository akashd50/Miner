package com.greymatter.miner.opengl.objects.materials.textured;

import android.opengl.GLES30;

import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.helpers.TextureBuilder;
import com.greymatter.miner.opengl.objects.Texture;

public class StaticTexturedMaterial extends TexturedMaterial {
    private Texture mainTexture;
    private Texture lightTexture;

    public StaticTexturedMaterial(MaterialDef id) {
        super(id);
    }

    public StaticTexturedMaterial attachMainTexture(String diffTexPath) {
        if (diffTexPath.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, diffTexPath);
            TextureBuilder.finish(texture);
            mainTexture = texture;
        }
        return this;
    }

    public StaticTexturedMaterial attachLightTexture(String lightTexPath) {
        if (lightTexPath!=null && lightTexPath.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, lightTexPath);
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

