package com.greymatter.miner.mainui.viewmode;

import com.greymatter.miner.mainui.renderers.AbstractRenderer;
import com.greymatter.miner.mainui.renderers.BuildingRenderer;
import com.greymatter.miner.mainui.renderers.GeneralRenderer;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.touch.touchmodes.AbstractTouchHandler;
import com.greymatter.miner.mainui.touch.touchmodes.BuildingTouchHandler;
import com.greymatter.miner.mainui.touch.touchmodes.GeneralTouchHandler;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeFactory {
    public static AbstractTouchHandler getNewTouchMode(ViewMode mode, TouchHelper tc, Camera camera) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingTouchHandler(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralTouchHandler(tc, camera);
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
