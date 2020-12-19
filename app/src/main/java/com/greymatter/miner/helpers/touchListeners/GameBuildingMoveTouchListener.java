package com.greymatter.miner.helpers.touchListeners;

import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import javax.vecmath.Vector2f;

public class GameBuildingMoveTouchListener implements OnTouchListener {
    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        return true;
    }

    @Override
    public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
        if(ViewModeManager.getActiveTouchHandler().getViewMode() == ViewMode.BUILDING_MODE) {
            gameObject.getParent().setTouchDownOffset(gameObject.getTouchDownOffset());
            OnTouchListener.super.defaultOnTouchMove(gameObject.getParent(), pointer);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
        if(ViewModeManager.getActiveTouchHandler().getViewMode() == ViewMode.BUILDING_MODE) {
            IGameObject planet = GameManager.getCurrentPlanet();
            GameBuilding building = (GameBuilding)gameObject.getParent();
            building.snapTo(planet);
            return true;
        }
        return false;
    }
}
