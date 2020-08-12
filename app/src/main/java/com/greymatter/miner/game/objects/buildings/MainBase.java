package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class MainBase extends GameBuilding {
    public MainBase(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public MainBase(ObjId id, Drawable drawable) {
        super(id, drawable);
    }
}
