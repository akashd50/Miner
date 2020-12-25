package com.greymatter.miner.game.objects.ui.buttons;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class GUpgradeButton extends GameButton {

    public GUpgradeButton(Drawable drawable) {
        super(drawable);
        initialize();
    }

    public GUpgradeButton(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public GUpgradeButton(String id) {
        super(id, DrawableDef.create(DrawableDef.UPGRADE_BUTTON));
        initialize();
    }

    private void initialize() {

    }

    @Override
    public boolean onClick(IGameObject object) {
        super.onClick(object);
        IGameObject actionObject = getActionObject();
        if (actionObject != null) {

        }
        return true;
    }
}
