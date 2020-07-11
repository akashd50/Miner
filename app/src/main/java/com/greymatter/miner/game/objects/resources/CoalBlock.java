package com.greymatter.miner.game.objects.resources;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class CoalBlock extends ResourceBlock {
    public CoalBlock(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public CoalBlock(String id, Drawable drawable) {
        super(id, drawable);
    }

//    @Override
//    public CoalBlock addTag(String tag) {
//        super.addTag(tag);
//        return this;
//    }
}
