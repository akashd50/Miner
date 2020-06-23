package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class ControllableGameObject extends GameObject{
    public ControllableGameObject(Drawable drawable) {
        super(drawable.getId(), drawable);
    }

    public ControllableGameObject(String id, Drawable drawable) {
        super(id, drawable);
    }
}
