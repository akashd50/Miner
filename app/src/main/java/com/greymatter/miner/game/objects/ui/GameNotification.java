package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public abstract class GameNotification extends GameUI {
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    private boolean isShowing;
    private FloatValueAnimator openingAnimator;
    public GameNotification(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        isShowing = false;
        openingAnimator = new FloatValueAnimator().withFPS(60).setPerFrameIncrement(0.1f);
        openingAnimator.setSingleCycle(true);
        openingAnimator.setToAnimateObject(this);
        openingAnimator.pause();

        openingAnimator.setOnAnimationFrameHandler((object, animator) -> {
            GameNotification notification = (GameNotification) object;
            float newVal = animator.getUpdatedFloat();
            scaleTo(notification.getDefaultScale().x * newVal, notification.getDefaultScale().y * newVal);
        });

        shouldDraw(false).addTag(Tag.NOTIFICATION);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        openingAnimator.update();
    }

    public GameNotification show() {
        isShowing = true;
        this.shouldDraw(true);
        showHelper();
        return this;
    }

    protected void showHelper() {
        this.getTransforms().scaleTo(0f,0f);
        openingAnimator.setBounds(0.5f, 1f);
        openingAnimator.startFrom(0.5f,true);
        openingAnimator.resume();
    }

    public GameNotification hide() {
        isShowing = false;
        this.shouldDraw(false);
        hideHelper();
        return this;
    }

    protected void hideHelper() {

    }

    @Override
    public GameNotification shouldDraw(boolean shouldDraw) {
        super.shouldDraw(isShowing);
        return this;
    }

    @Override
    public IGameObject setParent(IGameObject parent) {
        setParentHelper(parent);
        return super.setParent(parent);
    }

    protected void setParentHelper(IGameObject parent) {
        float r = parent.getTransforms().getScale().y + this.getDefaultScale().y;
        moveTo(0f,r, 1f);
        copyTranslationFromParent(true);
    }

    public GameNotification setOpeningAnimator(FloatValueAnimator openingAnimator) {
        this.openingAnimator = openingAnimator;
        return this;
    }

    public FloatValueAnimator getOpeningAnimator() {
        return openingAnimator;
    }

    public void linkTo(int position) {
        switch (position) {
            case TOP:
                float top = this.getParent().getTransforms().getScale().y + this.getDefaultScale().y;
                moveTo(0f, top);
                break;
            case BOTTOM:
                float bottom = -(this.getParent().getTransforms().getScale().y + this.getDefaultScale().y);
                moveTo(0f, bottom);
                break;
        }
    }

    public void linkTo(float x, float y) {
        float top = this.getParent().getTransforms().getScale().y + this.getDefaultScale().y;
        moveTo(x, y);
    }
}
