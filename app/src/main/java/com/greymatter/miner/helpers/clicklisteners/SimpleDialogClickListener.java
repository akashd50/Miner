package com.greymatter.miner.helpers.clicklisteners;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.mainui.touch.OnClickListener;

public class SimpleDialogClickListener implements OnClickListener {
    @Override
    public boolean onClick(IGameObject object) {
        if(!object.getNotification().shouldDraw()) {
            object.getNotification().show();
        }else{
            object.getNotification().hide();
        }
        return true;
    }
}
