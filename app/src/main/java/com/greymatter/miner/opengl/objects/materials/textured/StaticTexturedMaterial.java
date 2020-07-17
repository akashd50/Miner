package com.greymatter.miner.opengl.objects.materials.textured;

import android.opengl.GLES30;
import com.greymatter.miner.Path;
import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.helpers.TextureBuilder;
import com.greymatter.miner.opengl.objects.Texture;

import java.util.ArrayList;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class StaticTexturedMaterial extends TexturedMaterial {
    private Texture diffuseTexture;
    private Texture roughnessTexture;

    private float shinniness;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;

    private ArrayList<Vector4f> colors;

    public StaticTexturedMaterial(MatId id) {
        super(id);
        ambient = new Vector3f(1.0f,1.0f,1.0f);
        diffuse = new Vector3f(1.0f,1.0f,1.0f);
        specular = new Vector3f(0.5f,0.5f,0.5f);
        shinniness = 1.0f;
    }

    public StaticTexturedMaterial attachDiffuseTexture(String diffTexName) {
        if (diffTexName.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, Path.TEXTURES_F +diffTexName);
            TextureBuilder.finish(texture);
            diffuseTexture = texture;
        }
        return this;
    }

    public StaticTexturedMaterial attachRoughnessTexture(String roughTexName) {
        if (roughTexName.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, Path.TEXTURES_F +roughTexName);
            TextureBuilder.finish(texture);
            roughnessTexture = texture;
        }
        return this;
    }

    @Override
    public Texture getActiveDiffuseTexture() {
        return diffuseTexture;
    }

    @Override
    public Texture getActiveRoughnessTexture() {
        return roughnessTexture;
    }

    public float getShinniness() {
        return shinniness;
    }

    public void setShinniness(float f) {
        this.shinniness = f;
    }

    public Vector3f getDiffuse() {
        return this.diffuse;
    }

    public Vector3f getSpecular() {
        return this.specular;
    }

    public Vector3f getAmbient() {
        return this.ambient;
    }

    public void setDiffuse(Vector3f diff) {
        this.diffuse = diff;
    }

    public void setSpecular(Vector3f spec) {
        this.specular = spec;
    }

    public void setAmbient(Vector3f amb) {
        this.ambient = amb;
    }

    @Override
    public boolean hasDiffuseTexture() {
        return this.diffuseTexture != null;
    }

    @Override
    public boolean hasRoughnessTexture() {
        return this.roughnessTexture != null;
    }
}

