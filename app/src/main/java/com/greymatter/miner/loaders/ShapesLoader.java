package com.greymatter.miner.loaders;

import com.greymatter.miner.containers.ShapeContainer;
import com.greymatter.miner.loaders.enums.definitions.ShapeDef;

public class ShapesLoader extends Loader {
    public void load() {
        ShapeDef[] ids = ShapeDef.values();
        for (int i = 0; i < ids.length; i++) {
            ShapeContainer.add(ShapeDef.create(ids[i]));
        }
    }

    @Override
    public void onPostSurfaceInitializationHelper() {

    }
}
