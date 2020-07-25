package com.greymatter.miner.game.objects;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GameNotification extends GameObject {

    public GameNotification(Drawable drawable) {
        super(drawable.getId(), drawable);
        getRigidBody().isStaticObject(true);
    }

    public GameNotification(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
    }

    public GameNotification setButtonI(GameButton button) {
        addChild(ObjId.NOT_BUTTON_I, button);
        button.getTransforms().setParent(this.getTransforms())
                    .copyTranslationFromParent(true)
                    .copyRotationFromParent(true)
                    .copyScaleFromParent(true);
        button.scaleTo(0.4f,0.2f).moveTo(-0.5f, -0.7f, 2f);
        return this;
    }

    public GameNotification setButtonII(GameButton button) {
        addChild(ObjId.NOT_BUTTON_II, button);
        button.getTransforms().setParent(this.getTransforms())
                .copyTranslationFromParent(true)
                .copyRotationFromParent(true)
                .copyScaleFromParent(true);
        button.scaleTo(0.4f,0.2f).moveTo(0.5f, -0.7f, 2f);
        return this;
    }
}
