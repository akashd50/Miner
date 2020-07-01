package com.greymatter.miner.mainui.viewmode;

import com.greymatter.miner.mainui.renderers.AbstractRendererMode;
import com.greymatter.miner.mainui.renderers.BuildingModeRenderer;
import com.greymatter.miner.mainui.renderers.GeneralModeRenderer;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.touch.touchmodes.AbstractTouchMode;
import com.greymatter.miner.mainui.touch.touchmodes.BuildingTouchMode;
import com.greymatter.miner.mainui.touch.touchmodes.GeneralTouchMode;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeFactory {
    public static AbstractTouchMode getNewTouchMode(ViewMode mode, TouchHelper tc, Camera camera) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingTouchMode(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralTouchMode(tc, camera);
        }
    }

    public static AbstractRendererMode getNewRendererMode(ViewMode mode, TouchHelper tc, Camera camera) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingModeRenderer(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralModeRenderer(tc, camera);
        }
    }
}
