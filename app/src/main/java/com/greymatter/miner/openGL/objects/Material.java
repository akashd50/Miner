package com.greymatter.miner.openGL.objects;

import android.opengl.GLES30;

import javax.vecmath.Vector3d;

public class Material
{
    private Texture diffuseTexture;
    private Texture roughnessTexture;
    private float shinniness;
    private Vector3d ambient;
    private Vector3d diffuse;
    private Vector3d specular;
    public Material(String diffTPath, String roughTPath) {
		/*if(!diffTPath.empty()) diffuseTexture = new Texture(diffTPath);
		if(!roughTPath.empty()) roughnessTexture = new Texture(roughTPath);*/

        if (diffTPath.length() > 0) {
            TextureBuilder tb = new TextureBuilder();
            tb = tb.init(GLES30.GL_TEXTURE_2D);
            tb = tb.attachImageTexture(diffTPath);

            diffuseTexture = tb.finish();
        }
        if (roughTPath.length() > 0) {
            TextureBuilder tb = new TextureBuilder();
            tb = tb.init(GLES30.GL_TEXTURE_2D);
            tb = tb.attachImageTexture(roughTPath);

            roughnessTexture = tb.finish();
        }

        ambient = new Vector3d(1.0,1.0,1.0);
        diffuse = new Vector3d(1.0,1.0,1.0);
        specular = new Vector3d(0.5,0.5,0.5);

        shinniness = 1.0f;
    }

    public Material() {
        diffuseTexture = new Texture();
        roughnessTexture = new Texture();
        ambient = new Vector3d(1.0,1.0,1.0);
        diffuse = new Vector3d(1.0,1.0,1.0);
        specular = new Vector3d(1.0,1.0,1.0);

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

    public final Vector3d getDiffuse() {
        return this.diffuse;
    }

    public final Vector3d getSpecular() {
        return this.specular;
    }

    public final Vector3d getAmbient() {
        return this.ambient;
    }

    public final void setDiffuse(Vector3d diff) {
        this.diffuse = diff;
    }

    public final void setSpecular(Vector3d spec) {
        this.specular = spec;
    }

    public final void setAmbient(Vector3d amb) {
        this.ambient = amb;
    }

    public final boolean hasDiffuseTexture() {
        return this.diffuseTexture != null;
    }

    public final boolean hasRoughnessTexture() {
        return this.roughnessTexture != null;
    }
}

