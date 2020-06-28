package com.greymatter.miner.mainui.touch.touchviewmodes;

import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeManager {
    private static AbstractViewMode activeIViewMode;

    public static AbstractViewMode getActiveViewMode() {
        return activeIViewMode;
    }

    public static AbstractViewMode switchToGeneralMode(TouchController tc, Camera camera) {
        activeIViewMode = ViewModeFactory.getNew(ViewMode.GENERAL_MODE, tc, camera);
        return activeIViewMode;
    }

    public static AbstractViewMode switchToBuildingMode(TouchController tc, Camera camera) {
        activeIViewMode = ViewModeFactory.getNew(ViewMode.BUILDING_MODE, tc, camera);
        return activeIViewMode;
    }

    public static void switchTo(ViewMode mode, TouchController tc, Camera camera) {
        activeIViewMode = ViewModeFactory.getNew(mode, tc, camera);
    }
}
