package com.greymatter.miner.mainui.viewmodes;

import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeFactory {
    public static final String BUILDING_MODE = "buildingMode";
    public static final String GENERAL_MODE = "generalMode";

    public static ViewMode getNew(String mode, Camera camera, TouchController tc) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingMode(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralMode(tc, camera);
        }
    }
}
