package com.greymatter.miner.loaders;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.containers.UIToDrawContainer;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.GamePad;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;

public class UILoader extends Loader {
    public void load() {
        GameObjectsContainer.add("GAME_PAD", new GamePad(DrawableDef.create(DrawableDef.GAME_PAD_FRONT)));

        updateContainer();
    }

    public void updateContainer() {
        UIToDrawContainer.add(GameObjectsContainer.get("GAME_PAD"));
    }

    @Override
    public void onPostSurfaceInitializationHelper() {
        ((GamePad)GameObjectsContainer.get("GAME_PAD"))
                .setDefaultOnScreenLocation(AppServices.getUICamera().getCameraLeft()+0.8f,
                        AppServices.getUICamera().getCameraBottom()+0.6f);
        ((GamePad)GameObjectsContainer.get("GAME_PAD")).setCurrentControllableObject(
                (GameObject) GameObjectsContainer.get("MAIN_CHARACTER"));
    }
}
