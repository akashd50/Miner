package com.greymatter.miner.game.objects.resources;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public abstract class ResourceBlock extends GameObject {
    public ResourceBlock(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public ResourceBlock(String id, Drawable drawable) {
        super(id, drawable);
    }


}
