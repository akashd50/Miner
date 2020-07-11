package com.greymatter.miner.opengl.objects;

public abstract class ValueAnimator {
    private long _perFrameDelay, _lastFrameDrawTime;
    private int _framesPerSecond;
    public ValueAnimator() {
        _lastFrameDrawTime = 0;
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
        _perFrameDelay = 1000/fps;
        return this;
    }

    public long getPerFrameDelay() {
        return _perFrameDelay;
    }

    public void setPerFrameDelay(long _perFrameDelay) {
        this._perFrameDelay = _perFrameDelay;
    }

    protected abstract void updateOverridePositive();
    protected abstract void updateOverrideNegative();

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
