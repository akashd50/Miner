package com.greymatter.miner.game.objects;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class GameLight extends GameObject {
    private float radius, innerCutoff, outerCutoff;
    private Vector4f lightColor;
    public GameLight(Drawable drawable) {
        super(drawable.getId(), drawable);
        super.shouldDraw(false);
        initialize();
    }

    public GameLight(ObjId id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        lightColor = new Vector4f();
        radius = 0f;
        innerCutoff = 0f;
        outerCutoff = 0f;
    }

    public GameLight attachTo(GameBuilding building) {
        building.addLight(this);
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
