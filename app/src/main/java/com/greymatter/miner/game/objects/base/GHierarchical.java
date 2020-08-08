package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ObjId;

public abstract class GHierarchical extends GID {
    private HashMapE<ObjId, IGameObject> children;
    private IGameObject parent;
    public GHierarchical(ObjId id) {
        super(id);
        children = new HashMapE<>();
    }

    public IGameObject addChild(ObjId id, IGameObject object) {
        this.children.put(id, object);
        object.setParent(this);
        return this;
    }

    public IGameObject getChild(ObjId id) {
        return children.get(id);
    }

    public IGameObject setParent(IGameObject parent) {
        this.parent = parent;
        return this;
    }

    public IGameObject getParent() {
        return parent;
    }

    public HashMapE<ObjId, IGameObject> getChildren() {
        return children;
    }
}
