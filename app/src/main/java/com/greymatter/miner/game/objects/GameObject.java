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
import com.greymatter.miner.physics.objects.rb.GeneralRB;
import com.greymatter.miner.physics.objects.rb.PolygonRB;
import com.greymatter.miner.physics.objects.rb.RigidBody;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameObject {
    private boolean shouldDraw;
    private ObjId id;
    private int objectLevel;
    private ValueAnimator valueAnimator;
    private Transforms transforms;
    private Drawable objectDrawable;
    private RigidBody rigidBody;
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
        this.shouldDraw = true;
        this.objectLevel = 1;
        this.transforms = new Transforms();
        this.objectDrawable.setTransforms(transforms);

        try{
            this.setPolygonRB();
            this.setPolygonTC();
        }catch (NullPointerException e) {
            this.setGeneralRB();
        }
    }

    public void onFrameUpdate() {
        if(onAnimationFrameHandler != null && valueAnimator != null) {
            onAnimationFrameHandler.animate(this, valueAnimator);
        }
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
        this.children.put(id, object);
        this.transforms.addChild(object.getTransforms());
        return this;
    }

    public GameObject getChild(ObjId id) {
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
            if(onClickListener!=null) {
                onClickListener.onClick(this);
                return true;
            }
            if(onTouchListener!=null) {
                onTouchListener.onTouchUp(this, pointer);
                return true;
            }
        }
        return false;
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

    public GameObject setRigidBody(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
        this.rigidBody.setTransforms(transforms);
        return this;
    }

    public GameObject setPolygonRB() {
        this.setRigidBody(new PolygonRB(id, objectDrawable.getShape().getOrderedOuterMesh()));
        this.setPolygonTC();
        return this;
    }

    public GameObject setGeneralRB() {
        this.setRigidBody(new GeneralRB(id));
        return this;
    }

    public GameObject setPolygonTC() {
        touchChecker = new PolygonTouchChecker(getRigidBody()==null ?
                new PolygonRB(id, objectDrawable.getOrderedOuterMesh()) : getRigidBody().asPolygonRB());
        return this;
    }

    //object movement
    public GameObject moveBy(Vector2f moveTo) {
        transforms.translateBy(moveTo);
        return this;
    }

    public GameObject moveBy(float x, float y) {
        transforms.translateBy(x,y);
        return this;
    }

    public GameObject moveBy(float x, float y, float z) {
        transforms.translateBy(x,y,z);
        return this;
    }

    public GameObject moveBy(Vector3f moveTo) {
        transforms.translateBy(moveTo);
        return this;
    }

    public GameObject moveTo(Vector2f moveTo) {
        transforms.translateTo(moveTo);
        return this;
    }

    public GameObject moveTo(Vector3f moveTo) {
        transforms.translateTo(moveTo);
        return this;
    }

    public GameObject moveTo(float x, float y) {
        transforms.translateTo(x,y);
        return this;
    }

    public GameObject moveTo(float x, float y, float z) {
        transforms.translateTo(x,y,z);
        return this;
    }

    public GameObject scaleTo(float x, float y) {
        transforms.scaleTo(x,y);
        return this;
    }

    public GameObject scaleBy(float x, float y) {
        transforms.scaleBy(x,y);
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
        return transforms.getTranslation();
    }

    public boolean hasTag(Tag tag) {
        return this.objectTags.contains(tag);
    }

    public Drawable getDrawable() {
        return objectDrawable;
    }

    public Transforms getTransforms() {
        return transforms;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
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

    public ObjId getId() {
        return this.id;
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
