package com.greymatter.miner.helpers.clicklisteners;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.mainui.touch.OnClickListener;

public class SimpleDialogClickListener implements OnClickListener {
    @Override
    public boolean onClick(IGameObject object) {
        if(!object.getContextMenu().shouldDraw()) {
            object.getContextMenu().show();
        }else{
            object.getContextMenu().hide();
        }
        return true;
    }

    @Override
    public boolean onLongClick(IGameObject object) {
        return false;
    }
}
