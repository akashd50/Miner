package com.greymatter.miner.game.objects;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Static extends GameBuilding {
    public Static(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Static(ObjId id, Drawable drawable) {
        super(id, drawable);
    }
}
