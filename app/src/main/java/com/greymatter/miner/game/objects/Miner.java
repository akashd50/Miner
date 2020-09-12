package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Miner extends GameObject {
    public Miner(String id, Drawable drawable) {
        super(id, drawable);
    }

    public Miner(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {

    }
}
