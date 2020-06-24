package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class InteractiveGameObject extends GameObject{
    public InteractiveGameObject(Drawable drawable) {
        super(drawable.getId(), drawable);
    }

    public InteractiveGameObject(String id, Drawable drawable) {
        super(id, drawable);
    }
}
