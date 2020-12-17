package com.greymatter.miner.game.manager;

import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.GameConstants;
import com.greymatter.miner.game.objects.Planet;

public class GameManager {
    private static String currentPlanetID = GameConstants.PLANET_1;
    public static Planet getCurrentPlanet() {
        return (Planet) ContainerManager.getAllGameObjectsContainer().get(currentPlanetID);
    }
}
