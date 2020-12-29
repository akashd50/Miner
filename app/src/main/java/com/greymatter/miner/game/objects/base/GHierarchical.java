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
        if (object.getParent() == null ||
            object.getParent().getId().compareTo(this.getId()) != 0) {
            object.setParent(this);
        }
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
        if (!parent.getChildren().containsKey(this.getId())) {
            parent.addChild(this.getId(), this);
        }
        //parent.getChildren().put(this.getId(), this);
        return this;
    }

    public void clearParent() {
        this.parent.getChildren().remove(this.getId());
        this.parent = null;
    }

    public void removeChild(IGameObject child) {
        this.children.remove(child.getId());
        child.setParent(null);
    }

    public IGameObject getParent() {
        return parent;
    }

    public HashMapE<String, IGameObject> getChildren() {
        return children;
    }
}
