package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Static extends GameBuilding {
    public Static(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Static(String id, Drawable drawable) {
        super(id, drawable);
    }
}
