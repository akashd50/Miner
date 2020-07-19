package com.greymatter.miner.game.objects;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GameNotification extends GameObject {

    public GameNotification(Drawable drawable) {
        super(drawable.getId(), drawable);
    }

    public GameNotification(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void runPostInitialization() {
        getLinkedObjects().forEach((id,object) -> {
            object.getTransforms().setParent(this.getTransforms()
                    .copyTranslationFromParent(true)
                    .copyRotationFromParent(true)
                    .copyScaleFromParent(true));
        });
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
    }

    public GameNotification setButton(ObjId index, GameButton button) {
        addLinkedGameObject(index, button);
        return this;
    }
}
