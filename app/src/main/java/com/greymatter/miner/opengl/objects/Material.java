package com.greymatter.miner.opengl.objects;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.TextureBuilder;

import javax.vecmath.Vector3f;

public class Material
{
    private Texture diffuseTexture;
    private Texture roughnessTexture;

    private float shinniness;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;

    public Material(String diffTexName, String roughTexName) {
        if (diffTexName.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, Constants.TEXTURES+diffTexName);
            TextureBuilder.finish(texture);

            diffuseTexture = texture;
        }
        if (roughTexName.length() > 0) {
            Texture texture = TextureBuilder.create(GLES30.GL_TEXTURE_2D);
            TextureBuilder.attachImage(texture, Constants.TEXTURES+roughTexName);
            TextureBuilder.finish(texture);

            roughnessTexture = texture;
        }

        ambient = new Vector3f(1.0f,1.0f,1.0f);
        diffuse = new Vector3f(1.0f,1.0f,1.0f);
        specular = new Vector3f(0.5f,0.5f,0.5f);

        shinniness = 1.0f;
    }

    public Material() {
        diffuseTexture = new Texture();
        roughnessTexture = new Texture();
        ambient = new Vector3f(1.0f,1.0f,1.0f);
        diffuse = new Vector3f(1.0f,1.0f,1.0f);
        specular = new Vector3f(0.5f,0.5f,0.5f);
        shinniness = 1.0f;
    }

    public final Texture getDiffuseTexture() {
        return diffuseTexture;
    }

    public final Texture getRoughnessTexture() {
        return roughnessTexture;
    }

    public final float getShinniness() {
        return shinniness;
    }

    public final void setShinniness(float f) {
        this.shinniness = f;
    }

    public final Vector3f getDiffuse() {
        return this.diffuse;
    }

    public final Vector3f getSpecular() {
        return this.specular;
    }

    public final Vector3f getAmbient() {
        return this.ambient;
    }

    public final void setDiffuse(Vector3f diff) {
        this.diffuse = diff;
    }

    public final void setSpecular(Vector3f spec) {
        this.specular = spec;
    }

    public final void setAmbient(Vector3f amb) {
        this.ambient = amb;
    }

    public final boolean hasDiffuseTexture() {
        return this.diffuseTexture != null;
    }

    public final boolean hasRoughnessTexture() {
        return this.roughnessTexture != null;
    }
}

