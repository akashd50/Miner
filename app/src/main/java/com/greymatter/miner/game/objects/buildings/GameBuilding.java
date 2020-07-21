package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.ArrayList;

public abstract class GameBuilding extends GameObject {
    public GameBuilding(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
    }

    public void addLight(GameLight light) {
        light.getTransforms().setParent(this.getTransforms())
                            .copyTranslationFromParent(true)
                            .copyRotationFromParent(true)
                            .copyScaleFromParent(false);
        addChild(light.getId(), light);
    }

    public ArrayList<GameLight> getAllLights() {
        ArrayList<GameLight> toReturn = new ArrayList<>();
        getChildren().forEach((id, object) -> {
            if(object instanceof GameLight) toReturn.add(object.asGameLight());
        });
        return toReturn;
    }
}
