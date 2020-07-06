package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.ArrayList;

public abstract class GameBuilding extends GameObject{
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
        return buildingLights != null && buildingLights.size()>0;
    }
}
