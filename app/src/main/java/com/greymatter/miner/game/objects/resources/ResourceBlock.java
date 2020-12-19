package com.greymatter.miner.game.objects.resources;

import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public abstract class ResourceBlock extends GameObject {
    private ResourceAmount resourceAmount;
    public ResourceBlock(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public ResourceBlock(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        resourceAmount = new ResourceAmount();
        resourceAmount.setLinkedResource(this);
    }

    public ResourceAmount getResourceAmount() {
        return resourceAmount;
    }

    public ResourceBlock setResourceAmount(ResourceAmount resourceAmount) {
        this.resourceAmount = resourceAmount;
        this.resourceAmount.setLinkedResource(this);
        return this;
    }

    public void onResourceAmountChanged() {

    }
}
