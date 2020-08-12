package com.greymatter.miner.game.objects;

import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.shader.ShaderHelper;
import java.util.ArrayList;

public class GameObjectWGL extends GameObject {
    private ArrayList<GameLight> quickAccessGameLights;
    public GameObjectWGL(ObjId id, Drawable drawable) {
        super(id, drawable);
        quickAccessGameLights = new ArrayList<>();
    }

    @Override
    public void setShaderProperties(Camera camera) {
        super.setShaderProperties(camera);
        ShaderHelper.setLightProperties(getDrawable().getShader(), quickAccessGameLights);
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
