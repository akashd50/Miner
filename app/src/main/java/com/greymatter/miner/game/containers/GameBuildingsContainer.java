package com.greymatter.miner.game.containers;

import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.game.objects.GameBuilding;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Shader;
import java.util.ArrayList;

public class GameBuildingsContainer {
    private static HashMapE<String, GameBuilding> gameBuildings;
    private static GroupMap<String, GameBuilding> groupedByShader;

    public static void add(GameBuilding building) {
        if(gameBuildings == null) {
            gameBuildings = new HashMapE<>();
        }

        if(groupedByShader == null) {
            groupedByShader = new GroupMap<>();
        }

        gameBuildings.put(building.getId(), building);
        groupedByShader.add(building.getObjectDrawable().getShader().getId(), building);
    }

    public static void remove(String id) {
        GameBuilding removed = null;
        if(gameBuildings!=null) {
            removed = gameBuildings.remove(id);
        }

        if(groupedByShader != null && removed != null) {
            groupedByShader.delete(id,removed);
        }
    }

    public static void onDrawFrame() {
        gameBuildings.forEach((id, building) -> {
            building.onDrawFrame();
        });
    }

    public static void onDrawFrameByShader(Camera camera) {
        groupedByShader.forEach((shaderId, buildings) -> {
            Shader toUse = ShaderContainer.get(shaderId);
            ShaderHelper.useProgram(toUse);
            ShaderHelper.setCameraProperties(toUse, camera);

            buildings.forEach(GameBuilding::onDrawFrame);
        });
    }

    public static GameBuilding get(String id) {
        return gameBuildings.get(id);
    }

    public static ArrayList<GameBuilding> getAll() {
        return gameBuildings.toList();
    }
}
