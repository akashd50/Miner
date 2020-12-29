package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.helpers.BuildingSnapAndMovementHelper;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.game.objects.ui.GameNotification;
import com.greymatter.miner.game.objects.ui.OptionsMenu;
import com.greymatter.miner.game.objects.ui.helpers.InGameUIHelper;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;

public abstract class GameBuilding extends GameObjectWGL {
    private BuildingSnapAndMovementHelper buildingHelper;
    public GameBuilding(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        buildingHelper = new BuildingSnapAndMovementHelper(this);

//        movementPointer = new GenericObject(BUILDING_MOVEMENT_POINTER, DrawableDef.create(DrawableDef.BUILDING_MOVE_TARGET));
//        movementPointer.copyTranslationFromParent(true);
//        // movementPointer.getTransforms().copyScaleFromParent(true);
//        movementPointer.moveTo(0f, 0f,1f);
//        movementPointer.getTransforms().scaleTo(this.getTransforms().getScale());
//        movementPointer.getDrawable().setOpacity(0.5f);
//        movementPointer.setCircularRB();
//        movementPointer.setOnTouchListener(buildingHelper);
//        movementPointer.shouldDraw(false);
//        this.addChild(BUILDING_MOVEMENT_POINTER, movementPointer);

        this.setOnClickListener(this);
        this.setOnTouchListener(this);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        buildingHelper.update();
    }

    public BuildingSnapAndMovementHelper getBuildingHelper() {
        return buildingHelper;
    }

    @Override
    public boolean onLongClick(IGameObject object) {
        InGameUIHelper.showDialog(this);
        return true;
    }

    @Override
    public void onTransformsChanged() {
        super.onTransformsChanged();
    }

    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        return true;
    }
}
