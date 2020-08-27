package com.greymatter.miner.game.objects.base;

public abstract class GID implements IGameObject {
    private String id;
    public GID(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return this.id.toString();
    }
}
