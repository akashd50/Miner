package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.PolygonRbTRViaMat;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameUI extends GameObjectWGL {
    private Vector3f defaultScale;
    private FloatValueAnimator onTouchAnimator;
    private boolean hasTouchDown;
    public GameUI(ObjId id, Drawable drawable) {
        super(id, drawable);
        this.initialize();
    }

    private void initialize() {
        defaultScale = new Vector3f();
        this.setRB(new PolygonRbTRViaMat(getId(), this.getDrawable().getOrderedOuterMesh())).setPolygonTC();
        this.getRigidBody().isStaticObject(true);
        this.setAnimator(new FloatValueAnimator().setBounds(0f,1f).withFPS(60).setPerFrameIncrement(0.1f));

        onTouchAnimator = new FloatValueAnimator().setBounds(0.9f,1f).withFPS(60).setPerFrameIncrement(0.05f);
        onTouchAnimator.toAndFro(true).setSingleCycle(true).setToAnimateObject(this);
        onTouchAnimator.pause();
        onTouchAnimator.setOnAnimationFrameHandler((object, animator) -> {
            float updateFloat = animator.getUpdatedFloat();
            object.scaleTo(updateFloat * defaultScale.x, updateFloat * defaultScale.y);
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public void onTouchDown(GameObject gameObject, Vector2f pointer) {
                onTouchAnimator.startFrom(1.0f,false);
                onTouchAnimator.resume();
            }

            @Override
            public void onTouchMove(GameObject gameObject, Vector2f pointer) {

            }

            @Override
            public void onTouchUp(GameObject gameObject, Vector2f pointer) {
                onTouchAnimator.startFrom(gameObject.getTransforms().getScale().x/defaultScale.x,true);
                onTouchAnimator.resume();
            }
        });
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        onTouchAnimator.update();
    }

    public GameUI setDefaultScale(Vector3f scale) {
        this.defaultScale.set(scale);
        return this;
    }

    public Vector3f getDefaultScale() {
        return defaultScale;
    }
}
