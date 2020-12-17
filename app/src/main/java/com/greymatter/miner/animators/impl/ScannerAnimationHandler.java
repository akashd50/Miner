package com.greymatter.miner.animators.impl;

import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.buildings.Scanner;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.helpers.VectorHelper;

import javax.vecmath.Vector3f;

public class ScannerAnimationHandler implements OnAnimationFrameHandler {
    @Override
    public void onAnimationFrame(GameObject object, ValueAnimator animator) {
        Scanner scanner = (Scanner)object;
        ResourceBlock currentlyTracking = scanner.getCurrentlyTracking();
        currentlyTracking = currentlyTracking==null? scanner.findClosestResource() : currentlyTracking;
        scanner.setCurrentlyTracking(currentlyTracking);
        if(!scanner.isResourceInRange(currentlyTracking)) {
            Vector3f dir = VectorHelper.sub(currentlyTracking.getLocation(), scanner.getLocation());
            dir.normalize();
            dir.z = 0f;
            scanner.getRigidBody().getRBProps().updateVelocity(VectorHelper.multiply(dir, 0.02f));
        }else{
            ContainerManager.getActiveResourceContainer().add("COAL_BLOCK_I", currentlyTracking);
            if(!scanner.getSignal().shouldDraw()) {
                scanner.getSignal().show();
            }
        }
    }
}
