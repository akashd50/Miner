package com.greymatter.miner.game.objects;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.PolygonRbTRViaMat;

import javax.vecmath.Vector3f;

public class GameDialog extends GameObject {
    private Vector3f defaultScale;
    public GameDialog(Drawable drawable) {
        super(drawable.getId(), drawable);
        this.setRigidBody(new PolygonRbTRViaMat(getId(), this.getDrawable().getOrderedOuterMesh())).setPolygonTC();
        getRigidBody().isStaticObject(true);
    }

    public GameDialog(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onFrameUpdate() {

    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
    }

    public GameDialog setButtonI(GameButton button) {
        addChild(ObjId.NOT_BUTTON_I, button);
        button.getTransforms()
                    .copyTranslationFromParent(true)
                    .copyRotationFromParent(true)
                    .copyScaleFromParent(true);
        button.scaleTo(0.4f,0.2f).moveTo(-0.5f, -0.7f, 2f);
        return this;
    }

    public GameDialog setButtonIClickListener(OnClickListener listener) {
        getChild(ObjId.NOT_BUTTON_I).setOnClickListener(listener);
        return this;
    }

    public GameDialog setButtonIIClickListener(OnClickListener listener) {
        getChild(ObjId.NOT_BUTTON_II).setOnClickListener(listener);
        return this;
    }

    public GameDialog setButtonII(GameButton button) {
        addChild(ObjId.NOT_BUTTON_II, button);
        button.getTransforms()
                .copyTranslationFromParent(true)
                .copyRotationFromParent(true)
                .copyScaleFromParent(true);
        button.scaleTo(0.4f,0.2f).moveTo(0.5f, -0.7f, 2f);
        return this;
    }

    public GameDialog setDefaultScale(Vector3f scale) {
        this.defaultScale.set(scale);
        return this;
    }
}
