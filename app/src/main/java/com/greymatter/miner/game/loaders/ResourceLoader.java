package com.greymatter.miner.game.loaders;

import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.enums.definitions.DrawableDef;
import com.greymatter.miner.game.objects.resources.CoalBlock;

public class ResourceLoader extends Loader {
    public void load() {
        GameObjectsContainer.add(new CoalBlock(DrawableDef.create(ObjId.COAL_BLOCK_I))
                .addTag(Tag.RESOURCE_OBJECT)
                .scaleTo(0.2f, 0.2f).moveTo(10f, -4f, -2f));

        finishSetup();
    }

    public static void finishSetup() {
        ActiveResourcesContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I).asResourceBlock());
    }
}
