package com.greymatter.miner.opengl.objects.materials;

import android.opengl.GLES30;
import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.TextureBuilder;
import com.greymatter.miner.opengl.objects.Texture;
import java.util.ArrayList;

public class AnimatedMaterial extends Material {
    private ArrayList<Texture> textureFrames;
    public AnimatedMaterial(String id, String mainDiffuseTexPath) {
        super(id, mainDiffuseTexPath);

        textureFrames = new ArrayList<>();
        textureFrames.add(getDiffuseTexture());
    }

    public void addDiffuseTextureFrame(String diffuseTexPath) {
        if (diffuseTexPath.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, Constants.TEXTURES+diffuseTexPath);
            TextureBuilder.finish(texture);

            textureFrames.add(texture);
        }
    }

    public Texture getFrame(int index) {
        return textureFrames.get(index);
    }

    public ArrayList<Texture> getAllFrames() {
        return textureFrames;
    }
}
