package com.greymatter.miner.game.objects.resources;

public class ResourceAmount {
    private float total, remaining;
    private ResourceBlock linkedResource;
    public ResourceAmount() {}

    public float getRemaining() {
        return remaining;
    }

    public float getTotal() {
        return total;
    }

    public ResourceAmount setRemaining(float remaining) {
        this.remaining = remaining;
        this.onAmountChanged();
        return this;
    }

    public ResourceAmount setTotal(float total) {
        this.total = total;
        return this;
    }

    public ResourceAmount updateRemaining(float adjustment) {
        this.remaining += adjustment;
        this.onAmountChanged();
        return this;
    }

    public ResourceBlock getLinkedResource() {
        return linkedResource;
    }

    public ResourceAmount setLinkedResource(ResourceBlock linkedResource) {
        this.linkedResource = linkedResource;
        return this;
    }

    public void onAmountChanged() {
        linkedResource.onResourceAmountChanged();
    }
}
