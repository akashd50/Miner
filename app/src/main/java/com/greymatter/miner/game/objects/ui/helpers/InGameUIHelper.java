package com.greymatter.miner.game.objects.ui.helpers;

import com.greymatter.miner.containers.ContainerManager;
import static com.greymatter.miner.game.GameConstants.*;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.ui.GroupButtonsMenu;
import com.greymatter.miner.helpers.VectorHelper;

import javax.vecmath.Vector3f;

public class InGameUIHelper {
    public static void showDialog(GameBuilding gameBuilding) {
        GroupButtonsMenu menu = (GroupButtonsMenu)ContainerManager.getActiveUIContainer().get(BUILDING_OPTIONS_MENU);
        menu.setActionObject(gameBuilding);
        menu.refresh();
        menu.show();
    }

    public static Vector3f getUpVector(IGameObject gameObject) {
        Vector3f direction = VectorHelper.sub(gameObject.getLocalLocation(),
                GameManager.getCurrentPlanet().getLocalLocation());
        direction.normalize();
        return direction;
    }
}
