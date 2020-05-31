package com.greymatter.miner.physics;

import javax.vecmath.Vector3f;

public abstract class Collider {
    private Vector3f translation, rotation, scale;

    public Collider() {
        this.translation = new Vector3f(0f,0f,0f);
        this.rotation = new Vector3f(0f,0f,0f);
        this.scale = new Vector3f(1.0f,1.0f, 1.0f);
    }

    public void scaleTo(Vector3f newScale) {
        this.scale = newScale;
    }

    public void scaleBy(Vector3f newScale) {
        this.scale.add(newScale);
    }

    public void translateTo(Vector3f position) {
        this.translation = position;
    }

    public void translateBy(Vector3f translation) {
        this.translation.add(translation);
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
}
