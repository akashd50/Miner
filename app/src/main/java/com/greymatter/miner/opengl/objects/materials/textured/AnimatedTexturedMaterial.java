package com.greymatter.miner.opengl.objects.materials.textured;

import android.opengl.GLES30;
import com.greymatter.miner.Res;
import com.greymatter.miner.helpers.TextureBuilder;
import com.greymatter.miner.opengl.objects.Texture;
import com.greymatter.miner.opengl.objects.IntegerValueAnimator;

import java.util.ArrayList;

public class AnimatedTexturedMaterial extends TexturedMaterial {
    private ArrayList<Texture> diffuseTextureFrames;
    private IntegerValueAnimator intValueAnimator;
    public AnimatedTexturedMaterial(String id) {
        super(id);
        diffuseTextureFrames = new ArrayList<>();
    }

    public AnimatedTexturedMaterial addDiffuseTextureFrame(String diffuseTexPath) {
        if (diffuseTexPath.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, Res.TEXTURES_F +diffuseTexPath);
            TextureBuilder.finish(texture);

            diffuseTextureFrames.add(texture);
        }
        return this;
    }

    public AnimatedTexturedMaterial withAnimationHandler(IntegerValueAnimator animationHandler) {
        this.intValueAnimator = animationHandler;
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
        return diffuseTextureFrames.get(intValueAnimator.update().getUpdatedInt());
    }

    @Override
    public Texture getActiveRoughnessTexture() {
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
