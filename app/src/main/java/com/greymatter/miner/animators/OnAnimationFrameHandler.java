package com.greymatter.miner.animators;

import com.greymatter.miner.game.objects.GameObject;

public interface OnAnimationFrameHandler {
    void animate(GameObject object, ValueAnimator animator);
}
