package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public abstract class GameNotification extends GameUI {
    private FloatValueAnimator openingAnimator;
    public GameNotification(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        openingAnimator = new FloatValueAnimator().withFPS(60).setPerFrameIncrement(0.1f);
        openingAnimator.setSingleCycle(true);
        openingAnimator.setToAnimateObject(this);
        openingAnimator.pause();

        openingAnimator.setOnAnimationFrameHandler((object, animator) -> {
            GameNotification notification = (GameNotification) object;
            float newVal = animator.getUpdatedFloat();
            scaleTo(notification.getDefaultScale().x * newVal, notification.getDefaultScale().y * newVal);
            if(newVal >= openingAnimator.getUpperBound()) {
                openingAnimator.setBounds(1.0f, 1.1f);
                openingAnimator.startFrom(1.1f,false);
                openingAnimator.resume();
            }
        });

        shouldDraw(false).addTag(Tag.NOTIFICATION);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        openingAnimator.update();
    }

    public GameNotification show() {
        this.getTransforms().scaleTo(0f,0f);
        this.shouldDraw(true);

        openingAnimator.setBounds(0.5f, 1.1f);
        openingAnimator.startFrom(0.5f,true);
        openingAnimator.resume();
        return this;
    }

    public GameNotification hide() {
        this.shouldDraw(false);
        return this;
    }

    @Override
    public IGameObject setParent(IGameObject parent) {
        float r = parent.getTransforms().getScale().y + this.getDefaultScale().y;
        moveTo(0f,r, 1f);
        copyTranslationFromParent(true);
        return super.setParent(parent);
    }
}
