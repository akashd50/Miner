package com.greymatter.miner.game.loaders;

public abstract class Loader {
    public abstract void load();

    public static void loadAll() {
        LoaderDef[] types = LoaderDef.values();
        for (int i = 0; i < types.length; i++) {
            LoaderDef.create(types[i]).load();
        }
    }
}
