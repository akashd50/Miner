package com.greymatter.miner.helpers.clicklisteners;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.mainui.touch.OnClickListener;

public class SimpleDialogClickListener implements OnClickListener {
    @Override
    public boolean onClick(IGameObject object) {
        if(!object.getDialog().shouldDraw()) {
            object.getDialog().show();
        }else{
            object.getDialog().hide();
        }
        return true;
    }
}
