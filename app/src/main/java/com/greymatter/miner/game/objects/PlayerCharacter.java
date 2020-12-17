package com.greymatter.miner.game.objects;

import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class PlayerCharacter extends GameObject {
    public PlayerCharacter(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public PlayerCharacter(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public PlayerCharacter(String id) {
        super(id, DrawableDef.create(DrawableDef.MAIN_CHARACTER));
        initialize();
    }

    private void initialize() {
        addTag(Tag.DYNAMIC_PHYSICS_OBJECT);
    }
}
