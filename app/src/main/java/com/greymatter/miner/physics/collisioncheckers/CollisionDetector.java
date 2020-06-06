package com.greymatter.miner.physics.collisioncheckers;

import android.util.Log;

import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;

public class CollisionDetector {
    private Thread collisionCheckerThread;
    private boolean isActive;
    private long waitTimeBWChecks;
    private Collider linkedCollider;
    public CollisionDetector(Collider collider, long waitTimeBWChecks) {
        this.linkedCollider = collider;
        this.isActive = false;
        this.waitTimeBWChecks = waitTimeBWChecks;
        setUpCollisionCheckThread();
    }

    public void onStart() {
        isActive = true;
        collisionCheckerThread.start();
    }

    public void onDestroy(){
        this.isActive = false;
    }

    private void setUpCollisionCheckThread() {
        collisionCheckerThread = new Thread(new Runnable() {
            @Override
            public void run() {
            while(isActive) {
                for(Drawable drawable : CollisionDetectionSystem.getSystemObjectsExcept(
                        linkedCollider.getDrawable())) {
                    CollisionEvent event = CollisionDetectionHelper.checkCollision(linkedCollider, drawable.getCollider());

                    if(event.getCollisionStatus() && linkedCollider.getCollisionListener()!=null) {

                        Log.v("CollisionListener" ,"Collision Detected");

                        linkedCollider.getCollisionListener().onCollision(event);
                    }
                }
                if(waitTimeBWChecks!=0) {
                    synchronized (this) {
                        try {
                            this.wait(waitTimeBWChecks);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            }
        });
    }
}
