package com.greymatter.miner.game.objects.ui.menu_impls;

import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.game.GameConstants;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.ButtonsMenu;
import com.greymatter.miner.game.objects.ui.buttons.GameToggleButton;
import com.greymatter.miner.game.objects.ui.buttons.GameButton;
import com.greymatter.miner.helpers.touchListeners.GameBuildingMoveTouchListener;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class BuildingOptionsMenu extends ButtonsMenu {
    private static final String BUILDING_MOVEMENT_POINTER = "building_movement_target";

    public BuildingOptionsMenu(String id, Drawable drawable) {
        super(id, drawable);
    }

    public BuildingOptionsMenu(String id) {
        super(id);
    }

    public GameButton addMoveButton() {
        GameToggleButton moveButton = new GameToggleButton(BUTTON + getNextButtonID(), DrawableDef.create(DrawableDef.MOVE_BUTTON_I));
        moveButton.setOffMaterial(MaterialContainer.get(MaterialDef.MOVE_ICON_OFF_MATERIAL));
        moveButton.setOnMaterial(MaterialContainer.get(MaterialDef.MOVE_ICON_ON_MATERIAL));

        GenericObject actionIndicatorObj = ContainerManager.getActiveGameObjectsContainer().get(GameConstants.BUILDING_MOVEMENT_OVERLAY).asGenericObject();
        actionIndicatorObj.copyTranslationFromParent(true);
        actionIndicatorObj.scaleFromParent(true);
        actionIndicatorObj.rotationFromParent(true);
        actionIndicatorObj.moveTo(0f, 0f,1f);
        actionIndicatorObj.getTransforms().scaleTo(1f, 1f, 1f);
        actionIndicatorObj.getDrawable().setOpacity(0.5f);
        actionIndicatorObj.setCircularRB();
        actionIndicatorObj.setOnTouchListener(new GameBuildingMoveTouchListener());
        actionIndicatorObj.shouldDraw(false);

        moveButton.setActionIndicatorObject(actionIndicatorObj);
        moveButton.setOnToggleListener(newToggle -> {
            if (newToggle) {
                actionIndicatorObj.shouldDraw(true);
                actionIndicatorObj.setParent(moveButton.getActionObject());
            } else {
                actionIndicatorObj.shouldDraw(false);
                actionIndicatorObj.clearParent();
            }
        });

        super.addNewButton(moveButton);
        return moveButton;
    }

    public GameButton addUpgradeButton() {
        GameButton upgradeButton = new GameButton(BUTTON + getNextButtonID(), DrawableDef.create(DrawableDef.UPGRADE_BUTTON));
        upgradeButton.setOnClickListener(new OnClickListener() {
            @Override
            public boolean onClick(IGameObject object) {
                GameButton button = (GameButton)object;
                IGameObject actionObject = button.getActionObject();
                if (actionObject != null) {

                }
                return true;
            }

            @Override
            public boolean onLongClick(IGameObject object) {
                return false;
            }
        });
        super.addNewButton(upgradeButton);
        return upgradeButton;
    }

    public int getNumButtons() {
        return (int) getChildren().toList().stream().filter(child -> ((GameButton) child).isApplicable()).count();
    }

    @Override
    public boolean shouldAdd(GameButton gameButton) {
        return ((GameButton)gameButton).isApplicable();
    }
}
