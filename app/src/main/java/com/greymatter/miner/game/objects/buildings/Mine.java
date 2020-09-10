package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class Mine extends GameBuilding {
    private MinerParking minerParking;
    public Mine(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public Mine(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {
        minerParking = new MinerParking(DrawableDef.create(DrawableDef.COAL_BLOCK_I));
        minerParking.moveTo(getLocation().x, getLocation().y, ZHelper.FRONT_MID);
        addChild(minerParking.getId(), minerParking);
        setOnClickListener(new OnClickListener() {
            @Override
            public boolean onClick(IGameObject object) {
                minerParking.open();
                return true;
            }
        });
    }

    @Override
    public void onSnapAnimationComplete() {

    }

    @Override
    public void onSnapAnimationFrame() {
        minerParking.moveTo(getLocation().x, getLocation().y);
        minerParking.setClosedPoint(getLocation().x, getLocation().y);
        minerParking.setOpenPoint(getLocation().x, getLocation().y - 6f);
    }
}
