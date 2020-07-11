package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.ValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.RigidBody;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameObject {
    private ValueAnimator valueAnimator;
    private Drawable objectDrawable;
    private ArrayList<String> objectTags;
    private boolean shouldDraw;
    private String id;

    public GameObject(String id, Drawable drawable) {
        this.id = id;
        this.objectDrawable = drawable;
        this.objectTags = new ArrayList<>();
        shouldDraw = true;
    }

    public void onDrawFrame() {
        objectDrawable.onDrawFrame();
    }

    public void onFrameUpdate() {}

    public GameObject addTag(String tag) {
        this.objectTags.add(tag);
        return this;
    }

    public GameObject shouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
        return this;
    }

    public GameObject setValueAnimator(ValueAnimator valueAnimator) {
        this.valueAnimator = valueAnimator;
        return this;
    }

    //object movement
    public GameObject moveBy(Vector2f moveTo) {
        objectDrawable.getTransforms().translateBy(moveTo);
        return this;
    }

    public GameObject moveBy(float x, float y) {
        objectDrawable.getTransforms().translateBy(x,y);
        return this;
    }

    public GameObject moveBy(Vector3f moveTo) {
        objectDrawable.getTransforms().translateBy(moveTo);
        return this;
    }

    public GameObject moveTo(Vector2f moveTo) {
        objectDrawable.getTransforms().translateTo(moveTo);
        return this;
    }

    public GameObject moveTo(Vector3f moveTo) {
        objectDrawable.getTransforms().translateTo(moveTo);
        return this;
    }

    public GameObject moveTo(float x, float y) {
        objectDrawable.getTransforms().translateTo(x,y);
        return this;
    }

    public Vector3f getLocation() {
        return objectDrawable.getRigidBody().getTranslation();
    }

    public boolean hasTag(String tag) {
        return this.objectTags.contains(tag);
    }

    public String getId() {
        return this.id;
    }

    public Drawable getDrawable() {
        return objectDrawable;
    }

    public Transforms getTransforms() {
        return objectDrawable.getTransforms();
    }

    public RigidBody getRigidBody() {
        return objectDrawable.getRigidBody();
    }

    public int getNumTags() {return this.objectTags.size();}

    public boolean shouldDraw() {
        return shouldDraw;
    }

    public ValueAnimator getValueAnimator() {
        return valueAnimator;
    }

    public String toString() {
        return this.id;
    }

    //typecasting
    public GameBuilding asGameBuilding() {
        return (GameBuilding)this;
    }
}
