package com.greymatter.miner.helpers.touchListeners;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import javax.vecmath.Vector2f;

public class GeneralTouchListener implements OnTouchListener {
    @Override
    public boolean onTouchDown(GameObject gameObject, Vector2f pointer) {
        return false;
    }

    @Override
    public boolean onTouchMove(GameObject gameObject, Vector2f pointer) {
        OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
        return true;
    }

    @Override
    public boolean onTouchUp(GameObject gameObject, Vector2f pointer) {
        return true;
    }
}
