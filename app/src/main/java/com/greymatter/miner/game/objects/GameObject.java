package com.greymatter.miner.game.objects;

import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.animators.ValueAnimator;
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
    private int objectLevel;
    public GameObject(String id, Drawable drawable) {
        this.id = id;
        this.objectDrawable = drawable;
        this.objectTags = new ArrayList<>();
        shouldDraw = true;
        objectLevel = 1;
    }

    public void onDrawFrame() {
        objectDrawable.onDrawFrame();
    }

    public void onFrameUpdate() {

    }

    public void upgrade() {
        objectLevel++;
    }

    public void upgrade(int newLevel) {
        objectLevel = newLevel;
    }

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

    public GameObject moveBy(float x, float y, float z) {
        objectDrawable.getTransforms().translateBy(x,y,z);
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

    public GameObject moveTo(float x, float y, float z) {
        objectDrawable.getTransforms().translateTo(x,y,z);
        return this;
    }

    public GameObject scaleTo(float x, float y) {
        objectDrawable.getTransforms().scaleTo(x,y);
        return this;
    }

    public GameObject scaleBy(float x, float y) {
        objectDrawable.getTransforms().scaleBy(x,y);
        return this;
    }

    public int getObjectLevel() {
        return objectLevel;
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

    public int getNumTags() {
        return this.objectTags.size();
    }

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

    public ResourceBlock asResourceBlock() {
        return (ResourceBlock) this;
    }
}
