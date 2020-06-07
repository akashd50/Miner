package com.greymatter.miner.physics.objects;

public interface OnCollisionListener {
    default void onCollision(CollisionEvent event) {
        impulseResolution(event);
        positionalCorrection(event);
    }
    void impulseResolution(CollisionEvent event);
    void positionalCorrection(CollisionEvent event);
}
