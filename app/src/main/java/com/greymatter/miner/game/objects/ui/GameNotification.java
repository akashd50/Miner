package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public abstract class GameNotification extends GameUI {
    private Vector3f defaultScale;
    private boolean isAnimationComplete;

    public GameNotification(ObjId id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        isAnimationComplete = true;
        defaultScale = new Vector3f();

        setOnAnimationFrameHandler((object, animator) -> {
            GameNotification notification = (GameNotification) object;
            if(notification.shouldDraw() && !notification.isAnimationComplete()) {
                Vector3f scale = notification.getTransforms().getScale();
                float newVal = animator.update().getUpdatedFloat();
                notification.scaleTo(notification.getDefaultScale().x * newVal, notification.getDefaultScale().y * newVal);
                if(newVal >= 1f - animator.getPerFrameIncrement()) {
                    notification.isAnimationComplete(true);
                    notification.getAnimator().reset();
                }
            }
        });

        shouldDraw(false).addTag(Tag.NOTIFICATION);
    }

    public GameNotification show() {
        isAnimationComplete = false;
        getTransforms().scaleTo(0f,0f);
        this.shouldDraw(true);

        return this;
    }

    public GameNotification hide() {
        isAnimationComplete = true;
        this.shouldDraw(false);
        return this;
    }

    @Override
    public IGameObject setParent(IGameObject parent) {
        float r = parent.getTransforms().getScale().y/2 + this.defaultScale.y/2 + 0.6f;
        moveTo(0f,r, 1.5f);
        translationFromParent(true);
        return super.setParent(parent);
    }

    public GameNotification isAnimationComplete(boolean isAnimationComplete) {
        this.isAnimationComplete = isAnimationComplete;
        return this;
    }

    public GameNotification setDefaultScale(Vector3f scale) {
        this.defaultScale.set(scale);
        return this;
    }

    public boolean isAnimationComplete() {
        return isAnimationComplete;
    }

    public Vector3f getDefaultScale() {
        return defaultScale;
    }
}
