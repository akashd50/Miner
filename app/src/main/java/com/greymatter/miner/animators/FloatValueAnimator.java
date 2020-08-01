package com.greymatter.miner.animators;

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
    public FloatValueAnimator withFPS(int fps) {
        super.withFPS(fps);
        return this;
    }

    @Override
    protected void updateOverridePositive() {
        if(isTo()) {
            currentValue += perFrameIncrement;
        }else{
            currentValue -= perFrameIncrement;
        }

        if(currentValue > upperBound) {
            currentValue = toAndFro()? upperBound : lowerBound;
            to(!toAndFro());
        }
        if(currentValue < lowerBound) {
            currentValue = toAndFro()? lowerBound : upperBound;
            to(true);
        }
    }

    @Override
    protected void updateOverrideNegative() {

    }

    @Override
    public FloatValueAnimator reset() {
        super.reset();
        currentValue = lowerBound;
        return this;
    }

    @Override
    public float getUpdatedFloat() {
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
