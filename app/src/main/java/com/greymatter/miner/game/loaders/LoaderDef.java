package com.greymatter.miner.game.loaders;

public enum LoaderDef {
    SHADER_LOADER,
    MATERIAL_LOADER,
    SHAPES_LOADER,
    RESOURCE_LOADER,
    WORLD_LOADER;

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
