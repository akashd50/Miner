package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.PolygonRbTRViaMat;

public class GameUI extends GameObject {
    public GameUI(ObjId id, Drawable drawable) {
        super(id, drawable);
        this.setRB(new PolygonRbTRViaMat(getId(), this.getDrawable().getOrderedOuterMesh())).setPolygonTC();
        this.getRigidBody().isStaticObject(true);
        this.setAnimator(new FloatValueAnimator().setBounds(0f,1f).withFPS(60).setPerFrameIncrement(0.1f));
    }
}
