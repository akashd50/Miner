package com.greymatter.miner.mainui.viewmodes;

import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeManager {
    private static IViewMode activeIViewMode;

    public static IViewMode getActiveViewMode() {
        return activeIViewMode;
    }

    public static void switchTo(ViewMode mode, TouchController tc, Camera camera) {
        activeIViewMode = ViewModeFactory.getNew(mode, camera, tc);
    }
}
