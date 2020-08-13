package com.greymatter.miner.game.objects;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.base.GTransformable;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.mainui.touch.touchcheckers.PolygonTouchChecker;
import com.greymatter.miner.mainui.touch.touchcheckers.TouchChecker;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.shader.ShaderHelper;
import com.greymatter.miner.physics.objects.rb.GeneralRB;
import com.greymatter.miner.physics.objects.rb.PolygonRB;
import com.greymatter.miner.physics.objects.rb.RigidBody;

import java.util.ArrayList;

import javax.vecmath.Vector2f;

public abstract class GameObject extends GTransformable {
    private boolean shouldDraw, isActive;
    private int objectLevel;
    private ValueAnimator valueAnimator;
    private Drawable objectDrawable;
    private RigidBody rigidBody;
    private ArrayList<Tag> objectTags;
    private TouchChecker touchChecker;
    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;
    public GameObject(ObjId id, Drawable drawable) {
        super(id);
        this.objectDrawable = drawable;
        this.objectTags = new ArrayList<>();
        this.shouldDraw = true;
        this.isActive = true;
        this.objectLevel = 1;
        this.objectDrawable.setTransforms(getTransforms());
        this.getTransforms().setLinkedGameObject(this);

        try{
            this.setPolygonRB();
            this.setPolygonTC();
        }catch (NullPointerException e) {
            this.setGeneralRB();
        }
    }

    public void onFrameUpdate() {
        if(valueAnimator != null) {
            valueAnimator.update();
        }
    }

    public void setShaderProperties(Camera camera) {
        Shader toUse = objectDrawable.getShader();
        ShaderHelper.useProgram(toUse);
        ShaderHelper.setCameraProperties(toUse, camera);
        objectDrawable.getMaterial().setShaderProperties(toUse);
        ShaderHelper.clearLightProperties(toUse);
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

    public GameObject addTag(Tag tag) {
        this.objectTags.add(tag);
        return this;
    }

    public GameObject shouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
        this.isActive = shouldDraw;
        getChildren().toList().forEach(child -> { child.shouldDraw(shouldDraw); });
        return this;
    }

    public GameObject isActive(boolean value) {
        this.isActive = value;
        return this;
    }

    public GameObject setAnimator(ValueAnimator valueAnimator) {
        this.valueAnimator = valueAnimator;
        this.valueAnimator.setToAnimateObject(this);
        return this;
    }

    public GameObject setTouchChecker(TouchChecker touchChecker) {
        this.touchChecker = touchChecker;
        return this;
    }

    public boolean onTouchDownEvent(Vector2f pointer) {
        for(IGameObject child : getChildren().toList()) {
            if(child.shouldDraw()) {
                boolean res = child.onTouchDownEvent(pointer);
                if (res) return res;
            }
        }

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
                onTouchListener.onTouchMove(this, pointer);
                return true;
            }
        }
        return false;
    }

    public boolean onTouchUpEvent(Vector2f pointer) {
        for(IGameObject child : getChildren().toList()) {
            if(child.shouldDraw()) {
                boolean res = child.shouldDraw() && child.onTouchUpEvent(pointer);
                if (res) return res;
            }
        }

        boolean isClicked = isClicked(pointer);
        if(isClicked) {
            if(onTouchListener!=null) {
                onTouchListener.onTouchUp(this, pointer);
            }

            if(onClickListener != null) {
                if(!AppServices.getTouchHelper().isTouchPoint1Drag()) {
                    onClickListener.onClick(this);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isClicked(Vector2f pointer) {
        return shouldDraw && touchChecker != null && touchChecker.isClicked(pointer);
    }

    public boolean isActive() {
        return isActive;
    }

    public GameObject setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
        return this;
    }

    public GameObject setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public GameObject setRB(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
        this.rigidBody.setTransforms(getTransforms());
        getTransforms().onTransformsChanged();
        return this;
    }

    public GameObject setPolygonRB() {
        this.setRB(new PolygonRB(getId(), objectDrawable.getShape().getOrderedOuterMesh()));
        this.setPolygonTC();
        return this;
    }

    public GameObject setGeneralRB() {
        this.setRB(new GeneralRB(getId()));
        return this;
    }

    public GameObject setPolygonTC() {
        touchChecker = new PolygonTouchChecker(getRigidBody()==null ?
                new PolygonRB(getId(), objectDrawable.getOrderedOuterMesh()) : getRigidBody().asPolygonRB());
        return this;
    }

    public TouchChecker getTouchChecker() {
        return this.touchChecker;
    }

    public int getObjectLevel() {
        return objectLevel;
    }

    public boolean hasTag(Tag tag) {
        return this.objectTags.contains(tag);
    }

    public Drawable getDrawable() {
        return objectDrawable;
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

    //typecasting
    public GameBuilding asGameBuilding() {
        return (GameBuilding)this;
    }

    public GameObjectWGL asGameObjectWGL() {
        return (GameObjectWGL)this;
    }

    public GameLight asGameLight() {
        return (GameLight) this;
    }

    public GenericObject asGenericObject() {
        return (GenericObject) this;
    }

    public ResourceBlock asResourceBlock() {
        return (ResourceBlock) this;
    }
}
