package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.buttons.GMoveButton;
import com.greymatter.miner.game.objects.ui.buttons.GUpgradeButton;
import com.greymatter.miner.game.objects.ui.buttons.GameButton;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.concurrent.atomic.AtomicReference;
import javax.vecmath.Vector3f;

public class OptionsMenu extends GameNotification {
    protected static final String BUTTON = "BUTTON_";
    public static final String MOVE_BUTTON = "MOVE_BUTTON";
    private static final String OBJECT_DIALOG = "DIALOG";

    private int buttonID;
    private IGameObject linkedGameObject;
    public OptionsMenu(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public OptionsMenu(String id) {
        super(id, DrawableDef.create(DrawableDef.OBJECT_DIALOG));
        initialize();
    }

    private void initialize() {
        buttonID = 0;
        setDefaultScale(new Vector3f(1.0f, 0.4f, 1f));
    }

    public GameButton addMoveButton() {
        GMoveButton button = new GMoveButton(BUTTON + getNextButtonID());
        addButtonAndReformatDialog(button);
        return button;
    }

    public GameButton addUpgradeButton() {
        GUpgradeButton button = new GUpgradeButton(BUTTON + getNextButtonID());
        addButtonAndReformatDialog(button);
        return button;
    }

    public GameButton addNewButton(GameButton button) {
        addButtonAndReformatDialog(button);
        return button;
    }

    public OptionsMenu setActionObject(IGameObject gameObject) {
        this.linkedGameObject = gameObject;
        getChildren().toList().forEach(child -> {
            GameButton button = ((GameButton)child);
            if(button.isApplicable()) {
                button.setActionObject(gameObject);
            }
        });
        return this;
    }

    public void clearSelectionExcept(GameButton exceptThis) {
        for(IGameObject button : getChildren().toList()) {
            if (button.getId().compareTo(exceptThis.getId()) != 0) {
                ((GameButton) button).clearSelection();
            }
        }
    }

    private void addButtonAndReformatDialog(GameButton button) {
        addChild(button.getId(), button);
        button.getTransforms()
                .copyTranslationFromParent(true)
                .copyRotationFromParent(true)
                .copyScaleFromParent(true);
        button.moveTo(0f, 0f, 1f);

        refresh();
    }

    public void refresh() {
        float defaultScaleX = 0.3f;

        int activeButtons = getApplicableButtons();
        float redefinedScaleX = activeButtons * 2 * defaultScaleX + activeButtons * 0.1f + 0.1f;
        redefinedScaleX = redefinedScaleX/2;

        this.getTransforms().setDefaultScale(redefinedScaleX, 0.4f);

        AtomicReference<Float> leftStart = new AtomicReference<>(-redefinedScaleX);
        final float oneSidedScaleX = redefinedScaleX;
        getChildren().toList().forEach(child -> {
            GameButton currButton = (GameButton)child;
            if (currButton.isApplicable()) {
                leftStart.updateAndGet(v -> v + 0.1f);
                leftStart.updateAndGet(v -> v + defaultScaleX);

                currButton.moveTo(leftStart.get() / oneSidedScaleX, 0f);
                currButton.getTransforms().setDefaultScale(defaultScaleX / oneSidedScaleX, 0.6f);

                leftStart.updateAndGet(v -> v + defaultScaleX);
            }
        });
    }

    public int getApplicableButtons() {
        return (int) getChildren().toList().stream().filter(child -> ((GameButton) child).isApplicable()).count();
    }

    public int getNextButtonID() {
        int toReturn = buttonID;
        buttonID++;
        return toReturn;
    }
}
