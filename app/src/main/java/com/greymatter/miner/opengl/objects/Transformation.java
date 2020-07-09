package com.greymatter.miner.opengl.objects;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Transformation {
    private Vector3f translation, rotation, scale;
    public Transformation() {
        translation = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1f,1f,1f);
    }

    public Transformation scaleTo(Vector3f newScale) {
        this.scale.set(newScale);
        return this;
    }

    public Transformation scaleBy(Vector3f newScale) {
        this.scale.add(newScale);
        return this;
    }

    public Transformation scaleTo(float x, float y, float z) {
        this.scale.set(x,y,z);
        return this;
    }

    public Transformation scaleBy(float x, float y, float z) {
        this.scale.x+=x;
        this.scale.y+=y;
        this.scale.z+=z;
        return this;
    }

    public Transformation translateTo(Vector3f position) {
        this.translation.set(position);
        return this;
    }

    public Transformation translateBy(Vector3f translation) {
        this.translation.add(translation);
        return this;
    }

    public Transformation translateTo(Vector2f position) {
        this.translation.x = position.x;
        this.translation.y = position.y;
        return this;
    }

    public Transformation translateBy(Vector2f translation) {
        this.translation.x += translation.x;
        this.translation.y += translation.y;
        return this;
    }

    public Transformation translateTo(float x, float y) {
        this.translation.x = x;
        this.translation.y = y;
        return this;
    }

    public Transformation translateBy(float x, float y) {
        this.translation.x += x;
        this.translation.y += y;
        return this;
    }

    //rotation
    public Transformation rotateTo(Vector3f rotation) {
        this.rotation.set(rotation);
        restrictRotationRange();
        return this;
    }

    public Transformation rotateBy(Vector3f rotation) {
        this.rotation.add(rotation);
        restrictRotationRange();
        return this;
    }

    public Transformation rotateBy(float x, float y, float z) {
        this.rotation.x+=x;
        this.rotation.y+=y;
        this.rotation.z+=z;
        restrictRotationRange();
        return this;
    }

    public Transformation rotateTo(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
        restrictRotationRange();
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
}
