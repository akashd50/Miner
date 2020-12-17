package com.greymatter.miner.loaders;

import com.greymatter.miner.containers.ActiveResourcesGameObjectContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.resources.OilDeposit;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.game.objects.resources.CoalBlock;

import static com.greymatter.miner.game.GameConstants.*;

public class ResourceLoader extends Loader {
    public void load() {
        ActiveResourcesGameObjectContainer activeResourcesContainer = ContainerManager.getActiveResourceContainer();
        float planetRadius = GameManager.getCurrentPlanet().getTransforms().getScale().y;
        String res = "res";
        int i=0;
//        while(i<360) {
//            float x = planetRadius * (float)Math.cos(Math.toRadians(i));
//            float y = planetRadius * (float)Math.sin(Math.toRadians(i));
//            i += Math.random()*10;
//
//            CoalBlock block = new CoalBlock(DrawableDef.create(ObjId.COAL_BLOCK_I));
//            block.addTag(Tag.RESOURCE_OBJECT).scaleTo(0.2f, 0.2f).moveTo(x, y, ZHelper.OVER_FRONT);
//            ActiveResourcesContainer.add(res+i, block);
//        }

        CoalBlock block = new CoalBlock(DrawableDef.create(DrawableDef.COAL_BLOCK_I));
        block.addTag(Tag.RESOURCE_OBJECT).scaleTo(1f, 1f).moveTo(20f, -4f, ZHelper.OVER_FRONT);

        OilDeposit deposit = new OilDeposit(OIL_DEPOSIT_1);
        deposit.moveTo(-10f, -8f);

        ContainerManager.getAllResourcesContainer().add(DrawableDef.COAL_BLOCK_I.name(), block);
        ContainerManager.getAllResourcesContainer().add(OIL_DEPOSIT_1, deposit);

        activeResourcesContainer.add(DrawableDef.COAL_BLOCK_I.name(),
                ContainerManager.getAllResourcesContainer().get(DrawableDef.COAL_BLOCK_I.name()));
        activeResourcesContainer.add(OIL_DEPOSIT_1, deposit);
        //finishSetup();
    }

    public static void finishSetup() {
        //ActiveResourcesContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I).asResourceBlock());
        //ToDrawContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I));
    }

    @Override
    public void onPostSurfaceInitializationHelper() {

    }
}
