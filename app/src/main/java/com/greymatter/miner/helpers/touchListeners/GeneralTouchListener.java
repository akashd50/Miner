package com.greymatter.miner.helpers.touchListeners;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import javax.vecmath.Vector2f;

public class GeneralTouchListener implements OnTouchListener {
    @Override
    public void onTouchDown(GameObject gameObject, Vector2f pointer) {

    }

    @Override
    public void onTouchMove(GameObject gameObject, Vector2f pointer) {
        OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
    }

    @Override
    public void onTouchUp(GameObject gameObject, Vector2f pointer) {

    }
}
