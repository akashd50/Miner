package com.greymatter.miner.opengl.objects;

import android.opengl.Matrix;
import com.greymatter.miner.helpers.MatrixHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.RigidBody;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Transforms {
    private Vector3f translation, rotation, scale;
    private float[] modelMatrix, translationMat, translationRotMat;
    private Drawable linkedDrawable;
    private RigidBody linkedRigidBody;
    private boolean transformationsUpdated, copyTranslationFromParent,
                    copyRotationFromParent, copyScaleFromParent;
    private Transforms parent;
    public Transforms() {
        translation = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1f,1f,1f);
        this.modelMatrix = new float[16];
        this.copyTranslationFromParent = false;
        this.copyRotationFromParent = false;
        this.copyScaleFromParent = false;
    }

    public void applyTransformations() {
        if(transformationsUpdated) {
            if(parent != null) {
                checkParentTransformations();
            }else{
                Matrix.setIdentityM(this.modelMatrix, 0);
            }

            MatrixHelper.translateM(modelMatrix, translation);
            MatrixHelper.rotateM(modelMatrix, rotation);
            MatrixHelper.scaleM(modelMatrix, scale);
            transformationsUpdated = false;
        }
    }

    private void checkParentTransformations() {
        this.modelMatrix = parent.getModelMatrix();
        if(!copyScaleFromParent) MatrixHelper.scaleM(modelMatrix, 1f, 1f, 1f);
        if(!copyRotationFromParent) MatrixHelper.rotateM(modelMatrix,
                -parent.getRotation().x,
                -parent.getRotation().y,
                -parent.getRotation().z);
        if(!copyTranslationFromParent) MatrixHelper.rotateM(modelMatrix,
                -parent.getTranslation().x,
                -parent.getTranslation().y,
                -parent.getTranslation().z);
    }

    public void applyTransformationsOld() {
        if(transformationsUpdated) {
            Matrix.setIdentityM(this.modelMatrix, 0);
            if(parent != null) applyParentTransformations();
            applyOwnTransformations();
            transformationsUpdated = false;
        }
    }

    private void applyOwnTransformations() {
        MatrixHelper.translateM(modelMatrix, translation);
        MatrixHelper.rotateM(modelMatrix, rotation);
        MatrixHelper.scaleM(modelMatrix, scale);
    }

    private void applyParentTransformations() {
        if(copyTranslationFromParent) MatrixHelper.translateM(modelMatrix, parent.getTranslation());
        if(copyRotationFromParent) MatrixHelper.rotateM(modelMatrix, parent.getRotation());
        if(copyScaleFromParent) MatrixHelper.scaleM(modelMatrix, parent.getScale());
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

    public Transforms scaleTo(float x, float y) {
        this.scale.set(x,y,this.scale.z);
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

    public Transforms scaleBy(float x, float y) {
        this.scale.x+=x;
        this.scale.y+=y;
        onTransformsChanged();
        return this;
    }

    public Transforms translateTo(Vector3f position) {
        this.translation.set(position);
        onTransformsChanged();
        return this;
    }

    public Transforms translateTo(float x, float y, float z) {
        this.translation.set(x,y,z);
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

    public Transforms translateBy(float x, float y, float z) {
        this.translation.x += x;
        this.translation.y += y;
        this.translation.z += z;
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

    public Transforms getParent() {
        return parent;
    }

    public Transforms setParent(Transforms parent) {
        this.parent = parent;
        return this;
    }

    public Transforms setLinkedDrawable(Drawable linkedDrawable) {
        this.linkedDrawable = linkedDrawable;
        return this;
    }

    public boolean isCopyTranslationFromParent() {
        return copyTranslationFromParent;
    }

    public Transforms setCopyTranslationFromParent(boolean copyTranslationFromParent) {
        this.copyTranslationFromParent = copyTranslationFromParent;
        return this;
    }

    public boolean isCopyRotationFromParent() {
        return copyRotationFromParent;
    }

    public Transforms setCopyRotationFromParent(boolean copyRotationFromParent) {
        this.copyRotationFromParent = copyRotationFromParent;
        return this;
    }

    public boolean isCopyScaleFromParent() {
        return copyScaleFromParent;
    }

    public Transforms setCopyScaleFromParent(boolean copyScaleFromParent) {
        this.copyScaleFromParent = copyScaleFromParent;
        return this;
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

    public float[] getModelMatrix() { return this.modelMatrix; }

    public void setLinkedRigidBody(RigidBody linkedRigidBody) {
        this.linkedRigidBody = linkedRigidBody;
    }

    private void onTransformsChanged() {
        transformationsUpdated = true;
        linkedRigidBody.onTransformsChanged();
        linkedDrawable.onTransformsChanged();
    }
}
