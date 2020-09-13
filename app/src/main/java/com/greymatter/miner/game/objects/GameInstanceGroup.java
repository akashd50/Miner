package com.greymatter.miner.game.objects;

import com.greymatter.miner.game.GameInstance;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Instance;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;
import com.greymatter.miner.physics.objects.rb.CircularRB;

public class GameInstanceGroup extends GameObject {
    public GameInstanceGroup(String id, InstanceGroup drawable) {
        super(id, drawable);
        initialize();
    }

    public GameInstanceGroup(InstanceGroup drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {
        this.shouldCheckClicks(false);
    }

    @Override
    public void applyTransformations() {}

    @Override
    public void onDrawFrame(Camera camera) {
        if(shouldDraw()) {
            getDrawable().getRenderer().render(camera, this);
        }
    }

    public GameInstance addInstance() {
        InstanceGroup instanced = getDrawable().asInstanceGroup();
        GameInstance newInstance = new GameInstance(new Instance("INSTANCE_"+instanced.getTotalInstances())
                                                        .setParentGroup(instanced));
        newInstance.shouldDraw(false);
        newInstance.shouldCheckClicks(true);

        instanced.addInstance(newInstance.getDrawable().asInstance());

        this.addChild(newInstance.getId(), newInstance);
        return newInstance;
    }

    public GameInstance getInstance(int index) {
        return (GameInstance)this.getChild("INSTANCE_"+index);
    }

    public int getTotalInstances() {
        return this.getDrawable().asInstanceGroup().getTotalInstances();
    }
}
