package com.greymatter.miner.mainui.viewmode;

import com.greymatter.miner.mainui.renderers.AbstractRenderer;
import com.greymatter.miner.mainui.renderers.BuildingRenderer;
import com.greymatter.miner.mainui.renderers.GeneralRenderer;
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

    public static AbstractRenderer getNewRendererMode(ViewMode mode, TouchHelper tc, Camera camera) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingRenderer(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralRenderer(tc, camera);
        }
    }
}
