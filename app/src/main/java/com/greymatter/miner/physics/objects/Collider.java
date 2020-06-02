package com.greymatter.miner.physics.objects;

import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.objects.CircleCollider;
import com.greymatter.miner.physics.objects.CustomCollider;

import javax.vecmath.Vector3f;

public abstract class Collider {
    private Vector3f translation, rotation, scale;
    private float mass;
    private Drawable drawable;
    public Collider() {
        this.translation = new Vector3f(0f,0f,0f);
        this.rotation = new Vector3f(0f,0f,0f);
        this.scale = new Vector3f(1.0f,1.0f, 1.0f);
    }

    public CircleCollider asCircleCollider() {
        return (CircleCollider)this;
    }

    public CustomCollider asCustomColloder() {
        return (CustomCollider) this;
    }

    public void scaleTo(Vector3f newScale) {
        this.scale = newScale;
        this.updateParams();
    }

    public void scaleBy(Vector3f newScale) {
        this.scale.add(newScale);
        this.updateParams();
    }

    public void translateTo(Vector3f position) {
        this.translation = position;
        this.updateParams();
    }

    public void translateBy(Vector3f translation) {
        this.translation.add(translation);
        this.updateParams();
    }

    public void rotateTo(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void rotateBy(Vector3f rotation) {
        this.rotation.add(rotation);
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        if(this.drawable.getCollider()==null) this.drawable.setCollider(this);
    }

    public abstract void updateParams();
}
