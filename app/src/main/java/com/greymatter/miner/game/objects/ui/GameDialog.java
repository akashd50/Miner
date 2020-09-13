package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public class GameDialog extends GameNotification {
    public GameDialog(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public GameDialog(String id, Drawable drawable) {
        super(id, drawable);
    }

    public GameDialog() {
        super("OBJECT_DIALOG", DrawableDef.create(DrawableDef.OBJECT_DIALOG));
        initialize();
    }

    private void initialize() {
        setButtonI(new GameButton(DrawableDef.create(DrawableDef.DIALOG_BUTTON_I)));
        setButtonII(new GameButton(DrawableDef.create(DrawableDef.DIALOG_BUTTON_II)));
        setDefaultScale(new Vector3f(2f,1.5f, 1f));
        addTag(Tag.DIALOG);
    }

    public GameDialog setButtonI(GameButton button) {
        addChild("DIALOG_BUTTON_I", button);
        button.getTransforms()
                    .copyTranslationFromParent(true)
                    .copyRotationFromParent(true)
                    .copyScaleFromParent(true);
        button.scaleTo(0.4f,0.2f).moveTo(-0.5f, -0.4f, 1f);
        button.setDefaultScale(new Vector3f(0.4f, 0.2f, 1f));
        return this;
    }

    public GameDialog setButtonII(GameButton button) {
        addChild("DIALOG_BUTTON_II", button);
        button.getTransforms()
                .copyTranslationFromParent(true)
                .copyRotationFromParent(true)
                .copyScaleFromParent(true);
        button.scaleTo(0.4f,0.2f).moveTo(0.5f, -0.4f, 1f);
        button.setDefaultScale(new Vector3f(0.4f, 0.2f, 1f));
        return this;
    }

    public GameDialog setButtonIClickListener(OnClickListener listener) {
        getChild("DIALOG_BUTTON_I").setOnClickListener(listener);
        return this;
    }

    public GameDialog setButtonIIClickListener(OnClickListener listener) {
        getChild("DIALOG_BUTTON_II").setOnClickListener(listener);
        return this;
    }
}
