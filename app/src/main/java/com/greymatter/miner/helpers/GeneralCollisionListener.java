package com.greymatter.miner.helpers;

import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.OnCollisionListener;

public class GeneralCollisionListener implements OnCollisionListener {
    @Override
    public void impulseResolution(CollisionEvent event) {
        OnCollisionListener.super.impulseResolutionDefault(event);
    }
    @Override
    public void positionalCorrection(CollisionEvent event) {
        OnCollisionListener.super.positionalCorrectionDefault(event);
    }
}