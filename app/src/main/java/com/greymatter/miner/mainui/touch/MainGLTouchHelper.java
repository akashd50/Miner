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
    public static void onViewChanged(Camera cam) {
        camera = cam;
        touchController = new TouchController();
        ViewModeManager.switchToGeneralMode(touchController, camera);
    }

    public static void onTouch(MotionEvent event) {
        ViewModeManager.getActiveViewMode().onTouch(event);
    }

    public static void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_left:
                Vector3f left = VectorHelper.getNormal(DrawableContainer.get(MAIN_CHARACTER).getCollider().getUpVector());
                DrawableContainer.get(MAIN_CHARACTER).getCollider().updateVelocity(VectorHelper.multiply(left, 0.01f));
                break;
            case R.id.move_right:
                Vector3f right = VectorHelper.multiply(VectorHelper.getNormal(DrawableContainer.get(MAIN_CHARACTER).getCollider().getUpVector()), -1f);
                DrawableContainer.get(MAIN_CHARACTER).getCollider().updateVelocity(VectorHelper.multiply(right, 0.01f));
                break;
            default:
                break;
        }
    }
}
