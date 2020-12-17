package com.greymatter.miner.game.objects.resources;

import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class OilDeposit extends ResourceBlock {
    private static final String INNER_DEPOSIT = "inner_oil_deposit";
    private GenericObject innerOilDeposit;
    public OilDeposit(Drawable drawable) {
        super(drawable);
        initialize();
    }

    public OilDeposit(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public OilDeposit(String id) {
        super(id, DrawableDef.create(DrawableDef.OIL_DEPOSIT_OUTER));
        initialize();
    }

    private void initialize() {
        this.moveTo(0f, 0f, ZHelper.MID_BACK + 2f);

        innerOilDeposit = new GenericObject(INNER_DEPOSIT, DrawableDef.create(DrawableDef.OIL_DEPOSIT_INNER));
        innerOilDeposit.copyTranslationFromParent(true);
        innerOilDeposit.moveTo(0f, 0f, -1f);

        this.addChild(INNER_DEPOSIT, innerOilDeposit);
    }


}
