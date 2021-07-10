package com.greymatter.miner.game.objects.ui.buttons;


import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.GameUI;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GameButton extends GameUI {
    private IGameObject actionObject, actionIndicatorObject;
    private boolean isApplicable;
    public GameButton(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public GameButton(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        isApplicable = true;
    }

    public IGameObject getActionObject() {
        return actionObject;
    }

    public GameButton setActionObject(IGameObject actionObject) {
        this.actionObject = actionObject;
        return this;
    }

    public IGameObject getActionIndicatorObject() {
        return actionIndicatorObject;
    }

    public void setActionIndicatorObject(IGameObject actionIndicatorObject) {
        this.actionIndicatorObject = actionIndicatorObject;
    }

    public boolean isApplicable() {
        return isApplicable;
    }

    public void setApplicable(boolean applicable) {
        isApplicable = applicable;
    }

    public void clearSelection() {}
}
