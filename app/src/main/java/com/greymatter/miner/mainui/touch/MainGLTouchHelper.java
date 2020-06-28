package com.greymatter.miner.mainui.touch;

import android.view.MotionEvent;
import android.view.View;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.DrawableContainer;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.mainui.touch.touchviewmodes.ViewModeFactory;
import com.greymatter.miner.mainui.touch.touchviewmodes.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import static com.greymatter.miner.game.GC.*;

public class MainGLTouchHelper {
    private static Camera camera;
    private static TouchController touchController;
    private static boolean initialSetup = true;
    public static void onSurfaceChanged(Camera cam) {
        if(initialSetup) {
            camera = cam;
            touchController = new TouchController();
            ViewModeManager.switchToGeneralMode(touchController, camera);
            initialSetup = false;
        }
    }

    public static void onTouch(MotionEvent event) {
        ViewModeManager.getActiveViewMode().onTouch(event);
    }

    public static void onClick(View v) {
        ViewModeManager.getActiveViewMode().onClick(v);
    }
}
