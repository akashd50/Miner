package com.greymatter.miner.physics.objects;

import com.greymatter.miner.physics.objects.rb.RigidBody;

import javax.vecmath.Vector3f;

public class CollisionEvent {
    private RigidBody linkedObject, againstObject;
    private Vector3f collisionNormal;
    private Vector3f linkedObjectCollisionPoint;
    private Vector3f collisionPoint;
    private Vector3f linkedObjectCollisionVector;
    private Vector3f againstObjectCollisionVector;
    private float penetrationDepth;
    private boolean collisionStatus;
    public CollisionEvent(){ }

    public CollisionEvent withLinkedObject(RigidBody rigidBody) {
        this.linkedObject = rigidBody;
        return this;
    }

    public CollisionEvent againstObject(RigidBody rigidBody) {
        this.againstObject = rigidBody;
        return this;
    }

    public CollisionEvent withCollisionNormal(Vector3f normal) {
        this.collisionNormal = normal;
        return this;
    }

    public CollisionEvent withLinkedObjCollisionVector(Vector3f vector) {
        this.linkedObjectCollisionVector = vector;
        return this;
    }

    public CollisionEvent withCollisionPoint(Vector3f point) {
        this.collisionPoint = point;
        return this;
    }

    public CollisionEvent withStatus(boolean collStat) {
        this.collisionStatus = collStat;
        return this;
    }

    public CollisionEvent withPenDepth(float penDepth) {
        this.penetrationDepth = penDepth;
        return this;
    }

    public CollisionEvent withLinkedObjectCollisionPoint(Vector3f linkedObjectCollisionPoint) {
        this.linkedObjectCollisionPoint = linkedObjectCollisionPoint;
        return this;
    }

    public CollisionEvent withAgainstObjectCollisionVector(Vector3f againstObjectCollisionVector) {
        this.againstObjectCollisionVector = againstObjectCollisionVector;
        return this;
    }

    public Vector3f getAgainstObjectCollisionVector() {
        return againstObjectCollisionVector;
    }

    public Vector3f getLinkedObjectCollisionPoint() {
        return linkedObjectCollisionPoint;
    }

    public Vector3f getCollisionPoint() {
        return collisionPoint;
    }

    public float getPenDepth() {
        return this.penetrationDepth;
    }

    public Vector3f getCollisionNormal() {
        return this.collisionNormal;
    }

    public Vector3f getLinkedObjectCollisionVector() {
        return this.linkedObjectCollisionVector;
    }

    public boolean getCollisionStatus() {
        return collisionStatus;
    }

    public RigidBody getLinkedObject() {
        return linkedObject;
    }

    public RigidBody getAgainstObject() {
        return againstObject;
    }
}
