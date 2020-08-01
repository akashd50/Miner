package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.impl.DialogAnimator;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public class GameDialog extends GameUI {
    private Vector3f defaultScale;
    private boolean isAnimationComplete;
    public GameDialog(Drawable drawable) {
        super(drawable.getId(), drawable);
        isAnimationComplete = true;
        defaultScale = new Vector3f();
        setOnAnimationFrameHandler(new DialogAnimator());
    }

    public GameDialog(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
    }

    public GameDialog setButtonI(GameButton button) {
        addChild(ObjId.NOT_BUTTON_I, button);
        button.getTransforms()
                    .copyTranslationFromParent(true)
                    .copyRotationFromParent(true)
                    .copyScaleFromParent(true);
        button.scaleTo(0.4f,0.2f).moveTo(-0.5f, -0.4f, 2f);
        return this;
    }

    public GameDialog setButtonII(GameButton button) {
        addChild(ObjId.NOT_BUTTON_II, button);
        button.getTransforms()
                .copyTranslationFromParent(true)
                .copyRotationFromParent(true)
                .copyScaleFromParent(true);
        button.scaleTo(0.4f,0.2f).moveTo(0.5f, -0.4f, 2f);
        return this;
    }

    public GameDialog setButtonIClickListener(OnClickListener listener) {
        getChild(ObjId.NOT_BUTTON_I).setOnClickListener(listener);
        return this;
    }

    public GameDialog setButtonIIClickListener(OnClickListener listener) {
        getChild(ObjId.NOT_BUTTON_II).setOnClickListener(listener);
        return this;
    }

    public GameDialog show() {
        isAnimationComplete = false;
        getTransforms().scaleTo(0f,0f);
        this.shouldDraw(true);
        return this;
    }

    public GameDialog hide() {
        isAnimationComplete = true;
        this.shouldDraw(false);
        return this;
    }

    public GameDialog isAnimationComplete(boolean isAnimationComplete) {
        this.isAnimationComplete = isAnimationComplete;
        return this;
    }

    public GameDialog setDefaultScale(Vector3f scale) {
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
