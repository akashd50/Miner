package com.greymatter.miner.helpers.touchListeners;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;

import javax.vecmath.Vector2f;

public class BuildingModeTouchListener implements OnTouchListener {
    @Override
    public boolean onTouchDown(GameObject gameObject, Vector2f pointer) {
        return false;
    }

    @Override
    public boolean onTouchMove(GameObject gameObject, Vector2f pointer) {
        if(ViewModeManager.getActiveTouchMode().getViewMode() == ViewMode.BUILDING_MODE) {
            OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchUp(GameObject gameObject, Vector2f pointer) {
        return true;
    }
}
