package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameBuilding extends GameObject {
    private ArrayList<GameLight> buildingLights;
    public GameBuilding(ObjId id, Drawable drawable) {
        super(id, drawable);
        buildingLights = new ArrayList<>();
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        buildingLights.forEach(light -> {
            light.getDrawable().getTransforms().applyTransformationsForced();
        });
    }

    public void addLight(GameLight light) {
        light.getTransforms().setParent(this.getTransforms())
                            .copyTranslationFromParent(true)
                            .copyRotationFromParent(true)
                            .copyScaleFromParent(false);
        buildingLights.add(light);
    }

    public ArrayList<GameLight> getAllLights() {
        return buildingLights;
    }

    public boolean hasLights() {
        return buildingLights != null && buildingLights.size() > 0;
    }

    //object movement
    @Override
    public GameBuilding moveBy(Vector2f moveTo) {
        super.moveBy(moveTo);
        //buildingLights.forEach(gameLight -> gameLight.moveBy(moveTo));
        return this;
    }

    @Override
    public GameBuilding moveBy(Vector3f moveTo) {
        super.moveBy(moveTo);
        //buildingLights.forEach(gameLight -> gameLight.moveBy(moveTo));
        return this;
    }

    @Override
    public GameBuilding moveBy(float x, float y) {
        super.moveBy(x, y);
        //buildingLights.forEach(gameLight -> gameLight.moveBy(x,y));
        return this;
    }

    @Override
    public GameBuilding moveTo(Vector2f moveTo) {
        super.moveTo(moveTo);
        //buildingLights.forEach(gameLight -> gameLight.moveTo(moveTo));
        return this;
    }

    @Override
    public GameBuilding moveTo(Vector3f moveTo) {
        super.moveTo(moveTo);
        //buildingLights.forEach(gameLight -> gameLight.moveTo(moveTo));
        return this;
    }

    @Override
    public GameBuilding moveTo(float x, float y) {
        super.moveTo(x, y);
        //buildingLights.forEach(gameLight -> gameLight.moveTo(x,y));
        return this;
    }
}
