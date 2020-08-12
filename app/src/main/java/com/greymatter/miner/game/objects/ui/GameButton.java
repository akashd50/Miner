package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GameButton extends GameUI {

    public GameButton(Drawable drawable) {
        super(drawable.getId(), drawable);
    }

    public GameButton(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
    }
}
