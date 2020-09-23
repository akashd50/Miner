package com.greymatter.miner.physics.objects;

import com.greymatter.miner.physics.objects.rb.RigidBody;

import javax.vecmath.Vector3f;

public class CollisionEvent {
    private RigidBody linkedObject, againstObject;
    private Vector3f collisionNormal;
    private Vector3f linkedObjectCollisionPoint;
    private Vector3f collisionPoint;
    private Vector3f linkedObjCollLinePt1, linkedObjCollLinePt2;
    private Vector3f agObjCollLinePt1, agObjCollLinePt2;
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

    public CollisionEvent withLinkedObjCollLine(Vector3f pt1, Vector3f pt2) {
        this.linkedObjCollLinePt1 = pt1;
        this.linkedObjCollLinePt2 = pt2;
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

    public CollisionEvent withAgainstObjCollLine(Vector3f pt1, Vector3f pt2) {
        this.agObjCollLinePt1 = pt1;
        this.agObjCollLinePt2 = pt2;
        return this;
    }

    public Vector3f getLinkedObjectCollisionPoint() {
        return linkedObjectCollisionPoint;
    }

    public Vector3f getLinkedObjCollLinePt1() {
        return linkedObjCollLinePt1;
    }

    public Vector3f getLinkedObjCollLinePt2() {
        return linkedObjCollLinePt2;
    }

    public Vector3f getAgObjCollLinePt1() {
        return agObjCollLinePt1;
    }

    public Vector3f getAgObjCollLinePt2() {
        return agObjCollLinePt2;
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
