package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Planet extends GameBuilding {
    public Planet(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Planet(String id, Drawable drawable) {
        super(id, drawable);
    }
}
