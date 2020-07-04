package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class InteractiveObject extends GameObject {
    public InteractiveObject(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public InteractiveObject(String id, Drawable drawable) {
        super(id, drawable);
    }

}
