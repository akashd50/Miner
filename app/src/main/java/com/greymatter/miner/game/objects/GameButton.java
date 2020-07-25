package com.greymatter.miner.game.objects;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GameButton extends GameObject {

    public GameButton(Drawable drawable) {
        super(drawable.getId(), drawable);
        getRigidBody().isStaticObject(true);
    }

    public GameButton(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
    }
}
