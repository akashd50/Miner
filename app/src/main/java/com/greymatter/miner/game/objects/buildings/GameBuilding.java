package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.helpers.BuildingSnapAndMovementHelper;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.game.objects.ui.GameNotification;
import com.greymatter.miner.game.objects.ui.OptionsMenu;
import com.greymatter.miner.game.objects.ui.helpers.InGameUIHelper;
import com.greymatter.miner.helpers.touchListeners.GameBuildingMoveTouchListener;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;

public abstract class GameBuilding extends GameObjectWGL {
    private static final String BUILDING_MOVEMENT_TARGET = "building_movement_target";
    private static final String BUILDING_OPTIONS_MENU = "building_options_menu";

    private BuildingSnapAndMovementHelper buildingHelper;
    private GenericObject movementPointer;
    public GameBuilding(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        buildingHelper = new BuildingSnapAndMovementHelper(this);

        movementPointer = new GenericObject(BUILDING_MOVEMENT_TARGET, DrawableDef.create(DrawableDef.BUILDING_MOVE_TARGET));
        movementPointer.copyTranslationFromParent(true);
        movementPointer.getTransforms().copyScaleFromParent(true);
        movementPointer.moveTo(0f, 0f,1f);
        movementPointer.getDrawable().setOpacity(0.5f);
        movementPointer.setCircularRB();
        movementPointer.setOnTouchListener(buildingHelper);
        movementPointer.shouldDraw(false);
        this.addChild(BUILDING_MOVEMENT_TARGET, movementPointer);

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

    public GenericObject getMovementPointer() {
        return movementPointer;
    }

    @Override
    public void onTransformsChanged() {
        super.onTransformsChanged();
        OptionsMenu menu = getOptionsMenu();
        if (menu != null) {
            getOptionsMenu().linkTo(GameNotification.TOP);
        }
    }

    @Override
    public boolean onLongClick(IGameObject object) {
        InGameUIHelper.showDialog(this);
        return true;
    }

    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        return true;
    }
}
