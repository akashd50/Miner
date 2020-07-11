package com.greymatter.miner.opengl.objects;

public class BooleanAnimator extends ValueAnimator {
    private boolean shouldUpdate;
    public BooleanAnimator() {
        super();
    }

    @Override
    protected void updateOverridePositive() {
        shouldUpdate = true;
    }

    @Override
    protected void updateOverrideNegative() {
        shouldUpdate = false;
    }

    @Override
    public boolean getUpdatedBoolean() {
        return shouldUpdate;
    }
}
