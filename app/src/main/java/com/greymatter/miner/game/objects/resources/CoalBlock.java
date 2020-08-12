package com.greymatter.miner.game.objects.resources;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class CoalBlock extends ResourceBlock {
    public CoalBlock(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public CoalBlock(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

//    @Override
//    public CoalBlock addTag(String tag) {
//        super.addTag(tag);
//        return this;
//    }
}
