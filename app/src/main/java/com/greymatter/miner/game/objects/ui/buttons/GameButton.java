package com.greymatter.miner.game.objects.ui.buttons;


import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.GameUI;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GameButton extends GameUI {
    private IGameObject actionObject;

    public GameButton(Drawable drawable) {
        super(drawable.getId(), drawable);
    }

    public GameButton(String id, Drawable drawable) {
        super(id, drawable);
    }

    public IGameObject getActionObject() {
        return actionObject;
    }

    public GameButton setActionObject(IGameObject actionObject) {
        this.actionObject = actionObject;
        return this;
    }
}
