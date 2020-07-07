package com.greymatter.miner.opengl.objects.materials.textured;

import android.opengl.GLES30;
import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.TextureBuilder;
import com.greymatter.miner.opengl.objects.Texture;
import com.greymatter.miner.opengl.objects.materials.AnimationHandler;

import java.util.ArrayList;

public class AnimatedTexturedMaterial extends TexturedMaterial {
    private ArrayList<Texture> diffuseTextureFrames;
    private AnimationHandler _animationHandler;
    public AnimatedTexturedMaterial(String id) {
        super(id);
        diffuseTextureFrames = new ArrayList<>();
    }

    public AnimatedTexturedMaterial addDiffuseTextureFrame(String diffuseTexPath) {
        if (diffuseTexPath.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, Constants.TEXTURES_F +diffuseTexPath);
            TextureBuilder.finish(texture);

            diffuseTextureFrames.add(texture);
        }
        return this;
    }

    public AnimatedTexturedMaterial withAnimationHandler(AnimationHandler animationHandler) {
        this._animationHandler = animationHandler;
        return this;
    }

    @Override
    public boolean hasDiffuseTexture() {
        return true;
    }

    @Override
    public boolean hasRoughnessTexture() {
        return false;
    }

    @Override
    public Texture getActiveDiffuseTexture() {
        return diffuseTextureFrames.get(_animationHandler.updateActiveFrame());
    }

    @Override
    public Texture getActiveRoughnessTexture() {
        return null;
    }

    public AnimationHandler getAnimationHandler() {
        return _animationHandler;
    }

    public Texture getFrame(int index) {
        return diffuseTextureFrames.get(index);
    }

    public ArrayList<Texture> getAllFrames() {
        return diffuseTextureFrames;
    }
}
