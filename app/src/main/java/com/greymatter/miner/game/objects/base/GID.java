package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.loaders.enums.ObjId;

public abstract class GID implements IGameObject {
    private ObjId id;
    public GID(ObjId id) {
        this.id = id;
    }

    public ObjId getId() {
        return id;
    }

    public String toString() {
        return this.id.toString();
    }
}
