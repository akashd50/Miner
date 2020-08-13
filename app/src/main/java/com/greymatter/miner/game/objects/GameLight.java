package com.greymatter.miner.game.objects;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import javax.vecmath.Vector4f;

public class GameLight extends GameObject {
    private float radius, innerCutoff, outerCutoff, intensity;
    private Vector4f lightColor;
    public GameLight(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public GameLight(ObjId id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        super.shouldDraw(false);
        super.isActive(true);
        lightColor = new Vector4f();
        radius = 0f;
        innerCutoff = 0f;
        outerCutoff = 0f;
        intensity = 1f;
        addTag(Tag.GAME_LIGHT);
    }

    public GameLight attachTo(GameObjectWGL object) {
        object.addLight(this);
        return this;
    }

    public GameLight setColor(Vector4f color) {
        this.lightColor = color;
        return this;
    }

    public GameLight setColor(float r, float g, float b, float a) {
        this.lightColor.set(r,g,b,a);
        return this;
    }

    public GameLight setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public GameLight setInnerCutoff(float innerCutoff) {
        this.innerCutoff = innerCutoff;
        return this;
    }

    public GameLight setOuterCutoff(float outerCutoff) {
        this.outerCutoff = outerCutoff;
        return this;
    }

    public GameLight setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    @Override
    public GameLight shouldDraw(boolean shouldDraw) {
        shouldDraw = false;
        return this;
    }

    public float getIntensity() {
        return intensity;
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
