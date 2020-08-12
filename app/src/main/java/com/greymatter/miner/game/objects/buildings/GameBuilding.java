package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public abstract class GameBuilding extends GameObjectWGL {
    public GameBuilding(ObjId id, Drawable drawable) {
        super(id, drawable);
    }
}
