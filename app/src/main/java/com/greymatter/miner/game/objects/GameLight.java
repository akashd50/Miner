package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class GameLight extends GameObject {
    private float radius, innerCutoff, outerCutoff;
    private Vector4f lightColor;
    private Vector3f lightOffset;
    public GameLight(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public GameLight(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        lightColor = new Vector4f();
        lightOffset = new Vector3f();
        radius = 0f;
        innerCutoff = 0f;
        outerCutoff = 0f;
    }

    public GameLight attachTo(GameBuilding building, Vector3f offset) {
        building.addLight(this);
        this.lightOffset = offset;
        return this;
    }

    public GameLight withColor(Vector4f color) {
        this.lightColor = color;
        return this;
    }

    public GameLight withColor(float r, float g, float b, float a) {
        this.lightColor.set(r,g,b,a);
        return this;
    }

    public GameLight withRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public GameLight withInnerCutoff(float innerCutoff) {
        this.innerCutoff = innerCutoff;
        return this;
    }

    public GameLight withOuterCutoff(float outerCutoff) {
        this.outerCutoff = outerCutoff;
        return this;
    }

    public GameLight withLocation(Vector2f location) {
        getCollider().translateTo(location);
        return this;
    }

    public float getInnerCutoff() {
        return innerCutoff;
    }

    public float getOuterCutoff() {
        return outerCutoff;
    }

    public float getRadius() {
        return radius;
    }

    public Vector4f getLightColor() {
        return lightColor;
    }
}
