package com.greymatter.miner.game.objects.resources;

import com.greymatter.miner.game.GameInstance;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class OilDeposit extends ResourceBlock {
    private static final String OUTER_DEPOSIT = "outer_oil_deposit";
    private static final String INNER_DEPOSIT = "inner_oil_deposit";
    private IGameObject outerOilDeposit, innerOilDeposit;
    public OilDeposit(Drawable drawable) {
        super(drawable);
        initialize();
    }

    public OilDeposit(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public OilDeposit(String id) {
        super(id, DrawableDef.create(DrawableDef.OIL_RESOURCE_OUTER_INSTANCE_GROUP));
        this.shouldDraw(false);
        this.shouldCheckClicks(false);
        initialize();
    }

    private void initialize() {

    }

    public OilDeposit setInnerOilDeposit(IGameObject innerOilDeposit) {
        this.innerOilDeposit = innerOilDeposit;
        this.addChild(INNER_DEPOSIT, innerOilDeposit);
        return this;
    }

    public OilDeposit setOuterOilDeposit(IGameObject outerOilDeposit) {
        this.outerOilDeposit = outerOilDeposit;
        this.addChild(OUTER_DEPOSIT, outerOilDeposit);
        return this;
    }

    public IGameObject getInnerOilDeposit() {
        return innerOilDeposit;
    }

    public IGameObject getOuterOilDeposit() {
        return outerOilDeposit;
    }
}
