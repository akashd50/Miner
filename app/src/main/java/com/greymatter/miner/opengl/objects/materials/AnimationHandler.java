package com.greymatter.miner.opengl.objects.materials;

public class AnimationHandler {
    private int _framesPerSecond, _totalFrames, _currentFrame;
    private long _perFrameDelay, _lastFrameDrawTime;
    public AnimationHandler() {
        _currentFrame = 0;
        _lastFrameDrawTime = 0;
    }

    public int getActiveFrame() {
        if(System.currentTimeMillis() - _lastFrameDrawTime > _perFrameDelay) {
            _currentFrame++;
            if(_currentFrame < _totalFrames) {
                return _currentFrame;
            }else{
                _currentFrame = 0;
                return _currentFrame;
            }
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

    public int getCurrentFrame() {
        return _currentFrame;
    }
}
