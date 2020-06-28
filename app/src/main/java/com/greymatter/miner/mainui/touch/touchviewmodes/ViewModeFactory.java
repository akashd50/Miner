package com.greymatter.miner.mainui.touch.touchviewmodes;

import com.greymatter.miner.mainui.renderers.AbstractRendererMode;
import com.greymatter.miner.mainui.renderers.BuildingModeRenderer;
import com.greymatter.miner.mainui.renderers.GeneralModeRenderer;
import com.greymatter.miner.mainui.renderers.MainGLRenderer;
import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

public class ViewModeFactory {
    public static AbstractTouchMode getNewTouchMode(ViewMode mode, TouchController tc, Camera camera) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingTouchMode(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralTouchMode(tc, camera);
        }
    }

    public static AbstractRendererMode getNewRendererMode(ViewMode mode, TouchController tc, Camera camera) {
        switch (mode) {
            case BUILDING_MODE:
                return new BuildingModeRenderer(tc, camera);
            case GENERAL_MODE:
            default:
                return new GeneralModeRenderer(tc, camera);
        }
    }
}
