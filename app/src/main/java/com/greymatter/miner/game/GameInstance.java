package com.greymatter.miner.game;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.objects.drawables.Instance;

public class GameInstance extends GameObject {
    public GameInstance(String id, Instance drawable) {
        super(id, drawable);
    }

    public GameInstance(Instance drawable) {
        super(drawable.getId(), drawable);
    }
}
