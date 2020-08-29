package com.greymatter.miner.physics.objects.rb;

import javax.vecmath.Vector2f;

public class GeneralRB extends RigidBody {
    public GeneralRB(String id) {
        super(id);
    }

    @Override
    public void updateParamsOverride() {

    }

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        return false;
    }
}
