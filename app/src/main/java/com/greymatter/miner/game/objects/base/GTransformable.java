package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;

import java.util.ArrayList;
import java.util.Comparator;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GTransformable extends GHierarchical {
    private Transforms transforms;
    private ArrayList<IGameObject> backgroundChildren, foregroundChildren;
    public GTransformable(String id) {
        super(id);
        this.transforms = new Transforms();
        backgroundChildren = new ArrayList<>();
        foregroundChildren = new ArrayList<>();
    }

    public Transforms getTransforms() {
        return transforms;
    }

    public void applyTransformations() {
        transforms.applyTransformations();
    }

    @Override
    public IGameObject addChild(String id, IGameObject object) {
        super.addChild(id, object);
        this.transforms.addChild(object.getTransforms());
        this.addChildHelper(object);
        return this;
    }

    private void addChildHelper(IGameObject object) {
        Comparator<IGameObject> listC = new Comparator<IGameObject>() {
            @Override
            public int compare(IGameObject o1, IGameObject o2) {
                return (int)(o1.getTransforms().getTranslation().z - o2.getTransforms().getTranslation().z);
            }
        };

        if (object.getTransforms().getTranslation().z > transforms.getTranslation().z
        || (object.getTransforms().isCopyTranslationFromParent() && object.getTransforms().getTranslation().z > 0f)) {
            foregroundChildren.add(object);
        }else{
            backgroundChildren.add(object);
        }

        foregroundChildren.sort(listC);
        backgroundChildren.sort(listC);
        //TODO: Fix the list not sorting if transforms are changed after the fact
    }

    private void reSortChildren() {
        foregroundChildren.clear();
        backgroundChildren.clear();

        for (IGameObject iGameObject : getChildren().toList()) {
            addChildHelper(iGameObject);
        }
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
        reSortChildren();
        return this;
    }

    public IGameObject moveBy(Vector3f moveTo) {
        transforms.translateBy(moveTo);
        reSortChildren();
        return this;
    }

    public IGameObject moveTo(Vector2f moveTo) {
        transforms.translateTo(moveTo);
        return this;
    }

    public IGameObject moveTo(float x, float y, float z) {
        transforms.translateTo(x,y,z);
        reSortChildren();
        return this;
    }

    public IGameObject moveTo(Vector3f moveTo) {
        transforms.translateTo(moveTo);
        reSortChildren();
        return this;
    }

    public IGameObject moveTo(float x, float y) {
        transforms.translateTo(x,y);
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

    public IGameObject copyTranslationFromParent(boolean val) {
        transforms.copyTranslationFromParent(val);
        return this;
    }

    public IGameObject rotationFromParent(boolean val) {
        transforms.copyRotationFromParent(val);
        return this;
    }

    public IGameObject scaleFromParent(boolean val) {
        transforms.copyScaleFromParent(val);
        return this;
    }

    public void onTransformsChanged() {

    }

    public ArrayList<IGameObject> getBackgroundChildren() {
        return backgroundChildren;
    }

    public ArrayList<IGameObject> getForegroundChildren() {
        return foregroundChildren;
    }

    public Vector3f getLocalLocation() {
        return transforms.getTranslation();
    }

    @Override
    public Vector3f getGlobalLocation() {
        return VectorHelper.getGlobalLocation(transforms);
    }
}
