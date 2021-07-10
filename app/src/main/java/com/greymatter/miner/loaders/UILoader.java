package com.greymatter.miner.loaders;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.containers.ActiveGameObjectContainer;
import com.greymatter.miner.containers.AllGameObjectsContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.containers.ActiveUIContainer;
import com.greymatter.miner.game.objects.GamePad;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.menu_impls.BuildingOptionsMenu;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;

import static com.greymatter.miner.game.GameConstants.*;

public class UILoader extends Loader {
    private final AllGameObjectsContainer allGameObjectsContainer = ContainerManager.getAllGameObjectsContainer();
    private final ActiveUIContainer activeUIContainer = ContainerManager.getActiveUIContainer();
    private final ActiveGameObjectContainer activeGameObjectContainer = ContainerManager.getActiveGameObjectsContainer();

    public void load() {
        // load game object overlays for action buttons
        GenericObject moveIndicatorObj = new GenericObject(BUILDING_MOVEMENT_OVERLAY, DrawableDef.create(DrawableDef.BUILDING_MOVE_TARGET));
        activeGameObjectContainer.add(moveIndicatorObj);

        // preload other UI elements
        allGameObjectsContainer.add(new GamePad(GAME_PAD));

        BuildingOptionsMenu optionsMenu = new BuildingOptionsMenu(BUILDING_OPTIONS_MENU);
        optionsMenu.addMoveButton();
        optionsMenu.addUpgradeButton();
        optionsMenu.hide();

        allGameObjectsContainer.add(optionsMenu);

        activeUIContainer.add(allGameObjectsContainer.get(GAME_PAD));
        activeUIContainer.add(optionsMenu);
    }

    @Override
    public void onPostSurfaceInitializationHelper() {
        float posX = AppServices.getUICamera().getCameraLeft() + 0.8f;
        float posY = AppServices.getUICamera().getCameraBottom() + 0.6f;
        IGameObject mainCharacter = allGameObjectsContainer.get(MAIN_CHARACTER_1);
        ((GamePad) allGameObjectsContainer.get(GAME_PAD)).setDefaultOnScreenLocation(posX, posY);
        ((GamePad) allGameObjectsContainer.get(GAME_PAD)).setCurrentControllableObject(mainCharacter);
    }
}
