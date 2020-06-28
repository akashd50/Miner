package com.greymatter.miner.mainui.touch.touchviewmodes;

import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeFactory {
    public static AbstractViewMode getNew(ViewMode mode, TouchController tc, Camera camera) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingMode(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralMode(tc, camera);
        }
    }
}
