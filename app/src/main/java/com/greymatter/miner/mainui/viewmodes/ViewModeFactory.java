package com.greymatter.miner.mainui.viewmodes;

import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeFactory {
    public static IViewMode getNew(ViewMode mode, Camera camera, TouchController tc) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingMode(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralMode(tc, camera);
        }
    }
}
