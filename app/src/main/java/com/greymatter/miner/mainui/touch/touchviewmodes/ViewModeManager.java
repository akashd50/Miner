package com.greymatter.miner.mainui.touch.touchviewmodes;

import com.greymatter.miner.mainui.renderers.AbstractRendererMode;
import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeManager {
    private static AbstractTouchMode activeTouchMode;
    private static AbstractRendererMode activeRendererMode;

    public static AbstractTouchMode getActiveTouchMode() {
        return activeTouchMode;
    }
    public static AbstractRendererMode getActiveRendererMode() {
        return activeRendererMode;
    }

    public static void switchToGeneralMode(TouchController tc, Camera camera) {
        activeTouchMode = ViewModeFactory.getNewTouchMode(ViewMode.GENERAL_MODE, tc, camera);
        activeRendererMode = ViewModeFactory.getNewRendererMode(ViewMode.GENERAL_MODE, tc,camera);
    }

    public static void switchToBuildingMode(TouchController tc, Camera camera) {
        activeTouchMode = ViewModeFactory.getNewTouchMode(ViewMode.BUILDING_MODE, tc, camera);
        activeRendererMode = ViewModeFactory.getNewRendererMode(ViewMode.BUILDING_MODE, tc,camera);
    }

    public static void switchTo(ViewMode mode, TouchController tc, Camera camera) {
        activeTouchMode = ViewModeFactory.getNewTouchMode(mode, tc, camera);
        activeRendererMode = ViewModeFactory.getNewRendererMode(mode, tc,camera);
    }
}
