package com.greymatter.miner.animators;

public abstract class ValueAnimator {
    private long _perFrameDelay, _lastFrameDrawTime;
    private int _framesPerSecond;
    private boolean toAndFro, isIncrementing, isSingleCycle;
    public ValueAnimator() {
        _lastFrameDrawTime = 0;
        isIncrementing = true;
    }

    public ValueAnimator update() {
        if(System.currentTimeMillis() - _lastFrameDrawTime > _perFrameDelay) {
            updateOverridePositive();
            _lastFrameDrawTime = System.currentTimeMillis();
        }else{
            updateOverrideNegative();
        }
        return this;
    }

    public ValueAnimator withFPS(int fps) {
        _framesPerSecond = fps;
        _perFrameDelay = 1000/_framesPerSecond;
        return this;
    }

    public ValueAnimator reset() {
        _lastFrameDrawTime = 0;
        return this;
    }

    public ValueAnimator toAndFro(boolean val) {
        this.toAndFro = val;
        return this;
    }

    public ValueAnimator setSingleCycle(boolean singleCycle) {
        isSingleCycle = singleCycle;
        return this;
    }

    protected ValueAnimator setIncrementing(boolean val) {
        this.isIncrementing = val;
        return this;
    }

    public boolean isIncrementing() {
        return isIncrementing;
    }

    public boolean isSingleCycle() {
        return isSingleCycle;
    }

    public boolean toAndFro() {
        return toAndFro;
    }

    public long getPerFrameDelay() {
        return _perFrameDelay;
    }

    public void setPerFrameDelay(long _perFrameDelay) {
        this._perFrameDelay = _perFrameDelay;
    }

    protected abstract void updateOverridePositive();
    protected abstract void updateOverrideNegative();

    public float getPerFrameIncrement() {
        return 0f;
    }

    public int getUpdatedInt(){
        return 0;
    }

    public float getUpdatedFloat(){
        return 0f;
    }

    public boolean getUpdatedBoolean() {
        return false;
    }
}
