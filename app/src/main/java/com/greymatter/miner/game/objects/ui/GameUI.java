package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.PolygonRbTRViaMat;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameUI extends GameObjectWGL implements OnClickListener, OnTouchListener {
    private FloatValueAnimator onTouchResizeAnimator;
    public GameUI(String id, Drawable drawable) {
        super(id, drawable);
        this.initialize();
    }

    private void initialize() {
        this.setRectangularRB();
        this.getRigidBody().isStaticObject(true);
        this.setAnimator(new FloatValueAnimator().setBounds(0f,1f).withFPS(60).setPerFrameIncrement(0.1f));

        onTouchResizeAnimator = new FloatValueAnimator().setBounds(0.9f,1f).withFPS(60).setPerFrameIncrement(0.05f);
        onTouchResizeAnimator.toAndFro(true).setSingleCycle(true).setToAnimateObject(this);
        onTouchResizeAnimator.pause();
        onTouchResizeAnimator.setOnAnimationFrameHandler((object, animator) -> {
            float updateFloat = animator.getUpdatedFloat();
            Vector3f defaultScale = getTransforms().getDefaultScale();
            object.scaleTo(updateFloat * defaultScale.x, updateFloat * defaultScale.y);
        });

        this.setOnTouchListener(this);
        this.setOnClickListener(this);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        onTouchResizeAnimator.update();
    }

    @Override
    public boolean onClick(IGameObject object) {
        return false;
    }

    @Override
    public boolean onLongClick(IGameObject object) {
        return false;
    }

    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        onTouchResizeAnimator.startFrom(1.0f,false);
        onTouchResizeAnimator.resume();
        return true;
    }

    @Override
    public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
        return false;
    }

    @Override
    public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
        Vector3f defaultScale = getTransforms().getDefaultScale();
        onTouchResizeAnimator.startFrom(onTouchResizeAnimator.getUpdatedFloat(),true);
        onTouchResizeAnimator.resume();
        return true;
    }

    public GameUI setDefaultScale(Vector3f scale) {
        this.getTransforms().setDefaultScale(scale);
        return this;
    }

    public Vector3f getDefaultScale() {
        return getTransforms().getDefaultScale();
    }
}
