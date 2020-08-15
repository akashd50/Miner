package com.greymatter.miner.loaders.enums.definitions;

import com.greymatter.miner.loaders.Loader;
import com.greymatter.miner.loaders.MaterialLoader;
import com.greymatter.miner.loaders.ResourceLoader;
import com.greymatter.miner.loaders.ShaderLoader;
import com.greymatter.miner.loaders.ShapesLoader;
import com.greymatter.miner.loaders.WorldLoader;

public enum LoaderDef {
    SHADER_LOADER,
    MATERIAL_LOADER,
    SHAPES_LOADER,
    WORLD_LOADER,
    RESOURCE_LOADER;

    public static Loader create(LoaderDef type) {
        switch (type) {
            case RESOURCE_LOADER:
                return new ResourceLoader();
            case WORLD_LOADER:
                return new WorldLoader();
            case SHADER_LOADER:
                return new ShaderLoader();
            case MATERIAL_LOADER:
                return new MaterialLoader();
            case SHAPES_LOADER:
                return new ShapesLoader();
            default:
                return null;
        }
    }
}
