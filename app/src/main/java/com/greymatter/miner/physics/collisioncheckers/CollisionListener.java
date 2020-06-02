package com.greymatter.miner.physics.collisioncheckers;

import android.util.Log;

import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.physics.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.Collider;

public class CollisionListener {
    private Thread collisionCheckerThread;
    private boolean isActive;
    private long waitTimeBWChecks;
    private Collider linkedCollider;
    public CollisionListener(Collider collider, long waitTimeBWChecks) {
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
                    boolean hasCollided = CollisionDetector.checkCollision(linkedCollider, drawable.getCollider());

                    if(hasCollided && linkedCollider.getCollisionHandler()!=null) {
                        Log.v("CollisionListener" ,"Collision Detected");
                        linkedCollider.getCollisionHandler().obtainMessage(1,linkedCollider).sendToTarget();
                    }/*else {
                        linkedCollider.getCollisionHandler().obtainMessage(0, linkedCollider).sendToTarget();
                    }*/
                }
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
}
