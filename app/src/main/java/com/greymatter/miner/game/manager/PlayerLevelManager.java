package com.greymatter.miner.game.manager;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameObject;

public class PlayerLevelManager {
    private static int MAIN_BASE_LEVEL = 1;
    private static int MAIN_CHAR_LEVEL = 1;
    private static HashMapE<String, Integer> SCANNER_LEVELS;
    public static void upgrade(GameObject gameObject) {
        gameObject.upgrade();
    }
}
