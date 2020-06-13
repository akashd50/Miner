package com.greymatter.miner.opengl;

import android.view.MotionEvent;
import android.view.View;

import com.greymatter.miner.R;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Camera;

import javax.vecmath.Vector3f;

import static com.greymatter.miner.opengl.MainGLObjectsHelper.*;

public class MainGLTouchHelper {
    private static Camera camera;
    public static void onViewChanged(Camera cam) {
        camera = cam;
    }

    public static void onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                Vector3f touchPoint = getTouchPointVector(event.getX(), event.getY());

                ball.translateTo(touchPoint);
                ball.setVelocity(new Vector3f(0f,0f,0f));
                ball.rotateTo(new Vector3f());
                ball.getCollider().setAngularAcceleration(0f);
                ball.getCollider().setAngularVelocity(0f);
                Vector3f newPos = new Vector3f(touchPoint);
                newPos.y+=0.5f;

                //ball2.translateTo(newPos);
                //ball2.setVelocity(new Vector3f());

                //test angles
                System.out.println("Angle Test: "+Math.sin(VectorHelper.angle(new Vector3f(3f,1f,0f), new Vector3f(3f,2f,1f))));
                System.out.println(Math.sin(VectorHelper.angle(new Vector3f(2f,3f,0f), new Vector3f(3f,2f,1f))));
                break;
        }
    }

    public static void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_left:
                ball.updateVelocity(new Vector3f(-0.01f, 0f,0f));
                break;
            case R.id.move_right:
                ball.updateVelocity(new Vector3f(0.01f, 0f,0f));
                break;
            default:
                //ball.setAcceleration(new Vector3f(-0.001f, 0f,0f));
                break;
        }
    }

    private static Vector3f getTouchPointVector(float x, float y) {
        return new Vector3f(getLocalX(x), getLocalY(y), 0f);
    }

    private static float getLocalX(float x) {
        return camera.getTranslation().x + camera.getCameraWidth() * x/camera.getViewportWidth() - camera.getCameraWidth()/2;
    }

    private static float getLocalY(float y) {
        return camera.getTranslation().y + camera.getCameraHeight()/2 - camera.getCameraHeight() * y/camera.getViewportHeight();
    }
}
