package com.greymatter.miner.game.objects;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.PolygonRbTRViaMat;

public class GameButton extends GameObject {

    public GameButton(Drawable drawable) {
        super(drawable.getId(), drawable);
        this.setRigidBody(new PolygonRbTRViaMat(ObjId.NOT_BUTTON_I, this.getDrawable().getOrderedOuterMesh())).setPolygonTC();
        getRigidBody().isStaticObject(true);
    }

    public GameButton(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
    }
}
