package com.greymatter.miner.opengl.objects;

public class FloatValueAnimator extends ValueAnimator {
    private float currentValue, perFrameIncrement, lowerBound, upperBound;
    public FloatValueAnimator() {
        super();
        lowerBound = 0f;
        upperBound = 1f;
        perFrameIncrement = 0.01f;
        currentValue = 0f;
    }

    @Override
    protected void updateOverride() {
        currentValue+=perFrameIncrement;
        if(currentValue > upperBound) {
            currentValue = lowerBound;
        }
        if(currentValue < lowerBound) {
            currentValue = upperBound;
        }
    }

    @Override
    public int getInt() {
        return 0;
    }

    @Override
    public float getFloat() {
        return currentValue;
    }

    public FloatValueAnimator setBounds(float lowerBound, float upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        return this;
    }

    public FloatValueAnimator setPerFrameIncrement(float perFrameIncrement) {
        this.perFrameIncrement = perFrameIncrement;
        return this;
    }
}
