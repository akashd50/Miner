package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Townhall extends GameBuilding {
    public Townhall(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Townhall(String id, Drawable drawable) {
        super(id, drawable);
    }
}
