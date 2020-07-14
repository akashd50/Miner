package com.greymatter.miner.opengl.objects.animators;

public class IntegerValueAnimator extends ValueAnimator {
    private int _totalFrames, _currentFrame;
    public IntegerValueAnimator() {
        super();
        _currentFrame = 0;
    }

    @Override
    protected void updateOverridePositive() {
        _currentFrame++;
        if (_currentFrame >= _totalFrames) {
            _currentFrame = 0;
        }
    }

    @Override
    protected void updateOverrideNegative() {

    }

    @Override
    public int getUpdatedInt() {
        return _currentFrame;
    }

    @Override
    public IntegerValueAnimator withFPS(int fps) {
        super.withFPS(fps);
        return this;
    }

    public IntegerValueAnimator withTotalFrames(int totalFrames) {
        _totalFrames = totalFrames;
        return this;
    }
}
