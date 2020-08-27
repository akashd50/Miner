package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GenericObject extends GameObject {
    public GenericObject(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public GenericObject(String id, Drawable drawable) {
        super(id, drawable);
    }
}
