package com.greymatter.miner.game.objects;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.ArrayList;

public class GameObjectWGL extends GameObject {
    private ArrayList<GameLight> quickAccessGameLights;
    public GameObjectWGL(ObjId id, Drawable drawable) {
        super(id, drawable);
        quickAccessGameLights = new ArrayList<>();
    }

    public void addLight(GameLight light) {
        light.getTransforms().copyTranslationFromParent(true)
                .copyRotationFromParent(true)
                .copyScaleFromParent(true);
        addChild(light.getId(), light);
        quickAccessGameLights.add(light);
    }

    public ArrayList<GameLight> getAllLights() {
        return quickAccessGameLights;
    }
}
