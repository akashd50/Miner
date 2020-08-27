package com.greymatter.miner.game.manager;

public class MinerManager {
    private static int activeMiner = 0;
    private static final int totalMiners = 5;

    public static String getActiveMinerId() {
        return "MINER_"+activeMiner;
    }

    public static String getNextMinerId() {
        return "MINER_"+(++activeMiner);
    }
}
