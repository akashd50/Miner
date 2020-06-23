package com.greymatter.miner.game;

import com.greymatter.miner.game.objects.GameBuilding;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBuildingsContainer {
    private static HashMap<String, GameBuilding> gameBuildings;

    public static void addBuilding(GameBuilding building) {
        if(gameBuildings == null) {
            gameBuildings = new HashMap<>();
        }
        gameBuildings.put(building.getId(), building);
    }

    public static void removeBuilding(String id) {
        if(gameBuildings!=null) {
            gameBuildings.remove(id);
        }
    }

    public static void onDrawFrame() {
        gameBuildings.forEach((id, building) -> {
            building.onDrawFrame();
        });
    }

    public static GameBuilding getBuilding(String id) {
        return gameBuildings.get(id);
    }
}
