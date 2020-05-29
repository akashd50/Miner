package com.greymatter.miner;

import android.content.Context;
import android.content.res.AssetManager;

public class AppServices {
    private static AssetManager assetManager;

    public static void init(Context context) {
        assetManager = context.getAssets();
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }
}
