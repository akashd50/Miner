package com.greymatter.miner.physics.collisioncheckers;

import android.util.Log;
import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.CollisionDetectionSystem;
import javax.vecmath.Vector3f;

public class CollisionChecker {
    private Thread collisionCheckerThread;
    private boolean isActive;
    private long waitTimeBWChecks;
    public CollisionChecker(Drawable obj1, Drawable obj2, long spacing) {
        this.isActive = true;
        this.waitTimeBWChecks = spacing;
        this.setUpCollisionCheckThread(obj1, obj2);
    }

    public void start() {
        collisionCheckerThread.start();
    }

    private void setUpCollisionCheckThread(final Drawable obj1, final Drawable obj2) {
        collisionCheckerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isActive) {
                    boolean hasCollided = CollisionDetectionSystem.circleVCircle(
                            obj1.getCollider().asCircleCollider(),
                            obj2.getCollider().asCircleCollider());


                    if(hasCollided) {
                        obj2.translateTo(new Vector3f(0f,1f,0f));
                    }
                    Log.v("CollisionHandler", hasCollided+"");

                    synchronized (this) {
                        try {
                            this.wait(waitTimeBWChecks);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void onDestroy(){
        this.isActive = false;
    }
}
