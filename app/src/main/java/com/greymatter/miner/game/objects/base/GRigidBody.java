package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.physics.objects.rb.RigidBody;
import javax.vecmath.Vector2f;

public abstract class GRigidBody extends GTransformable {
    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;
    private RigidBody rigidBody;
    private Vector2f touchDownOffset;

    public GRigidBody(String id) {
        super(id);
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
        getTransforms().onTransformsChanged();
        return this;
    }

    public Vector2f getTouchDownOffset() {
        return touchDownOffset;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public boolean onTouchDownEvent(Vector2f pointer) {
        for(IGameObject child : getChildren().toList()) {
            if(child.shouldDraw()) {
                boolean res = child.onTouchDownEvent(pointer);
                if (res) return res;
            }
        }

        if(onTouchListener!=null) {
            if (isClicked(pointer)) {
                touchDownOffset = new Vector2f(pointer.x - getLocation().x, pointer.y - getLocation().y);
                return onTouchListener.onTouchDown(this, pointer);
            }
        }
        return false;
    }

    public boolean onTouchMoveEvent(Vector2f pointer) {
        if(onTouchListener!=null) {
            if (isClicked(pointer)) {
                return onTouchListener.onTouchMove(this, pointer);
            }
        }
        return false;
    }

    public boolean onTouchUpEvent(Vector2f pointer) {
        for(IGameObject child : getChildren().toList()) {
            if(child.shouldDraw()) {
                boolean res = child.onTouchUpEvent(pointer);
                if (res) return res;
            }
        }

        boolean isClicked = isClicked(pointer);
        if(isClicked) {
            boolean handled = false;
            if(onTouchListener!=null) {
                handled = onTouchListener.onTouchUp(this, pointer);
            }

            if(onClickListener != null) {
                if(!AppServices.getTouchHelper().isTouchPoint1Drag()) {
                    handled = onClickListener.onClick(this);
                }
            }
            return handled;
        }
        return false;
    }

    private boolean isClicked(Vector2f pointer) {
        return shouldDraw() && rigidBody != null && rigidBody.isClicked(pointer);
    }
}
