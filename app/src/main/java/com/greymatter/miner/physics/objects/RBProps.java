package com.greymatter.miner.physics.objects;

import javax.vecmath.Vector3f;

public class RBProps {
    private Vector3f acceleration, velocity, gravity, friction;
    private float mass, restitution, angularVel, angularAcc;

    public RBProps() {
        this.acceleration = new Vector3f();
        this.velocity = new Vector3f();
        this.gravity = new Vector3f();
        this.friction = new Vector3f();
    }

    public void update() {
        this.resetAcceleration();

        this.updateAcceleration(gravity);
        this.updateVelocity(friction);
        this.updateVelocity(acceleration);
        this.updateAngularVelocity(angularAcc);

        //angular friction
        if(angularVel > 0) {
            angularVel-=0.01f;
        }else if(angularVel < 0){
            angularVel+=0.01f;
        }
    }

    public RBProps resetGravity() {
        this.gravity.set(0f,0f,0f);
        return this;
    }

    public RBProps applyGravity(Vector3f gUpdate) {
        this.gravity.add(gUpdate);
        return this;
    }

    public RBProps resetFriction() {
        this.friction.set(0f,0f,0f);
        return this;
    }

    public RBProps applyFriction(Vector3f gUpdate) {
        this.friction.add(gUpdate);
        return this;
    }

    public RBProps resetAcceleration() {
        this.acceleration.set(0f,0f,0f);
        return this;
    }

    public RBProps setAngularAcceleration(float acceleration) {
        this.angularAcc = acceleration;
        return this;
    }

    public RBProps setAngularVelocity(float vel) {
        this.angularVel = vel;
        return this;
    }

    public RBProps updateAngularAcceleration(float acceleration) {
        this.angularAcc += acceleration;
        return this;
    }

    public RBProps updateAngularVelocity(float vel) {
        this.angularVel += vel;
        return this;
    }

    public RBProps setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    public RBProps setVelocity(Vector3f velocity) {
        this.velocity = velocity;
        return this;
    }

    public RBProps updateAcceleration(Vector3f acceleration) {
        this.acceleration.add(acceleration);
        return this;
    }

    public RBProps updateVelocity(Vector3f velocity) {
        this.velocity.add(velocity);
        return this;
    }

    public RBProps setMass(float mass) {
        this.mass = mass;
        return this;
    }

    public RBProps setRestitution(float restitution) {
        this.restitution = restitution;
        return this;
    }

    public float getAngularAcceleration() {
        return angularAcc;
    }

    public float getAngularVelocity() {
        return angularVel;
    }

    public Vector3f getVelocity() {
        velocity.z = 0f;
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

    public float getMass(){
        return this.mass;
    }

    public float getRestitution() {
        return restitution;
    }
}
