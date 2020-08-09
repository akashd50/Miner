package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
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
            GameNotification dialog = (GameNotification) object;
            if(dialog.shouldDraw() && !dialog.isAnimationComplete()) {
                Vector3f scale = dialog.getTransforms().getScale();
                float newVal = animator.update().getUpdatedFloat();
                dialog.scaleTo(dialog.getDefaultScale().x * newVal, dialog.getDefaultScale().y * newVal);
                if(scale.x >= dialog.getDefaultScale().x-0.2 || scale.y >= dialog.getDefaultScale().y-0.2) {
                    dialog.isAnimationComplete(true);
                    dialog.getAnimator().reset();
                }
            }
        });

        shouldDraw(false).translationFromParent(true).addTag(Tag.NOTIFICATION);
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
        float r = parent.getTransforms().getScale().y + this.getTransforms().getScale().y + 0.2f;
        moveTo(0f,r, 1.5f);
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
