package com.greymatter.miner.game.objects;

import com.greymatter.miner.game.objects.base.GRigidBody;
import com.greymatter.miner.game.objects.ui.GameDialog;
import com.greymatter.miner.game.objects.ui.GameSignal;
import com.greymatter.miner.game.objects.ui.GenericMenu;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.CircularRB;
import com.greymatter.miner.physics.objects.rb.GeneralRB;
import com.greymatter.miner.physics.objects.rb.PolygonRB;
import com.greymatter.miner.physics.objects.rb.RectangularRB;
import java.util.ArrayList;

public abstract class GameObject extends GRigidBody {
    private boolean shouldDraw, isActive, shouldCheckClicks;
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
        this.shouldCheckClicks = true;
        this.objectLevel = 1;
        this.objectDrawable.setTransforms(getTransforms());
        this.getTransforms().setLinkedGameObject(this);

        try{
            this.setPolygonRB();
        }catch (NullPointerException e) {
            this.setGeneralRB();
        }
    }

    public void onFrameUpdate() {
        if(valueAnimator != null) {
            valueAnimator.update();
        }
    }

    public void onDrawFrame(Camera camera) {
        onFrameUpdate();
        if(shouldDraw) {
            objectDrawable.getRenderer().render(camera, this);
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
        this.shouldCheckClicks = shouldDraw;
        synchronized (getChildren()) {
            getChildren().toList().forEach(child -> {
                child.shouldDraw(shouldDraw);
            });
        }
        return this;
    }

    public GameObject isActive(boolean value) {
        this.isActive = value;
        return this;
    }

    public GameObject shouldCheckClicks(boolean value) {
        this.shouldCheckClicks = value;
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

    public GameObject setOptionsMenu(GenericMenu menu) {
        addChild("OPTIONS_MENU", menu);
        return this;
    }

    public GameObject setSignal(GameSignal signal) {
        addChild("SIGNAL", signal);
        return this;
    }

    public GameDialog getDialog() {
        return (GameDialog)getChild("DIALOG");
    }

    public GenericMenu getOptionsMenu() {
        return (GenericMenu)getChild("OPTIONS_MENU");
    }

    public GameSignal getSignal() {
        return (GameSignal)getChild("SIGNAL");
    }

    public GameObject setPolygonRB() {
        this.setRB(new PolygonRB(getId(), objectDrawable.getShape().getOrderedOuterMesh()));
        return this;
    }

    public GameObject setCircularRB() {
        this.setRB(new CircularRB(getId(), 1f));
        return this;
    }

    public GameObject setRectangularRB() {
        this.setRB(new RectangularRB(getId()));
        return this;
    }

    public GameObject setGeneralRB() {
        this.setRB(new GeneralRB(getId()));
        return this;
    }

    @Override
    public void onTransformsChanged() {
        super.onTransformsChanged();
        objectDrawable.onTransformsChanged();
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean shouldCheckClicks() {
        return shouldCheckClicks;
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
