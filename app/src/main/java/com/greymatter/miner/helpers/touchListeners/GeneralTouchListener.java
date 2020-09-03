package com.greymatter.miner.helpers.touchListeners;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import javax.vecmath.Vector2f;

public class GeneralTouchListener implements OnTouchListener {
    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        return true;
    }

    @Override
    public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
        OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
        return true;
    }

    @Override
    public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
        return true;
    }
}
