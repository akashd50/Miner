package com.greymatter.miner.game.objects.buildings;

import android.view.ContextMenu;

import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.helpers.BuildingSnapAndMovementHelper;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.game.objects.ui.GameNotification;
import com.greymatter.miner.game.objects.ui.OptionsMenu;
import com.greymatter.miner.helpers.touchListeners.GameBuildingMoveTouchListener;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;

public abstract class GameBuilding extends GameObjectWGL {
    private static final String BUILDING_MOVEMENT_TARGET = "building_movement_target";
    private static final String BUILDING_OPTIONS_MENU = "building_options_menu";

    private BuildingSnapAndMovementHelper buildingHelper;
    private GenericObject buildingMovementTarget;
    public GameBuilding(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        buildingHelper = new BuildingSnapAndMovementHelper(this);

        buildingMovementTarget = new GenericObject(BUILDING_MOVEMENT_TARGET, DrawableDef.create(DrawableDef.BUILDING_MOVE_TARGET));
        buildingMovementTarget.copyTranslationFromParent(true);
        buildingMovementTarget.moveTo(0f, 0f,1f);
        buildingMovementTarget.getDrawable().setOpacity(0.5f);
        buildingMovementTarget.setCircularRB();
        buildingMovementTarget.setOnTouchListener(new GameBuildingMoveTouchListener());
        buildingMovementTarget.shouldDraw(false);
        this.addChild(BUILDING_MOVEMENT_TARGET, buildingMovementTarget);

        OptionsMenu optionsMenu = new OptionsMenu(BUILDING_OPTIONS_MENU).withBuildingAs(this);
        this.setOptionsMenu(optionsMenu);

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

    public void startMoving() {
        buildingMovementTarget.shouldDraw(true);
    }

    public void stopMoving() {
        buildingMovementTarget.shouldDraw(false);
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
        object.asGameBuilding().getOptionsMenu().show();
        return true;
    }

    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        return true;
    }
}
