package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class PowerGenerator extends GameBuilding {
    public PowerGenerator(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public PowerGenerator(String id, Drawable drawable) {
        super(id, drawable);
    }

}
