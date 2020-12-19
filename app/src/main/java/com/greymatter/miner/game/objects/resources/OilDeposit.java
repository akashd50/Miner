package com.greymatter.miner.game.objects.resources;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

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

    @Override
    public void onResourceAmountChanged() {
        Transforms innerTrans = innerOilDeposit.getTransforms();
        Vector3f defaultTranslation = innerTrans.getDefaultTranslation();
        Vector3f defaultScale = innerTrans.getDefaultScale();

        float amountRatio = (getResourceAmount().getRemaining()/getResourceAmount().getTotal()) * defaultScale.y;
        this.innerOilDeposit.scaleTo(defaultScale.x, amountRatio);
        float translationFix = defaultTranslation.y - (defaultScale.y - amountRatio);
        this.innerOilDeposit.moveTo(defaultTranslation.x, translationFix, defaultTranslation.z);
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
