package com.greymatter.miner.opengl.objects;

public class AnimationHandler {
    private int _framesPerSecond, _totalFrames, _currentFrame;
    private long _perFrameDelay, _lastFrameDrawTime;
    public AnimationHandler() {
        _currentFrame = 0;
        _lastFrameDrawTime = 0;
    }

    public int updateActiveFrame() {
        if(System.currentTimeMillis() - _lastFrameDrawTime > _perFrameDelay) {
            _currentFrame++;
            if (_currentFrame >= _totalFrames) {
                _currentFrame = 0;
            }
            _lastFrameDrawTime = System.currentTimeMillis();
            return _currentFrame;
        }
        return _currentFrame;
    }

    public AnimationHandler withFPS(int fps) {
        _framesPerSecond = fps;
        _perFrameDelay = 1000/fps;
        return this;
    }

    public AnimationHandler withTotalFrames(int totalFrames) {
        _totalFrames = totalFrames;
        return this;
    }
}
