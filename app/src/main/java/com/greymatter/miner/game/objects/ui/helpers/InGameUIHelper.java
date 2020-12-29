package com.greymatter.miner.game.objects.ui.helpers;

import com.greymatter.miner.containers.ContainerManager;
import static com.greymatter.miner.game.GameConstants.*;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.ui.OptionsMenu;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;
import javax.vecmath.Vector3f;

public class InGameUIHelper {
    public static void showDialog(GameBuilding gameBuilding) {
        OptionsMenu menu = (OptionsMenu)ContainerManager.getActiveUIContainer().get(BUILDING_OPTIONS_MENU);
        menu.setActionObject(gameBuilding);

        Transforms buildingTransforms = gameBuilding.getTransforms();
        Transforms menuTransforms = menu.getTransforms();

//        Vector3f up = getUpVector(gameBuilding);
//        up.x *= buildingTransforms.getScale().y + menuTransforms.getScale().y;
//        up.x += gameBuilding.getLocalLocation().x;
//        up.y *= buildingTransforms.getScale().y + menuTransforms.getScale().y;
//        up.y += gameBuilding.getLocalLocation().y;
//        up.z = gameBuilding.getLocalLocation().z + 1f;

        menu.refresh();

        //menu.moveTo(up);
        menu.show();
    }

    public static Vector3f getUpVector(IGameObject gameObject) {
        Vector3f direction = VectorHelper.sub(gameObject.getLocalLocation(),
                GameManager.getCurrentPlanet().getLocalLocation());
        direction.normalize();
        return direction;
    }
}
