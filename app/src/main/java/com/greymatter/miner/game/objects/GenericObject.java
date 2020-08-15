package com.greymatter.miner.game.objects;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GenericObject extends GameObject {
    public GenericObject(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public GenericObject(ObjId id, Drawable drawable) {
        super(id, drawable);
    }
}
