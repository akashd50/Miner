package com.greymatter.miner.physics.objects;

import com.greymatter.miner.opengl.objects.Drawable;

import javax.vecmath.Vector3f;

public abstract class Collider {
    private Vector3f translation, rotation, scale, acceleration, velocity;
    private float mass, restitution;
    private boolean isStaticObject, dynamicallyUpdated;
    private Drawable drawable;
    private OnCollisionListener onCollisionListener;
    //private CollisionDetector collisionDetector;

    public Collider() {
        this.translation = new Vector3f(0f,0f,0f);
        this.rotation = new Vector3f(0f,0f,0f);
        this.scale = new Vector3f(1.0f,1.0f, 1.0f);
        this.acceleration = new Vector3f();
        this.velocity = new Vector3f();
        this.mass = 0;
    }
    
    public void update() {
        this.updateVelocity(acceleration);
        this.translateBy(velocity);
    }

    public Collider setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    public Collider setVelocity(Vector3f velocity) {
        this.velocity = velocity;
        return this;
    }

    public Collider updateAcceleration(Vector3f acceleration) {
        this.acceleration.add(acceleration);
        return this;
    }

    public Collider updateVelocity(Vector3f velocity) {
        this.velocity.add(velocity);
        return this;
    }

    public Collider setMass(float mass) {
        this.mass = mass;
        return this;
    }

    public Collider setRestitution(float restitution) {
        this.restitution = restitution;
        return this;
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
        this.updateParams();
    }

    public void rotateBy(Vector3f rotation) {
        this.rotation.add(rotation);
        this.updateParams();
    }

    public void isStaticObject(boolean isStatic) {
        this.isStaticObject = isStatic;
    }

    public OnCollisionListener getCollisionListener() {
        return onCollisionListener;
    }

    public void setCollisionListener(OnCollisionListener collisionListener) {
        this.onCollisionListener = collisionListener;
    }

    public void updateTransformationsPerMovement(boolean dynamicallyUpdated) {
        this.dynamicallyUpdated = dynamicallyUpdated;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        if(this.drawable.getCollider()==null) this.drawable.setCollider(this);
    }

    public void updateParams() {
        if(dynamicallyUpdated) {
            this.updateParamsOverride();
        }
        this.drawable.transformationsUpdated();
    }

    public boolean isStaticObject() {
        return isStaticObject;
    }

    public boolean isUpdatedPerMovement() {
        return dynamicallyUpdated;
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

    public Vector3f getVelocity() {
        return velocity;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public float getMass(){
        return this.mass;
    }

    public float getRestitution() {
        return restitution;
    }

    public CircleCollider asCircleCollider() {
        return (CircleCollider)this;
    }

    public PolygonCollider asPolygonCollider() {
        return (PolygonCollider) this;
    }

    public abstract void updateParamsOverride();
}
