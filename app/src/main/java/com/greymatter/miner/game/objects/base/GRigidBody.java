package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.physics.objects.rb.RigidBody;
import javax.vecmath.Vector2f;

public abstract class GRigidBody extends GTransformable
        implements OnTouchListener, OnClickListener {

    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;
    private RigidBody rigidBody;
    private Vector2f touchDownOffset;

    public GRigidBody(String id) {
        super(id);
        this.setOnClickListener(this);
        this.setOnTouchListener(this);
    }

    public IGameObject setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
        return this;
    }

    public IGameObject setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public IGameObject setRB(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
        this.rigidBody.setTransforms(getTransforms());
        this.getTransforms().onTransformsChanged();
        return this;
    }

    public IGameObject setTouchDownOffset(Vector2f offset) {
        this.touchDownOffset = offset;
        return this;
    }

    public OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public Vector2f getTouchDownOffset() {
        return touchDownOffset;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public boolean isClicked(Vector2f pointer) {
        return shouldCheckClicks() && rigidBody != null && rigidBody.isClicked(pointer);
    }

    @Override
    public void onTransformsChanged() {
        rigidBody.onTransformsChanged();
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
        return false;
    }

    @Override
    public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
        return false;
    }

    @Override
    public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
        return false;
    }
}
