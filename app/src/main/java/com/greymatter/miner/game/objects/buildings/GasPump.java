package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.GamePipeline;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;
import com.greymatter.miner.opengl.objects.renderers.InstancedRenderer;
import javax.vecmath.Vector3f;

public class GasPump extends GameBuilding {
    private GamePipeline pipe;
    public GasPump(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public GasPump(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        InstanceGroup square = new InstanceGroup("INS");
        square.setRenderer(new InstancedRenderer()).build();
        square.setMaterial(MaterialContainer.get(MaterialDef.GROUND_MATERIAL));

        pipe = new GamePipeline(square);
        pipe.getJointIndicator().getInstance(0).moveTo(getLocation().x, getLocation().y);
        this.addChild("INS", pipe);
    }

    @Override
    public void onSnapAnimationComplete() {
        Vector3f directionToCenter = VectorHelper.sub(GameObjectsContainer.get(GameManager.getCurrentPlanet()).getLocation(), getLocation());
        directionToCenter.normalize();
        pipe.getJointIndicator().getInstance(0).moveTo(getLocation().x + directionToCenter.x*0.6f, getLocation().y + directionToCenter.y*0.6f);
    }

    @Override
    public void onSnapAnimationFrame() {
        Vector3f directionToCenter = VectorHelper.sub(GameObjectsContainer.get(GameManager.getCurrentPlanet()).getLocation(), getLocation());
        directionToCenter.normalize();
        pipe.getJointIndicator().getInstance(0).moveTo(getLocation().x + directionToCenter.x*0.6f, getLocation().y + directionToCenter.y*0.6f);
    }
}
