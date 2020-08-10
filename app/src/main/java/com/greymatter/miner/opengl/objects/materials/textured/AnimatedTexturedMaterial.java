package com.greymatter.miner.opengl.objects.materials.textured;

import android.opengl.GLES30;

import com.greymatter.miner.enums.definitions.MaterialDef;
import com.greymatter.miner.helpers.TextureBuilder;
import com.greymatter.miner.opengl.objects.Texture;
import com.greymatter.miner.animators.IntegerValueAnimator;

import java.util.ArrayList;

public class AnimatedTexturedMaterial extends TexturedMaterial {
    private ArrayList<Texture> diffuseTextureFrames;
    private IntegerValueAnimator intValueAnimator;
    public AnimatedTexturedMaterial(MaterialDef id) {
        super(id);
        diffuseTextureFrames = new ArrayList<>();
    }

    public AnimatedTexturedMaterial addMainTextureFrames(String dirPath, String[] files) {
        for (int i = 0; i < files.length; i++) {
            addDiffuseTextureFrame(dirPath +"/" + files[i]);
        }
        return this;
    }

    public AnimatedTexturedMaterial addDiffuseTextureFrame(String diffuseTexPath) {
        if (diffuseTexPath.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, diffuseTexPath);
            TextureBuilder.finish(texture);

            diffuseTextureFrames.add(texture);
        }
        return this;
    }

    public AnimatedTexturedMaterial setAnimationHandler(IntegerValueAnimator animationHandler) {
        this.intValueAnimator = animationHandler;
        return this;
    }

    @Override
    public boolean hasMainTexture() {
        return true;
    }

    @Override
    public boolean hasLightTexture() {
        return false;
    }

    @Override
    public Texture getActiveMainTexture() {
        return diffuseTextureFrames.get(intValueAnimator.update().getUpdatedInt());
    }

    @Override
    public Texture getActiveLightTexture() {
        return null;
    }

    public IntegerValueAnimator getAnimationHandler() {
        return intValueAnimator;
    }

    public Texture getFrame(int index) {
        return diffuseTextureFrames.get(index);
    }

    public ArrayList<Texture> getAllFrames() {
        return diffuseTextureFrames;
    }
}
