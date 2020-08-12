package com.greymatter.miner.loaders;

import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.loaders.enums.definitions.ShaderDef;

public class ShaderLoader extends Loader {
    public void load() {
        ShaderDef[] ids = ShaderDef.values();
        for (int i = 0; i < ids.length; i++) {
            ShaderContainer.addShader(ShaderDef.create(ids[i]));
        }
    }
}
