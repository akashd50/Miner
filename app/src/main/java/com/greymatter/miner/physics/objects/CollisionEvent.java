package com.greymatter.miner.physics.objects;

import com.greymatter.miner.opengl.objects.Drawable;

import javax.vecmath.Vector3f;

public class CollisionEvent {
    private Collider linkedObject, againstObject;
    private Vector3f collisionNormal;
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

    public CollisionEvent withStatus(boolean collStat) {
        this.collisionStatus = collStat;
        return this;
    }

    public CollisionEvent withPenDepth(float penDepth) {
        this.penetrationDepth = penDepth;
        return this;
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

    public Collider getLinkedObject() {
        return linkedObject;
    }

    public Collider getAgainstObject() {
        return againstObject;
    }
}
