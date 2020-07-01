package com.greymatter.miner.mainui.viewmode;

import com.greymatter.miner.R;
import com.greymatter.miner.mainui.LayoutHelper;
import com.greymatter.miner.mainui.renderers.AbstractRenderer;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.touch.touchmodes.AbstractTouchMode;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeManager {
    private static AbstractTouchMode activeTouchMode;
    private static AbstractRenderer activeRenderer;

    public static AbstractTouchMode getActiveTouchMode() {
        return activeTouchMode;
    }
    public static AbstractRenderer getActiveRenderer() {
        return activeRenderer;
    }

    public static void switchToGeneralMode(TouchHelper tc, Camera camera) {
        LayoutHelper.hide(R.id.building_mode_ll);
        LayoutHelper.show(R.id.general_mode_ll);

        activeTouchMode = ViewModeFactory.getNewTouchMode(ViewMode.GENERAL_MODE, tc, camera);
        activeRenderer = ViewModeFactory.getNewRendererMode(ViewMode.GENERAL_MODE, tc,camera);
    }

    public static void switchToBuildingMode(TouchHelper tc, Camera camera) {
        LayoutHelper.hide(R.id.general_mode_ll);
        LayoutHelper.show(R.id.building_mode_ll);

        activeTouchMode = ViewModeFactory.getNewTouchMode(ViewMode.BUILDING_MODE, tc, camera);
        activeRenderer = ViewModeFactory.getNewRendererMode(ViewMode.BUILDING_MODE, tc,camera);
    }

    public static void switchTo(ViewMode mode, TouchHelper tc, Camera camera) {
        activeTouchMode = ViewModeFactory.getNewTouchMode(mode, tc, camera);
        activeRenderer = ViewModeFactory.getNewRendererMode(mode, tc,camera);
    }
}
