package com.greymatter.miner.game.objects;

import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Animated extends GameBuilding {
    public Animated(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Animated(String id, Drawable drawable) {
        super(id, drawable);
    }
}
