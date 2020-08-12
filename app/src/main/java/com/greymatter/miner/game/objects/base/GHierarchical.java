package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;

import java.util.ArrayList;

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

    public ArrayList<IGameObject> getChildrenWithTag(Tag tag) {
        ArrayList<IGameObject> toReturn = new ArrayList<>();
        getChildren().toList().forEach(object -> {
            if(object.hasTag(tag)) toReturn.add(object);
        });
        return toReturn;
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
