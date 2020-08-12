package com.greymatter.miner.game.loaders;

import com.greymatter.miner.containers.ShapeContainer;
import com.greymatter.miner.enums.definitions.ShapeDef;

public class ShapesLoader extends Loader {
    public void load() {
        ShapeDef[] ids = ShapeDef.values();
        for (int i = 0; i < ids.length; i++) {
            ShapeContainer.add(ShapeDef.create(ids[i]));
        }
    }
}
