package com.greymatter.miner.game.objects.resources;

import com.greymatter.miner.game.GameInstance;
import com.greymatter.miner.game.objects.GameInstanceGroup;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class OilDepositGroup extends ResourceBlock {
    private static final String OUTER_GROUP = "outer_group";
    private static final String INNER_GROUP = "inner_group";
    private static final String OIL_DEPOSIT = "oil_deposit";

    private GameInstanceGroup outerOilDeposit, innerOilDeposit;
    private ArrayList<OilDeposit> oilDeposits;
    public OilDepositGroup(Drawable drawable) {
        super(drawable);
        initialize();
    }

    public OilDepositGroup(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public OilDepositGroup(String id) {
        super(id, DrawableDef.create(DrawableDef.OIL_RESOURCE_OUTER_INSTANCE_GROUP));
        initialize();
    }

    private void initialize() {
        this.oilDeposits = new ArrayList<>();
        this.shouldDraw(false);
        this.shouldCheckClicks(false);
        this.moveTo(0f, 0f, 0f);
        outerOilDeposit = new GameInstanceGroup(DrawableDef.create(DrawableDef.OIL_RESOURCE_OUTER_INSTANCE_GROUP));
        innerOilDeposit = new GameInstanceGroup(DrawableDef.create(DrawableDef.OIL_RESOURCE_INNER_INSTANCE_GROUP));
        outerOilDeposit.moveTo(0f,0f, ZHelper.FRONT);
        innerOilDeposit.moveTo(0f,0f, ZHelper.FRONT - 2f);

        this.addChild(INNER_GROUP, innerOilDeposit);
        this.addChild(OUTER_GROUP, outerOilDeposit);
    }

    public void addInstance(float x, float y, float amount) {
        int id = outerOilDeposit.getTotalInstances();
        GameInstance newOuterInstance = outerOilDeposit.createAndAddInstance();
        GameInstance newInnerInstance = innerOilDeposit.createAndAddInstance();
        newOuterInstance.getTransforms().setDefaultTranslation(x, y, outerOilDeposit.getLocalLocation().z);
        newOuterInstance.getTransforms().setDefaultScale(1f, 1f);
        newInnerInstance.getTransforms().setDefaultTranslation(x, y, innerOilDeposit.getLocalLocation().z);
        newInnerInstance.getTransforms().setDefaultScale(0.6f, 0.7f);

        outerOilDeposit.setRectangularRB();

        OilDeposit newDeposit = new OilDeposit(OIL_DEPOSIT + id)
                .setOuterOilDeposit(newOuterInstance)
                .setInnerOilDeposit(newInnerInstance);
        newDeposit.getResourceAmount().setTotal(amount).setRemaining(amount);
        oilDeposits.add(newDeposit);
    }

    public OilDeposit getOverlappingDeposit(Vector2f pointer) {
        for (OilDeposit deposit : oilDeposits) {
            if (deposit.getOuterOilDeposit().getRigidBody().isClicked(pointer)) {
                return deposit;
            }
        }
        return null;
    }


}
