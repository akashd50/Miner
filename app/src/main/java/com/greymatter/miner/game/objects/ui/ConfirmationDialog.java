package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.buttons.GameButton;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import javax.vecmath.Vector3f;

public class ConfirmationDialog extends ButtonsMenu {
    public ConfirmationDialog(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public ConfirmationDialog(String id) {
        super(id, DrawableDef.create(DrawableDef.SIMPLE_DIALOG));
        initialize();
    }

    private void initialize() {
        super.addNewButton(new GameButton(BUTTON + super.getNextButtonID(), DrawableDef.create(DrawableDef.OK_BUTTON)));
        super.addNewButton(new GameButton(BUTTON + super.getNextButtonID(), DrawableDef.create(DrawableDef.CANCEL_BUTTON)));
    }

    @Override
    protected void setParentHelper(IGameObject parent) {
        Vector3f defaultTrans = getTransforms().getDefaultTranslation();
        moveTo(defaultTrans.x, defaultTrans.y, parent.getGlobalLocation().z);
        this.copyTranslationFromParent(false).scaleFromParent(false).rotationFromParent(false);
    }
}
