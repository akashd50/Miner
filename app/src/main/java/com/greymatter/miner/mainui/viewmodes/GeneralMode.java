package com.greymatter.miner.mainui.viewmodes;

import android.view.View;

import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

public class GeneralMode implements IViewMode {
    private Camera mainCamera;
    private TouchController touchController;

    public GeneralMode(TouchController controller, Camera camera) {
        this.mainCamera = camera;
        this.touchController = controller;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void doOnTouchDown() {

    }

    @Override
    public void doOnTouchMove() {

    }

    @Override
    public void doOnTouchUp() {

    }
}
