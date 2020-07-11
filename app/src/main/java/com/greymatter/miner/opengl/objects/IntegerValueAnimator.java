package com.greymatter.miner.opengl.objects;

public class IntegerValueAnimator extends ValueAnimator {
    private int _totalFrames, _currentFrame;
    public IntegerValueAnimator() {
        super();
        _currentFrame = 0;
    }

    @Override
    protected void updateOverride() {
        _currentFrame++;
        if (_currentFrame >= _totalFrames) {
            _currentFrame = 0;
        }
    }

    @Override
    public int getInt() {
        return _currentFrame;
    }

    @Override
    public float getFloat(){
        return 0f;
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
