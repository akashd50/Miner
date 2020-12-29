package com.greymatter.miner.game.objects.ui.buttons;

import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.helpers.touchListeners.GameBuildingMoveTouchListener;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GMoveButton extends GameToggleButton {
    private static final String BUILDING_MOVEMENT_POINTER = "building_movement_target";
    private GenericObject movementPointer;

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

        movementPointer = new GenericObject(BUILDING_MOVEMENT_POINTER, DrawableDef.create(DrawableDef.BUILDING_MOVE_TARGET));
        movementPointer.copyTranslationFromParent(true);
        movementPointer.scaleFromParent(true);
        movementPointer.rotationFromParent(true);
        //movementPointer.getTransforms().copyScaleFromParent(true);
        movementPointer.moveTo(0f, 0f,1f);
        movementPointer.getTransforms().scaleTo(1f, 1f, 1f);
        movementPointer.getDrawable().setOpacity(0.5f);
        movementPointer.setCircularRB();
        movementPointer.setOnTouchListener(new GameBuildingMoveTouchListener());
        movementPointer.shouldDraw(false);
    }

    @Override
    public boolean onClick(IGameObject object) {
        super.onClick(object);
        IGameObject actionObject = getActionObject();
        if (actionObject instanceof GameBuilding) {
            if (isToggle()) {
                startMoving(actionObject.asGameBuilding());
            }else{
                stopMoving(actionObject.asGameBuilding());
            }
        }
        return true;
    }

    private void startMoving(GameBuilding building) {
        movementPointer.shouldDraw(true);
        //movementPointer.moveTo(building.getLocalLocation().x, building.getLocalLocation().y);
        movementPointer.setParent(building);
    }

    private void stopMoving(GameBuilding building) {
        movementPointer.shouldDraw(false);
        movementPointer.clearParent();
    }

    public GenericObject getMovementPointer() {
        return movementPointer;
    }
}
