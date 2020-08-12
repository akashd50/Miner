package com.greymatter.miner.game.loaders;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.animators.IntegerValueAnimator;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.enums.definitions.MaterialDef;
import javax.vecmath.Vector4f;

public class MaterialLoader extends Loader {
    public void load() {
        MaterialDef[] ids = MaterialDef.values();
        for (int i = 0; i < ids.length; i++) {
            MaterialContainer.add(MaterialDef.create(ids[i]));
        }

        MaterialContainer.get(MaterialDef.GRADIENT_COLOR_MATERIAL).asColoredMaterial()
                .addColor(ShaderConst.GRADIENT_CENTER_COLOR, new Vector4f(0f,0.2f,0.2f,0.6f))
                .addColor(ShaderConst.GRADIENT_MID_COLOR, new Vector4f(0f,0.4f,0.3f,0.2f))
                .addColor(ShaderConst.GRADIENT_EDGE_COLOR, new Vector4f(0f,0.7f,0.3f,0f));
        MaterialContainer.get(MaterialDef.TREE_MATERIAL).asAnimatedTexturedMaterial()
                .setAnimationHandler(new IntegerValueAnimator().withFPS(6).withTotalFrames(5));
    }
}
