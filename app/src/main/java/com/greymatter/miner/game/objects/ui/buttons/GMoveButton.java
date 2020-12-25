package com.greymatter.miner.game.objects.ui.buttons;

import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GMoveButton extends GameToggleButton {

    public GMoveButton(Drawable drawable) {
        super(drawable);
        initialize();
    }

    public GMoveButton(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }


    public GMoveButton(String id) {
        super(id, DrawableDef.create(DrawableDef.MOVE_BUTTON_I));
        initialize();
    }

    private void initialize() {
        setOffMaterial(MaterialContainer.get(MaterialDef.MOVE_ICON_OFF_MATERIAL));
        setOnMaterial(MaterialContainer.get(MaterialDef.MOVE_ICON_ON_MATERIAL));
    }

    @Override
    public boolean onClick(IGameObject object) {
        super.onClick(object);
        IGameObject actionObject = getActionObject();
        if (actionObject instanceof GameBuilding) {
            if (actionObject != null) {
                if (isToggle()) {
                    actionObject.asGameBuilding().getBuildingHelper().startMoving();
                } else {
                    actionObject.asGameBuilding().getBuildingHelper().stopMoving();
                }
            }
        }
        return true;
    }
}
