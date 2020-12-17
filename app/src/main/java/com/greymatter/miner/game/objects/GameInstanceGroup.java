package com.greymatter.miner.game.objects;

import com.greymatter.miner.game.GameInstance;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Instance;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;

public class GameInstanceGroup extends GameObject {
    public GameInstanceGroup(String id, InstanceGroup drawable) {
        super(id, drawable);
        initialize();
    }

    public GameInstanceGroup(Drawable drawable) {
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

    public GameInstance createAndAddInstance() {
        InstanceGroup instanceGroup = getDrawable().asInstanceGroup();
        GameInstance newInstance = new GameInstance(instanceGroup.createInstance());
        newInstance.shouldDraw(false);
        newInstance.shouldCheckClicks(true);

        this.addChild(newInstance.getId(), newInstance);
        return newInstance;
    }

    @Override
    public IGameObject addChild(String id, IGameObject object) {
        getDrawable().asInstanceGroup().addInstance(object.getDrawable().asInstance());
        return super.addChild(id, object);
    }

    public GameInstance getInstance(int index) {
        return (GameInstance)this.getChild("INSTANCE_"+index);
    }

    public int getTotalInstances() {
        return this.getDrawable().asInstanceGroup().getTotalInstances();
    }
}
