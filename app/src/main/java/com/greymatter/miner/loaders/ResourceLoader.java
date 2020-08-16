package com.greymatter.miner.loaders;

import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.AllResourcesContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.game.objects.resources.CoalBlock;

public class ResourceLoader extends Loader {
    public void load() {
        float planetRadius = GameObjectsContainer.get(ObjId.PLANET).getTransforms().getScale().y;
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

        CoalBlock block = new CoalBlock(DrawableDef.create(ObjId.COAL_BLOCK_I));
        block.addTag(Tag.RESOURCE_OBJECT).scaleTo(0.2f, 0.2f).moveTo(10f, -4f, ZHelper.OVER_FRONT);
        AllResourcesContainer.add(ObjId.COAL_BLOCK_I.toString(),block);

        //finishSetup();
    }

    public static void finishSetup() {
        //ActiveResourcesContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I).asResourceBlock());
        //ToDrawContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I));
    }
}
