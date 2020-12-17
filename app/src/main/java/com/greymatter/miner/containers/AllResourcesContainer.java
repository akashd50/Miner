package com.greymatter.miner.containers;

import com.greymatter.miner.game.objects.resources.ResourceBlock;

public class AllResourcesContainer extends AllGameObjectsContainer {
    public AllResourcesContainer() {
        super();
    }

    public ResourceBlock remove(String id) {
        return (ResourceBlock)super.remove(id);
    }

    public ResourceBlock get(String id) {
        return (ResourceBlock)super.get(id);
    }
}
