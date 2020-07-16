package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Miner extends GameBuilding {
    public Miner(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Miner(String id, Drawable drawable) {
        super(id, drawable);
    }
}
