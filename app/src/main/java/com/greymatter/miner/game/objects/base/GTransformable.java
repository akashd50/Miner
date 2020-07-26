package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.opengl.objects.Transforms;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GTransformable extends GHierarchical {
    private Transforms transforms;
    public GTransformable(ObjId id) {
        super(id);
        this.transforms = new Transforms();
    }

    public Transforms getTransforms() {
        return transforms;
    }

    @Override
    public IGameObject addChild(ObjId id, IGameObject object) {
        super.addChild(id, object);
        this.transforms.addChild(object.getTransforms());
        return this;
    }

    public IGameObject moveBy(Vector2f moveTo) {
        transforms.translateBy(moveTo);
        return this;
    }

    public IGameObject moveBy(float x, float y) {
        transforms.translateBy(x,y);
        return this;
    }

    public IGameObject moveBy(float x, float y, float z) {
        transforms.translateBy(x,y,z);
        return this;
    }

    public IGameObject moveBy(Vector3f moveTo) {
        transforms.translateBy(moveTo);
        return this;
    }

    public IGameObject moveTo(Vector2f moveTo) {
        transforms.translateTo(moveTo);
        return this;
    }

    public IGameObject moveTo(Vector3f moveTo) {
        transforms.translateTo(moveTo);
        return this;
    }

    public IGameObject moveTo(float x, float y) {
        transforms.translateTo(x,y);
        return this;
    }

    public IGameObject moveTo(float x, float y, float z) {
        transforms.translateTo(x,y,z);
        return this;
    }

    public IGameObject scaleTo(float x, float y) {
        transforms.scaleTo(x,y);
        return this;
    }

    public IGameObject scaleBy(float x, float y) {
        transforms.scaleBy(x,y);
        return this;
    }

    public Vector3f getLocation() {
        return transforms.getTranslation();
    }
}
