package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ObjId;

public abstract class GHierarchical extends GID {
    private HashMapE<ObjId, IGameObject> children;
    public GHierarchical(ObjId id) {
        super(id);
        children = new HashMapE<>();
    }

    public IGameObject addChild(ObjId id, IGameObject object) {
        this.children.put(id, object);
        return this;
    }

    public IGameObject getChild(ObjId id) {
        return children.get(id);
    }

    public HashMapE<ObjId, IGameObject> getChildren() {
        return children;
    }
}
