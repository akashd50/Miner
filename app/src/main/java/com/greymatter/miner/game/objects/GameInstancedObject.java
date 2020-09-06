package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Instance;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;

public class GameInstancedObject extends GameObject {
    public GameInstancedObject(String id, InstanceGroup drawable) {
        super(id, drawable);
        initialize();
    }

    public GameInstancedObject(InstanceGroup drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {
        this.shouldCheckClicks(false);
    }

    public GameObject addInstance() {
        InstanceGroup instanced = getDrawable().asInstanceGroup();
        GenericObject genericObject = new GenericObject(new Instance("INSTANCE_"+instanced.getTotalInstances())
                                                        .setParentGroup(instanced));

        genericObject.shouldDraw(false);
        genericObject.shouldCheckClicks(true);

        instanced.addInstance(genericObject.getDrawable().asInstance());

        this.addChild(genericObject.getId(), genericObject);
        return genericObject;
    }
}
