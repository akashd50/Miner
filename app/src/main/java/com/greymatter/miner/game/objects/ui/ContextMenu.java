package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.buttons.GameButton;
import com.greymatter.miner.game.objects.ui.buttons.GameToggleButton;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public class ContextMenu extends GameNotification {
    private static final String BUTTON = "BUTTON_";
    private static final String OBJECT_DIALOG = "OBJECT_NOTI";

    private FloatValueAnimator openingAnimator;
    private int buttonID;
    public ContextMenu(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public ContextMenu(String id) {
        super(OBJECT_DIALOG, DrawableDef.create(DrawableDef.OBJECT_DIALOG));
        initialize();
    }

    private void initialize() {
        buttonID = 0;
        setDefaultScale(new Vector3f(1.0f, 0.4f, 1f));
        addMoveButton();
    }

    public GameButton addMoveButton() {
        GameToggleButton button = new GameToggleButton(BUTTON + buttonID, DrawableDef.create(DrawableDef.MOVE_BUTTON_I));
        button.setOffMaterial(MaterialContainer.get(MaterialDef.MOVE_ICON_OFF_MATERIAL));
        button.setOnMaterial(MaterialContainer.get(MaterialDef.MOVE_ICON_ON_MATERIAL));
        buttonID++;

        addButtonAndReformatDialog(button);
        return button;
    }

    public void clearSelectionExcept(GameButton exceptThis) {
        for(IGameObject button : getChildren().toList()) {
            if (button.getId().compareTo(exceptThis.getId()) != 0) {
                ((GameButton) button).clearSelection();
            }
        }
    }

    private void addButtonAndReformatDialog(GameButton button) {
        float defaultScaleX = 0.3f;
        addChild(button.getId(), button);
        button.getTransforms()
                .copyTranslationFromParent(true)
                .copyRotationFromParent(true)
                .copyScaleFromParent(true);
        button.moveTo(0f, 0f, 1f);

        float redefinedScaleX = buttonID * 2 * defaultScaleX + buttonID * 0.1f + 0.1f;
        redefinedScaleX = redefinedScaleX/2;

        this.getTransforms().setDefaultScale(redefinedScaleX, 0.4f);

        float leftStart = - redefinedScaleX;
        for (int i = 0; i < buttonID; i++) {
            leftStart += 0.1f;

            GameButton currButton = (GameButton)getChild(BUTTON + i);

            leftStart += defaultScaleX;
            currButton.moveTo(leftStart/redefinedScaleX, 0f);

            currButton.getTransforms().setDefaultScale(defaultScaleX/redefinedScaleX, 0.6f);

            leftStart += defaultScaleX;
        }
    }
}
