package com.greymatter.miner.loaders;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.containers.AllGameObjectsContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.containers.UIToDrawContainer;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.GamePad;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;

import static com.greymatter.miner.game.GameConstants.*;

public class UILoader extends Loader {
    public void load() {
        ContainerManager.getAllGameObjectsContainer().add(GAME_PAD, new GamePad(DrawableDef.create(DrawableDef.GAME_PAD_FRONT)));

        updateContainer();
    }

    public void updateContainer() {
        UIToDrawContainer.add(ContainerManager.getAllGameObjectsContainer().get(GAME_PAD));
    }

    @Override
    public void onPostSurfaceInitializationHelper() {
        ((GamePad) ContainerManager.getAllGameObjectsContainer().get(GAME_PAD))
                .setDefaultOnScreenLocation(AppServices.getUICamera().getCameraLeft()+0.8f,
                        AppServices.getUICamera().getCameraBottom()+0.6f);
        ((GamePad) ContainerManager.getAllGameObjectsContainer().get(GAME_PAD)).setCurrentControllableObject(
                (GameObject) ContainerManager.getAllGameObjectsContainer().get(MAIN_CHARACTER_1));
    }
}
