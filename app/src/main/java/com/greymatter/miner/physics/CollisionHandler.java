package com.greymatter.miner.physics;

import android.util.Log;

import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.objects.CircleCollider;

import javax.vecmath.Vector3f;

public class CollisionHandler {
    public static void setUpCollisionCheckThread(final Drawable obj1, final Drawable obj2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    boolean hasCollided = circleVCircle(obj1.getCollider().asCircleCollider(),
                                                        obj2.getCollider().asCircleCollider());
                    if(hasCollided) {
                        obj2.translateTo(new Vector3f(0f,1f,0f));
                    }
                    Log.v("CollisionHandler", hasCollided+"");

                    synchronized (this) {
                        try {
                            this.wait(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public static boolean circleVCircle(CircleCollider c1, CircleCollider c2) {
        float marginOfError = 80f;
        float r = c1.getRadius() + c2.getRadius();
        Log.v("Radius Sum: ", r+"");

        r *= r;

        Log.v("Translation c1: ", c1.getTranslation().toString());
        Log.v("Translation c2: ", c2.getTranslation().toString());

        return r < Math.pow((c1.getTranslation().x + c2.getTranslation().x),2)
                + Math.pow((c1.getTranslation().y + c2.getTranslation().y),2) - marginOfError;
    }
}
