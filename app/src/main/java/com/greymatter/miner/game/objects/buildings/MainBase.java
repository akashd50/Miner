package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import javax.vecmath.Vector2f;
import static com.greymatter.miner.game.GameConstants.MAIN_BASE_LIGHT_1;

public class MainBase extends GameBuilding {
    public MainBase(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }
    public MainBase(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public MainBase(String id) {
        super(id, DrawableDef.create(DrawableDef.MAIN_BASE));
        initialize();
    }

    private void initialize() {
        addTag(Tag.STATIC);
        addTag(Tag.PLACABLE_GAME_BUILDING);
        GameLight gameLight = new GameLight(new Obj(MAIN_BASE_LIGHT_1));
        gameLight.setRadius(1f).setColor(1f,0f,0f,1f)
                .setInnerCutoff(0.02f).setOuterCutoff(0.8f)
                .attachTo(this).moveTo(new Vector2f(0.12f,-0.06f));

        FloatValueAnimator lightValueAnimator = new FloatValueAnimator();
        lightValueAnimator.setPerFrameIncrement(0.05f).toAndFro(true).withFPS(60);
        lightValueAnimator.setOnAnimationFrameHandler((object, animator) -> {
            object.asGameLight().setIntensity(animator.getUpdatedFloat());
        });
        gameLight.setAnimator(lightValueAnimator);
    }
}
