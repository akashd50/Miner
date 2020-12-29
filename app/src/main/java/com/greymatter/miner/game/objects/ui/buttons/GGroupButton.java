package com.greymatter.miner.game.objects.ui.buttons;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GGroupButton extends GameButton {
    private boolean isApplicable;

    public GGroupButton(Drawable drawable) {
        super(drawable);
        initialize();
    }

    public GGroupButton(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        isApplicable = true;
    }

    public void clearSelection() {}

    public boolean isApplicable() {
        return isApplicable;
    }

    public GameButton setApplicable(boolean applicable) {
        isApplicable = applicable;
        return this;
    }
}
