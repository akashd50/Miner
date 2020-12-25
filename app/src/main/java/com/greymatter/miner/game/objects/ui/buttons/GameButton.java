package com.greymatter.miner.game.objects.ui.buttons;


import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.GameUI;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GameButton extends GameUI {
    private IGameObject actionObject;
    private boolean isApplicable;

    public GameButton(Drawable drawable) {
        super(drawable.getId(), drawable);
        isApplicable = true;
    }

    public GameButton(String id, Drawable drawable) {
        super(id, drawable);
        isApplicable = true;
    }

    public void clearSelection() {}

    public IGameObject getActionObject() {
        return actionObject;
    }

    public GameButton setActionObject(IGameObject actionObject) {
        this.actionObject = actionObject;
        return this;
    }

    public boolean isApplicable() {
        return isApplicable;
    }

    public GameButton setApplicable(boolean applicable) {
        isApplicable = applicable;
        return this;
    }
}
