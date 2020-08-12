package com.greymatter.miner.game.objects;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class PlayerCharacter extends GameObject {
    public PlayerCharacter(ObjId id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public PlayerCharacter(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {

        addTag(Tag.DYNAMIC_PHYSICS_OBJECT);
    }
}
