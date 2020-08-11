package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.enums.definitions.DrawableDef;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class GameSignal extends GameNotification {
    public GameSignal(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    public GameSignal() {
        super(ObjId.OBJECT_SIGNAL, DrawableDef.create(ObjId.OBJECT_SIGNAL));
        setDefaultScale(new Vector3f(0.5f,0.8f, 1f));
        addTag(Tag.SIGNAL);

        new GameLight(new Obj(ObjId.OBJECT_SIGNAL_LIGHT))
                .setRadius(1f)
                .setColor(1f,0f,0f,1f)
                .setInnerCutoff(0.02f).setOuterCutoff(0.8f)
                .attachTo(this)
                .moveTo(new Vector2f(0f,0.4f))
                .setAnimator(new FloatValueAnimator().setPerFrameIncrement(0.05f).toAndFro(true).withFPS(60))
                .setOnAnimationFrameHandler((object, animator) -> {
                    object.asGameLight().setIntensity(animator.update().getUpdatedFloat());
                });
    }
}
