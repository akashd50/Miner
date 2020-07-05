package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Scanner extends GameBuilding {
    private float _scannerRange, _scanningAngle;
    public Scanner(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Scanner(String id, Drawable drawable) {
        super(id, drawable);
    }

    public boolean inRange() {

        return false;
    }

    public float getScannerRange() {
        return _scannerRange;
    }

    public void setScannerRange(float scannerRange) {
        this._scannerRange = scannerRange;
    }

    public float getScanningAngle() {
        return _scanningAngle;
    }

    public void setScanningAngle(float scanningAngle) {
        this._scanningAngle = scanningAngle;
    }
}
