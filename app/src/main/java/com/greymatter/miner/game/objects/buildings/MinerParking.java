package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.Miner;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;

public class MinerParking extends GameBuilding {
    private Vector2f closedPoint, openPoint;
    private Miner miner;
    public MinerParking(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public MinerParking(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {
        openPoint = new Vector2f();
        closedPoint = new Vector2f();

        setAnimator(new FloatValueAnimator().setPerFrameIncrement(0.1f).setSingleCycle(true).withFPS(30).toAndFro(true));
        getAnimator().pause();
        getAnimator().setOnAnimationFrameHandler(new OnAnimationFrameHandler() {
            @Override
            public void onAnimationFrame(GameObject object, ValueAnimator animator) {
                float x = (closedPoint.x * (1f - animator.getUpdatedFloat()) + openPoint.x * animator.getUpdatedFloat());
                float y = (closedPoint.y * (1f - animator.getUpdatedFloat()) + openPoint.y * animator.getUpdatedFloat());
                object.moveTo(x,y);
            }
        });

        miner = new Miner(DrawableDef.create(DrawableDef.GAME_PAD_FRONT));
        miner.moveTo(0f,0f,1f);
        miner.scaleTo(0.5f,0.5f);
        miner.translationFromParent(true);
        addChild("MINER", miner);
    }

    public MinerParking setClosedPoint(float x, float y) {
        this.closedPoint.set(x,y);
        return this;
    }

    public MinerParking setOpenPoint(float x, float y) {
        this.openPoint.set(x,y);
        return this;
    }

    public void open() {
        getAnimator().asFloatValueAnimator().startFrom(0,true);
        getAnimator().resume();
    }

    public void close() {
        getAnimator().asFloatValueAnimator().startFrom(1.0f,false);
        getAnimator().resume();
    }

    public Miner getMiner() {
        return miner;
    }
}
