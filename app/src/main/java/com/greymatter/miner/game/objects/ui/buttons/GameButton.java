package com.greymatter.miner.game.objects.ui.buttons;

import com.greymatter.miner.game.objects.ui.GameUI;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GameButton extends GameUI {

    public GameButton(Drawable drawable) {
        super(drawable.getId(), drawable);
    }

    public GameButton(String id, Drawable drawable) {
        super(id, drawable);
    }

    public void clearSelection() {}
}
