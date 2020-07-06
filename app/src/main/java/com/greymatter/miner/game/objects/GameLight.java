package com.greymatter.miner.game.objects;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class GameLight extends GameObject {
    private float radius, innerCutoff, outerCutoff;
    private Vector4f lightColor;
    private Vector2f lightOffset;
    public GameLight(Drawable drawable) {
        super(drawable.getId(), drawable);
        super.shouldDraw(false);
        initialize();
    }

    public GameLight(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        lightColor = new Vector4f();
        lightOffset = new Vector2f();
        radius = 0f;
        innerCutoff = 0f;
        outerCutoff = 0f;
    }

    public GameLight attachTo(GameBuilding building, Vector2f offset) {
        building.addLight(this);
        this.lightOffset = offset;
        moveTo(VectorHelper.toVector2f(building.getLocation()));
        moveBy(lightOffset);
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

    //object movement overrides
    @Override
    public GameLight moveTo(Vector2f moveTo) {
        moveTo.add(lightOffset);
        super.moveTo(moveTo);
        return this;
    }

    @Override
    public GameLight moveTo(Vector3f moveTo) {
        moveTo.add(new Vector3f(lightOffset.x, lightOffset.y, 0f));
        super.moveTo(moveTo);
        return this;
    }

    @Override
    public GameLight moveTo(float x, float y) {
        x+=lightOffset.x;
        y+=lightOffset.y;
        super.moveTo(x,y);
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
