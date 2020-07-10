package com.greymatter.miner.opengl.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.Collider;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Transforms {
    private Vector3f translation, rotation, scale;
    private Drawable linkedDrawable;
    private Collider linkedCollider;
    private boolean hasChanged;
    public Transforms() {
        translation = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1f,1f,1f);
    }

    public Transforms scaleTo(Vector3f newScale) {
        this.scale.set(newScale);
        onTransformsChanged();
        return this;
    }

    public Transforms scaleBy(Vector3f newScale) {
        this.scale.add(newScale);
        onTransformsChanged();
        return this;
    }

    public Transforms scaleTo(float x, float y, float z) {
        this.scale.set(x,y,z);
        onTransformsChanged();
        return this;
    }

    public Transforms scaleBy(float x, float y, float z) {
        this.scale.x+=x;
        this.scale.y+=y;
        this.scale.z+=z;
        onTransformsChanged();
        return this;
    }

    public Transforms translateTo(Vector3f position) {
        this.translation.set(position);
        onTransformsChanged();
        return this;
    }

    public Transforms translateBy(Vector3f translation) {
        this.translation.add(translation);
        onTransformsChanged();
        return this;
    }

    public Transforms translateTo(Vector2f position) {
        this.translation.x = position.x;
        this.translation.y = position.y;
        onTransformsChanged();
        return this;
    }

    public Transforms translateBy(Vector2f translation) {
        this.translation.x += translation.x;
        this.translation.y += translation.y;
        onTransformsChanged();
        return this;
    }

    public Transforms translateTo(float x, float y) {
        this.translation.x = x;
        this.translation.y = y;
        onTransformsChanged();
        return this;
    }

    public Transforms translateBy(float x, float y) {
        this.translation.x += x;
        this.translation.y += y;
        onTransformsChanged();
        return this;
    }

    //rotation
    public Transforms rotateTo(Vector3f rotation) {
        this.rotation.set(rotation);
        restrictRotationRange();
        onTransformsChanged();
        return this;
    }

    public Transforms rotateBy(Vector3f rotation) {
        this.rotation.add(rotation);
        restrictRotationRange();
        onTransformsChanged();
        return this;
    }

    public Transforms rotateBy(float x, float y, float z) {
        this.rotation.x+=x;
        this.rotation.y+=y;
        this.rotation.z+=z;
        restrictRotationRange();
        onTransformsChanged();
        return this;
    }

    public Transforms rotateTo(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
        restrictRotationRange();
        onTransformsChanged();
        return this;
    }

    private void restrictRotationRange() {
        if(rotation.x > 360) {
            rotation.x = rotation.x - 360;
        } else if(rotation.x < -360) {
            rotation.x = rotation.x + 360;
        }

        if(rotation.y > 360) {
            rotation.y = rotation.y - 360;
        } else if(rotation.y < -360) {
            rotation.y = rotation.y + 360;
        }

        if(rotation.z > 360) {
            rotation.z = rotation.z - 360;
        } else if(rotation.z < -360) {
            rotation.z = rotation.z + 360;
        }
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

    public void setLinkedCollider(Collider linkedCollider) {
        this.linkedCollider = linkedCollider;
    }

    public void setLinkedDrawable(Drawable linkedDrawable) {
        this.linkedDrawable = linkedDrawable;
    }

    public void onTransformsChanged() {
        hasChanged = true;
        linkedCollider.onTransformsChanged();
        linkedDrawable.onTransformsChanged();
    }
}
