package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Scanner extends GameBuilding {
    public Scanner(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Scanner(String id, Drawable drawable) {
        super(id, drawable);
    }
}
