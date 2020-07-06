package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameBuilding extends GameObject {
    private ArrayList<GameLight> buildingLights;
    public GameBuilding(String id, Drawable drawable) {
        super(id, drawable);
        buildingLights = new ArrayList<>();
    }

    public void addLight(GameLight light) {
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
        buildingLights.forEach(gameLight -> gameLight.moveBy(moveTo));
        return this;
    }

    @Override
    public GameBuilding moveBy(Vector3f moveTo) {
        super.moveBy(moveTo);
        buildingLights.forEach(gameLight -> gameLight.moveBy(moveTo));
        return this;
    }

    @Override
    public GameBuilding moveBy(float x, float y) {
        super.moveBy(x, y);
        buildingLights.forEach(gameLight -> gameLight.moveBy(x,y));
        return this;
    }

    @Override
    public GameBuilding moveTo(Vector2f moveTo) {
        super.moveTo(moveTo);
        buildingLights.forEach(gameLight -> gameLight.moveTo(moveTo));
        return this;
    }

    @Override
    public GameBuilding moveTo(Vector3f moveTo) {
        super.moveTo(moveTo);
        buildingLights.forEach(gameLight -> gameLight.moveTo(moveTo));
        return this;
    }

    @Override
    public GameBuilding moveTo(float x, float y) {
        super.moveTo(x, y);
        buildingLights.forEach(gameLight -> gameLight.moveTo(x,y));
        return this;
    }
}
