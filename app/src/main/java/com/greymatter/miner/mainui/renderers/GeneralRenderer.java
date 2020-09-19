package com.greymatter.miner.mainui.renderers;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.containers.UIToDrawContainer;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.rb.RigidBody;

import javax.vecmath.Vector3f;

public class GeneralRenderer extends AbstractRenderer {
    private long prevCountFinishTime;
    private int fps, mainFPS;
    public GeneralRenderer(TouchHelper touchHelper, Camera camera) {
        super(touchHelper, camera);
    }

    public synchronized void onDrawFrame() {
        if(System.currentTimeMillis() - prevCountFinishTime < 1000){
               fps++;
        }else{
            mainFPS = fps;
            prevCountFinishTime = System.currentTimeMillis();
            fps = 0;
        }

        long currentFrameStartTime = System.currentTimeMillis();

        super.onDrawFrame();

        IGameObject planet = GameObjectsContainer.get(GameManager.getCurrentPlanet());
        IGameObject mainCharacter = GameObjectsContainer.get("MAIN_CHARACTER");
        CollisionEvent collisionEvent = mainCharacter.getRigidBody().getLastCollisionEvent(planet.getRigidBody());
        if (collisionEvent != null && collisionEvent.getCollisionPoint()!=null) {
            Vector3f collPoint = collisionEvent.getCollisionPoint();
            GameObjectsContainer.get("POINT").moveTo(collPoint.x, collPoint.y);
            GameObjectsContainer.get("LINE").getDrawable().asLine().setVertex(0, collPoint);
            GameObjectsContainer.get("LINE").getDrawable().asLine().setVertex(1, VectorHelper.add(collisionEvent.getLinkedObjectCollisionVector(), collPoint));
        }

        /*<---------------------------------------update----------------------------------------->*/


        /*<-----------------------------------------draw----------------------------------------->*/
        ToDrawContainer.onDrawFrame(AppServices.getGameCamera());
        ActiveResourcesContainer.onDrawFrame(AppServices.getGameCamera());
        UIToDrawContainer.onDrawFrame(AppServices.getUICamera());

        System.out.println("FPS: " + mainFPS);
        System.out.println("FRAME TIME: " + (System.currentTimeMillis() - currentFrameStartTime));
    }
}
