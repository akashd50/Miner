package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
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
    private FloatValueAnimator onTouchResizeAnimator;
    public GameUI(ObjId id, Drawable drawable) {
        super(id, drawable);
        this.initialize();
    }

    private void initialize() {
        defaultScale = new Vector3f();
        this.setRB(new PolygonRbTRViaMat(getId(), this.getDrawable().getOrderedOuterMesh())).setPolygonTC();
        this.getRigidBody().isStaticObject(true);
        this.setAnimator(new FloatValueAnimator().setBounds(0f,1f).withFPS(60).setPerFrameIncrement(0.1f));

        onTouchResizeAnimator = new FloatValueAnimator().setBounds(0.9f,1f).withFPS(60).setPerFrameIncrement(0.05f);
        onTouchResizeAnimator.toAndFro(true).setSingleCycle(true).setToAnimateObject(this);
        onTouchResizeAnimator.pause();
        onTouchResizeAnimator.setOnAnimationFrameHandler((object, animator) -> {
            float updateFloat = animator.getUpdatedFloat();
            object.scaleTo(updateFloat * defaultScale.x, updateFloat * defaultScale.y);
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouchDown(GameObject gameObject, Vector2f pointer) {
                onTouchResizeAnimator.startFrom(1.0f,false);
                onTouchResizeAnimator.resume();
                return true;
            }

            @Override
            public boolean onTouchMove(GameObject gameObject, Vector2f pointer) {
                return false;
            }

            @Override
            public boolean onTouchUp(GameObject gameObject, Vector2f pointer) {
                onTouchResizeAnimator.startFrom(gameObject.getTransforms().getScale().x/defaultScale.x,true);
                onTouchResizeAnimator.resume();
                return true;
            }
        });
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        onTouchResizeAnimator.update();
    }

    public GameUI setDefaultScale(Vector3f scale) {
        this.defaultScale.set(scale);
        return this;
    }

    public Vector3f getDefaultScale() {
        return defaultScale;
    }
}
