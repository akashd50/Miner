package com.greymatter.miner.game.objects;

import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.mainui.touch.touchcheckers.PolygonTouchChecker;
import com.greymatter.miner.mainui.touch.touchcheckers.TouchChecker;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
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
    private HashMapE<ObjId, GameObject> children;
    private ArrayList<Tag> objectTags;
    private TouchChecker touchChecker;
    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;
    private OnAnimationFrameHandler onAnimationFrameHandler;
    public GameObject(ObjId id, Drawable drawable) {
        this.id = id;
        this.objectDrawable = drawable;
        this.objectTags = new ArrayList<>();
        this.children = new HashMapE<>();
        shouldDraw = true;
        objectLevel = 1;
    }

    public void runPostInitialization() {}

    public void onFrameUpdate() {
        if(onAnimationFrameHandler != null && valueAnimator != null) {
            onAnimationFrameHandler.animate(this, valueAnimator);
        }

        objectDrawable.getTransforms().applyTransformations();
        children.forEach((id, object) -> {
            object.getDrawable().getTransforms().applyTransformationsForced();
        });
    }

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

    public GameObject setOnAnimationFrameHandler(OnAnimationFrameHandler handler) {
        this.onAnimationFrameHandler = handler;
        return this;
    }

    protected GameObject addChild(ObjId id, GameObject object) {
        children.put(id, object);
        return this;
    }

    protected GameObject getChild(ObjId id) {
        return children.get(id);
    }

    public GameObject addTag(Tag tag) {
        this.objectTags.add(tag);
        return this;
    }

    public GameObject shouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
        return this;
    }

    public GameObject setAnimator(ValueAnimator valueAnimator) {
        this.valueAnimator = valueAnimator;
        return this;
    }

    public GameObject setTouchChecker(TouchChecker touchChecker) {
        this.touchChecker = touchChecker;
        return this;
    }

    public boolean onTouchDownEvent(Vector2f pointer) {
        if(onTouchListener!=null) {
            if (isClicked(pointer)) {
                onTouchListener.onTouchDown(this, pointer);
                return true;
            }
        }
        return false;
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

    public GameObject setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
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

    public HashMapE<ObjId, GameObject> getChildren() {
        return children;
    }

    public TouchChecker getTouchChecker() {
        return this.touchChecker;
    }

    public int getObjectLevel() {
        return objectLevel;
    }

    public Vector3f getLocation() {
        return objectDrawable.getTransforms().getTranslation();
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

    public ValueAnimator getAnimator() {
        return valueAnimator;
    }

    public String toString() {
        return this.id.toString();
    }

    //typecasting
    public GameBuilding asGameBuilding() {
        return (GameBuilding)this;
    }

    public GameLight asGameLight() {
        return (GameLight) this;
    }

    public Animated asAnimatedObject() {
        return (Animated) this;
    }

    public ResourceBlock asResourceBlock() {
        return (ResourceBlock) this;
    }
}
