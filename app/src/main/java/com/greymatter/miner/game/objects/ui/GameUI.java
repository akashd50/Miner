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

public abstract class GameUI extends GameObjectWGL {
    private FloatValueAnimator onTouchAnimator;
    private boolean hasTouchDown;
    public GameUI(ObjId id, Drawable drawable) {
        super(id, drawable);
        this.initialize();
    }

    private void initialize() {
        this.setRB(new PolygonRbTRViaMat(getId(), this.getDrawable().getOrderedOuterMesh())).setPolygonTC();
        this.getRigidBody().isStaticObject(true);
        this.setAnimator(new FloatValueAnimator().setBounds(0f,1f).withFPS(60).setPerFrameIncrement(0.1f));

        onTouchAnimator = new FloatValueAnimator().setBounds(0.8f,1f).withFPS(60).setPerFrameIncrement(0.1f);
        onTouchAnimator.toAndFro(true).setSingleCycle(true);
        onTouchAnimator.startFrom(1.0f, false);

        setOnAnimationFrameHandler(new OnAnimationFrameHandler() {
            @Override
            public void animate(GameObject object, ValueAnimator animator) {
                //object.scaleTo()
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public void onTouchDown(GameObject gameObject, Vector2f pointer) {
                hasTouchDown = true;
            }

            @Override
            public void onTouchMove(GameObject gameObject, Vector2f pointer) {

            }

            @Override
            public void onTouchUp(GameObject gameObject, Vector2f pointer) {
                hasTouchDown = false;
            }
        });
    }
}
