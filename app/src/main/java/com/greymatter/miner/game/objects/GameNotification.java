package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;

public class GameNotification extends GameObject {
    public GameNotification(Drawable drawable, RadialGradient rangeDrawable) {
        super(drawable.getId(), drawable);
    }

    public GameNotification(String id, Drawable drawable) {
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
