package com.greymatter.miner.physics.riggingsystem;

import java.util.ArrayList;

public class Skeleton {
    private Bone base;
    private ArrayList<Bone> bones;
    public Skeleton(Bone base) {
        this.base = base;
    }

    public Bone getBaseBone() {
        return this.base;
    }
}
