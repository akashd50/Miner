package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.loaders.enums.Tag;

import java.util.ArrayList;

public abstract class GHierarchical extends GID {
    private HashMapE<String, IGameObject> children;
    private IGameObject parent;
    public GHierarchical(String id) {
        super(id);
        children = new HashMapE<>();
    }

    public IGameObject addChild(String id, IGameObject object) {
        this.children.put(id, object);
        object.setParent(this);
        return this;
    }

    public IGameObject getChild(String id) {
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

    public HashMapE<String, IGameObject> getChildren() {
        return children;
    }
}
