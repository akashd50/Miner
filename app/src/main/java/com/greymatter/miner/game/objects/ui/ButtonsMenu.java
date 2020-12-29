package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.ui.buttons.GameButton;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.concurrent.atomic.AtomicReference;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class ButtonsMenu extends GameNotification {
    protected static final String BUTTON = "BUTTON_";

    private int buttonID;
    private IGameObject linkedGameObject;
    private float buttonsSpacing;
    private float buttonsScale;
    private Vector2f screenBottomLocation;
    public ButtonsMenu(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public ButtonsMenu(String id) {
        super(id, DrawableDef.create(DrawableDef.OBJECT_DIALOG));
        initialize();
    }

    private void initialize() {
        buttonID = 0;
        buttonsSpacing = 0.05f;
        screenBottomLocation = new Vector2f(0f, -1.1f);
        setDefaultScale(new Vector3f(1f, 0.1f, 1f));
        getTransforms().setDefaultTranslation(0f, -0.9f, 25f);

        FloatValueAnimator openingAnimator = new FloatValueAnimator().withFPS(30).setPerFrameIncrement(0.2f);
        openingAnimator.setSingleCycle(true);
        openingAnimator.setToAnimateObject(this);
        openingAnimator.pause();

        openingAnimator.setOnAnimationFrameHandler(new OnAnimationFrameHandler() {
            @Override
            public void onAnimationFrame(GameObject object, ValueAnimator animator) {
                Vector3f defaultLocation = getTransforms().getDefaultTranslation();
                float animValue = animator.getUpdatedFloat();
                float x = (defaultLocation.x * (1f - animValue) + screenBottomLocation.x * animValue);
                float y = (defaultLocation.y * (1f - animValue) + screenBottomLocation.y * animValue);
                object.moveTo(x,y);
            }
        });

        setOpeningAnimator(openingAnimator);
    }

    public GameButton addNewButton(GameButton button) {
        addChild(button.getId(), button);
        button.getTransforms()
                .copyTranslationFromParent(true)
                .copyRotationFromParent(true)
                .copyScaleFromParent(true);
        button.moveTo(0f, 0f, 1f);

        refresh();

        return button;
    }

    public ButtonsMenu setActionObject(IGameObject gameObject) {
        this.linkedGameObject = gameObject;
        getChildren().toList().forEach(child -> {
            GameButton button = ((GameButton)child);
            if(shouldAdd(button)) {
                button.setActionObject(gameObject);
            }
        });
        return this;
    }

    public void refresh() {
        float defaultScaleX = 3*buttonsSpacing;

        int activeButtons = getNumButtons();
        float redefinedScaleX = activeButtons * 2 * defaultScaleX + activeButtons * buttonsSpacing + buttonsSpacing;
        redefinedScaleX = redefinedScaleX/2;

        this.getTransforms().setDefaultScale(redefinedScaleX, getDefaultScale().y);

        AtomicReference<Float> leftStart = new AtomicReference<>(-redefinedScaleX);
        final float oneSidedScaleX = redefinedScaleX;
        getChildren().toList().forEach(child -> {
            GameButton currButton = (GameButton)child;
            if (shouldAdd(currButton)) {
                leftStart.updateAndGet(v -> v + buttonsSpacing);
                leftStart.updateAndGet(v -> v + defaultScaleX);

                currButton.moveTo(leftStart.get() / oneSidedScaleX, 0f);
                currButton.getTransforms().setDefaultScale(defaultScaleX / oneSidedScaleX, 0.8f);

                leftStart.updateAndGet(v -> v + defaultScaleX);
            }
        });
    }

    public int getNumButtons() {
        return getChildren().toList().size();
    }

    public boolean shouldAdd(GameButton gameButton) {
        return true;
    }

    public int getNextButtonID() {
        int toReturn = buttonID;
        buttonID++;
        return toReturn;
    }

    public ButtonsMenu setButtonsSpacing(float buttonsSpacing) {
        this.buttonsSpacing = buttonsSpacing;
        return this;
    }

    public ButtonsMenu setButtonsScale(float buttonsScale) {
        this.buttonsScale = buttonsScale;
        return this;
    }

    @Override
    protected void showHelper() {
        moveTo(screenBottomLocation);
        getOpeningAnimator().startFrom(1.0f, false);
        getOpeningAnimator().resume();
    }

    @Override
    protected void hideHelper() {
        getOpeningAnimator().startFrom(0f, true);
        getOpeningAnimator().resume();
    }
}
