package com.greymatter.miner.physics.objects;

import javax.vecmath.Vector3f;

public class CollisionEvent {
    private Collider linkedObject, againstObject;
    private Vector3f collisionNormal;
    private Vector3f linkedObjectCollisionPoint;
    private Vector3f againstObjectCollisionPoint;
    private Vector3f linkedObjectCollisionVector;
    private Vector3f againstObjectCollisionVector;
    private float penetrationDepth;
    private boolean collisionStatus;
    public CollisionEvent(){ }

    public CollisionEvent withLinkedObject(Collider collider) {
        this.linkedObject = collider;
        return this;
    }

    public CollisionEvent againstObject(Collider collider) {
        this.againstObject = collider;
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

    public CollisionEvent withAgainstObjectCollisionPoint(Vector3f point) {
        this.againstObjectCollisionPoint = point;
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

    public Vector3f getAgainstObjectCollisionPoint() {
        return againstObjectCollisionPoint;
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

    public Collider getLinkedObject() {
        return linkedObject;
    }

    public Collider getAgainstObject() {
        return againstObject;
    }
}
