package com.greymatter.miner.opengl.objects.drawables;

public class Instance extends Drawable {
    private InstanceGroup parentGroup;
    public Instance(String id) {
        super(id);
    }

    public Instance setParentGroup(InstanceGroup parentGroup) {
        this.parentGroup = parentGroup;
        this.setShape(parentGroup.getShape());
        this.setMaterial(parentGroup.getMaterial());
        return this;
    }

    @Override
    public void onTransformsChanged() {
        parentGroup.onTransformsChanged();
    }
}
