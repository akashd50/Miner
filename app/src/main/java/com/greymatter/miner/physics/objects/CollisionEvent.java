package com.greymatter.miner.physics.objects;

import com.greymatter.miner.opengl.objects.Drawable;

public class CollisionEvent {
    private Collider linkedObject, againstObject;
    private int collisionStatus;
    public CollisionEvent(){ }

    public CollisionEvent withLinkedObject(Collider collider) {
        this.linkedObject = collider;
        return this;
    }

    public CollisionEvent againstObject(Collider collider) {
        this.againstObject = collider;
        return this;
    }

    public CollisionEvent withStatus(int s) {
        this.collisionStatus = s;
        return this;
    }

    public int getCollisionStatus() {
        return collisionStatus;
    }

    public Collider getLinkedObject() {
        return linkedObject;
    }

    public Collider getAgainstObject() {
        return againstObject;
    }
}
