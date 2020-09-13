package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.Tag;
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
        this.addTag(Tag.PLACABLE_GAME_BUILDING).scaleTo(6f,3f).moveBy(30f,2f,ZHelper.FRONT);

        minerParking = new MinerParking(DrawableDef.create(DrawableDef.COAL_BLOCK_I));
        minerParking.setRectangularRB();
        minerParking.moveTo(0f, 0f, -2f);
        minerParking.translationFromParent(true);
        minerParking.setClosedPoint(0f, 0f).setOpenPoint(0f, -6f);

        this.addChild(minerParking.getId(), minerParking);
    }

    @Override
    public boolean onClick(IGameObject object) {
        minerParking.open();
        return true;
    }
}
