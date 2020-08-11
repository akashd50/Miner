package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.ArrayList;

public abstract class GameBuilding extends GameObjectWGL {
    public GameBuilding(ObjId id, Drawable drawable) {
        super(id, drawable);
    }
}
