package com.greymatter.miner.mainui.touch.touchmodes;

import com.greymatter.miner.game.objects.base.IGameObject;

public class TouchEventBundle {
    private IGameObject gameObject;

    public TouchEventBundle setObject(IGameObject IGameObject) {
        this.gameObject = IGameObject;
        return this;
    }

    public IGameObject getObject() {
        return gameObject;
    }
}
