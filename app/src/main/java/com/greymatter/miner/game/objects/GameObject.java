package com.greymatter.miner.game.objects;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.game.objects.base.GRBTouch;
import com.greymatter.miner.game.objects.ui.GameDialog;
import com.greymatter.miner.game.objects.ui.GameSignal;
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
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.GeneralRB;
import com.greymatter.miner.physics.objects.rb.PolygonRB;
import com.greymatter.miner.physics.objects.rb.RigidBody;
import java.util.ArrayList;
import javax.vecmath.Vector2f;

public abstract class GameObject extends GRBTouch {
    private boolean shouldDraw, isActive;
    private int objectLevel;
    private ValueAnimator valueAnimator;
    private Drawable objectDrawable;
    private ArrayList<Tag> objectTags;

    public GameObject(String id, Drawable drawable) {
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

    public GameObject setDialog(GameDialog gameDialog) {
        addChild("DIALOG", gameDialog);
        return this;
    }

    public GameObject setSignal(GameSignal signal) {
        addChild("SIGNAL", signal);
        return this;
    }

    public GameDialog getDialog() {
        return (GameDialog)getChild("DIALOG");
    }

    public GameSignal getSignal() {
        return (GameSignal)getChild("SIGNAL");
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
        setTouchChecker(new PolygonTouchChecker(getRigidBody()==null ?
                new PolygonRB(getId(), objectDrawable.getOrderedOuterMesh()) : getRigidBody().asPolygonRB()));
        return this;
    }

    public boolean isActive() {
        return isActive;
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
