package com.greymatter.miner.animators.impl;

import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.ui.GameDialog;

import javax.vecmath.Vector3f;

public class DialogAnimator implements OnAnimationFrameHandler {
    @Override
    public void animate(GameObject object, ValueAnimator animator) {
        GameDialog dialog = (GameDialog) object;
        if(!dialog.isAnimationComplete()) {
            Vector3f scale = dialog.getTransforms().getScale();
            float newVal = animator.update().getUpdatedFloat();
            dialog.scaleTo(dialog.getDefaultScale().x * newVal, dialog.getDefaultScale().y * newVal);
            if(scale.x >= dialog.getDefaultScale().x-0.2 || scale.y >= dialog.getDefaultScale().y-0.2) {
                dialog.isAnimationComplete(true);
                dialog.getAnimator().reset();
            }
        }
    }
}
