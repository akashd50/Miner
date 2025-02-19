package com.greymatter.miner.loaders;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.containers.AllGameObjectsContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.containers.ActiveUIContainer;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.GamePad;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;

import static com.greymatter.miner.game.GameConstants.*;

public class UILoader extends Loader {
    private final AllGameObjectsContainer allGameObjectsContainer = ContainerManager.getAllGameObjectsContainer();
    private final ActiveUIContainer activeUIContainer = ContainerManager.getActiveUIContainer();

    public void load() {
        allGameObjectsContainer.add(new GamePad(GAME_PAD));

        updateContainer();
    }

    public void updateContainer() {
        activeUIContainer.add(allGameObjectsContainer.get(GAME_PAD));
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
