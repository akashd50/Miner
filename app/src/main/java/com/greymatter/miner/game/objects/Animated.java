package com.greymatter.miner.game.objects;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Animated extends GameObject {
    public Animated(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Animated(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        getDrawable().animate(getAnimator());
    }
}
