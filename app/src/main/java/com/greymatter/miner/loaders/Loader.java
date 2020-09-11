package com.greymatter.miner.loaders;

import com.greymatter.miner.loaders.enums.definitions.LoaderDef;

public abstract class Loader {
    public abstract void load();
    public abstract void onPostSurfaceInitializationHelper();

    public static void loadAll() {
        LoaderDef[] types = LoaderDef.values();
        for (int i = 0; i < types.length; i++) {
            LoaderDef.create(types[i]).load();
        }
    }

    public static void onPostSurfaceInitialization() {
        LoaderDef[] types = LoaderDef.values();
        for (int i = 0; i < types.length; i++) {
            LoaderDef.create(types[i]).onPostSurfaceInitializationHelper();
        }
    }
}
