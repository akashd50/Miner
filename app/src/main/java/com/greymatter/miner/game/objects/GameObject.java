package com.greymatter.miner.game.objects;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.mainui.touch.Clickable;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.mainui.touch.touchcheckers.PolygonTouchChecker;
import com.greymatter.miner.mainui.touch.touchcheckers.TouchChecker;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.animators.ValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.physics.objects.rb.RigidBody;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameObject {
    private boolean shouldDraw;
    private ObjId id;
    private int objectLevel;
    private ValueAnimator valueAnimator;
    private Drawable objectDrawable;
    private HashMapE<ObjId, GameObject> linkedObjects;
    private ArrayList<Tag> objectTags;
    private TouchChecker touchChecker;
    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;
    public GameObject(ObjId id, Drawable drawable) {
        this.id = id;
        this.objectDrawable = drawable;
        this.objectTags = new ArrayList<>();
        this.linkedObjects = new HashMapE<>();
        shouldDraw = true;
        objectLevel = 1;
    }

    public void runPostInitialization() {}
    public void onFrameUpdate() {}
    public void onDrawFrame() {
        objectDrawable.onDrawFrame();
    }

    public GameObject upgrade() {
        objectLevel++;
        return this;
    }

    public GameObject upgrade(int newLevel) {
        objectLevel = newLevel;
        return this;
    }

    protected GameObject addLinkedGameObject(ObjId id, GameObject object) {
        linkedObjects.put(id, object);
        return this;
    }

    protected GameObject getLinkedGameObject(ObjId id) {
        return linkedObjects.get(id);
    }

    public HashMapE<ObjId, GameObject> getLinkedObjects() {
        return linkedObjects;
    }

    public GameObject addTag(Tag tag) {
        this.objectTags.add(tag);
        return this;
    }

    public GameObject shouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
        return this;
    }

    public GameObject setValueAnimator(ValueAnimator valueAnimator) {
        this.valueAnimator = valueAnimator;
        return this;
    }

    public GameObject setTouchChecker(TouchChecker touchChecker) {
        this.touchChecker = touchChecker;
        return this;
    }

    public TouchChecker getTouchChecker() {
        return this.touchChecker;
    }

    public boolean onTouchDownEvent(Vector2f pointer) {
        boolean isClicked = isClicked(pointer);

        return isClicked;
    }

    public boolean onTouchMoveEvent(Vector2f pointer) {
        if(onTouchListener!=null) {
            if (isClicked(pointer)) {
                if (onTouchListener != null) onTouchListener.onTouchMove(this, pointer);
                return true;
            }
        }
        return false;
    }

    public boolean onTouchUpEvent(Vector2f pointer) {
        boolean isClicked = isClicked(pointer);
        if(isClicked) {
            if(onClickListener!=null) onClickListener.onClick();
            if(onTouchListener!=null) onTouchListener.onTouchUp(this, pointer);
        }
        return isClicked;
    }

    private boolean isClicked(Vector2f pointer) {
        return touchChecker != null && touchChecker.isClicked(pointer);
    }

    public GameObject setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
        return this;
    }

    public GameObject attachPolygonTouchChecker() {
        touchChecker = new PolygonTouchChecker(getRigidBody().asPolygonRB());
        return this;
    }

    //object movement
    public GameObject moveBy(Vector2f moveTo) {
        objectDrawable.getTransforms().translateBy(moveTo);
        return this;
    }

    public GameObject moveBy(float x, float y) {
        objectDrawable.getTransforms().translateBy(x,y);
        return this;
    }

    public GameObject moveBy(float x, float y, float z) {
        objectDrawable.getTransforms().translateBy(x,y,z);
        return this;
    }

    public GameObject moveBy(Vector3f moveTo) {
        objectDrawable.getTransforms().translateBy(moveTo);
        return this;
    }

    public GameObject moveTo(Vector2f moveTo) {
        objectDrawable.getTransforms().translateTo(moveTo);
        return this;
    }

    public GameObject moveTo(Vector3f moveTo) {
        objectDrawable.getTransforms().translateTo(moveTo);
        return this;
    }

    public GameObject moveTo(float x, float y) {
        objectDrawable.getTransforms().translateTo(x,y);
        return this;
    }

    public GameObject moveTo(float x, float y, float z) {
        objectDrawable.getTransforms().translateTo(x,y,z);
        return this;
    }

    public GameObject scaleTo(float x, float y) {
        objectDrawable.getTransforms().scaleTo(x,y);
        return this;
    }

    public GameObject scaleBy(float x, float y) {
        objectDrawable.getTransforms().scaleBy(x,y);
        return this;
    }

    public int getObjectLevel() {
        return objectLevel;
    }

    public Vector3f getLocation() {
        return objectDrawable.getRigidBody().getTranslation();
    }

    public boolean hasTag(Tag tag) {
        return this.objectTags.contains(tag);
    }

    public ObjId getId() {
        return this.id;
    }

    public Drawable getDrawable() {
        return objectDrawable;
    }

    public Transforms getTransforms() {
        return objectDrawable.getTransforms();
    }

    public RigidBody getRigidBody() {
        return objectDrawable.getRigidBody();
    }

    public int getNumTags() {
        return this.objectTags.size();
    }

    public boolean shouldDraw() {
        return shouldDraw;
    }

    public ValueAnimator getValueAnimator() {
        return valueAnimator;
    }

    public String toString() {
        return this.id.toString();
    }

    //typecasting
    public GameBuilding asGameBuilding() {
        return (GameBuilding)this;
    }

    public ResourceBlock asResourceBlock() {
        return (ResourceBlock) this;
    }
}
