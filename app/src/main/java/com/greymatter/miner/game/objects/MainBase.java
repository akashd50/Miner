package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class MainBase extends GameBuilding {
    public MainBase(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public MainBase(String id, Drawable drawable) {
        super(id, drawable);
    }
}
