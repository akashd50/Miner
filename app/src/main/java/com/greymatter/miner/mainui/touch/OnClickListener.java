package com.greymatter.miner.mainui.touch;

import com.greymatter.miner.game.objects.base.IGameObject;

public interface OnClickListener {
    boolean onClick(IGameObject object);
    boolean onLongClick(IGameObject object);
}
