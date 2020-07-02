package com.greymatter.miner.mainui.touch.touchmodes;

import com.greymatter.miner.game.objects.GameObject;

public class TouchEventBundle {
    private GameObject gameObject;

    public TouchEventBundle setDrawable(GameObject go) {
        this.gameObject = go;
        return this;
    }

    public GameObject getObject() {
        return gameObject;
    }
}
