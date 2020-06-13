package com.greymatter.miner.physics.objects;

import com.greymatter.miner.opengl.objects.Drawable;

import java.util.HashMap;

import javax.vecmath.Vector3f;

public abstract class Collider {
    private Vector3f translation, rotation, scale,
            acceleration, velocity, gravity, friction;
    private float mass, restitution, angularVel, angularAcc;
    private boolean isStaticObject, dynamicallyUpdated, shouldUpdateGravity, shouldUpdateFriction;
    private Drawable drawable;
    private OnCollisionListener onCollisionListener;
    private HashMap<String, CollisionEvent> lastCollisionEvents;
    public Collider() {
        this.translation = new Vector3f(0f,0f,0f);
        this.rotation = new Vector3f(0f,0f,0f);
        this.scale = new Vector3f(1.0f,1.0f, 1.0f);
        this.acceleration = new Vector3f();
        this.velocity = new Vector3f();
        this.gravity = new Vector3f();
        this.friction = new Vector3f();
        this.mass = 0;
        this.lastCollisionEvents = new HashMap<>();
        this.shouldUpdateGravity = true;
        this.shouldUpdateFriction = true;
    }

    //<---------------------------------forces start-------------------------------------------->
    public void resetGravity() {
        this.gravity.set(0f,0f,0f);
    }

    public void applyGravity(Vector3f gUpdate) {
        this.gravity.add(gUpdate);
    }

    public void resetFriction() {
        this.friction.set(0f,0f,0f);
    }

    public void applyFriction(Vector3f gUpdate) {
        this.friction.add(gUpdate);
    }

    public void update() {
        this.resetAcceleration();
        this.updateAcceleration(gravity);

        this.updateVelocity(friction);
        this.updateVelocity(acceleration);

        this.translateBy(velocity);

        this.updateAngularVelocity(angularAcc);
        //this.rotation.z += Math.min(angularVel, 5f);
        this.rotation.z = angularVel;
    }

    public void addOrUpdateCollisionEvent(CollisionEvent collisionEvent) {
        if(lastCollisionEvents.containsKey(collisionEvent.getAgainstObject().toString())) {
            lastCollisionEvents.replace(collisionEvent.getAgainstObject().toString(), collisionEvent);
        }else{
            lastCollisionEvents.put(collisionEvent.getAgainstObject().toString(), collisionEvent);
        }
    }

    public CollisionEvent getLastCollisionEvent(Drawable against) {
        return lastCollisionEvents.get(against.getCollider().toString());
    }
    //<---------------------------------forces end -------------------------------------------->
    public void resetAcceleration() {
        this.acceleration.set(0f,0f,0f);
    }

    public Collider setAngularAcceleration(float acceleration) {
        this.angularAcc = acceleration;
        return this;
    }

    public Collider setAngularVelocity(float vel) {
        this.angularVel = vel;
        return this;
    }

    public Collider updateAngularAcceleration(float acceleration) {
        this.angularAcc += acceleration;
        return this;
    }

    public Collider updateAngularVelocity(float vel) {
        this.angularVel += vel;
        return this;
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

    public float getAngularAcceleration() {
        return angularAcc;
    }

    public float getAngularVelocity() {
        return angularVel;
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

    public Vector3f getFriction() {
        return friction;
    }

    public Vector3f getGravity() {
        return gravity;
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

    public boolean shouldUpdateGravity() {
        return shouldUpdateGravity;
    }

    public void shouldUpdateGravity(boolean shouldUpdateGravity) {
        this.shouldUpdateGravity = shouldUpdateGravity;
    }

    public boolean shouldUpdateFriction() {
        return shouldUpdateFriction;
    }

    public void shouldUpdateFriction(boolean shouldUpdateFriction) {
        this.shouldUpdateFriction = shouldUpdateFriction;
    }

    public abstract void updateParamsOverride();
}
