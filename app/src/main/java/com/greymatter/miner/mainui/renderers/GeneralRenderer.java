package com.greymatter.miner.mainui.renderers;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.containers.ActiveUIContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.physics.objects.CollisionEvent;
import static com.greymatter.miner.game.GameConstants.*;

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

        IGameObject planet = GameManager.getCurrentPlanet();
        IGameObject mainCharacter = ContainerManager.getAllGameObjectsContainer().get(MAIN_CHARACTER_1);
        CollisionEvent collisionEvent = mainCharacter.getRigidBody().getLastCollisionEvent(planet.getRigidBody());
//        if (collisionEvent != null && collisionEvent.getCollisionPoint()!=null) {
//            Vector3f collPoint = collisionEvent.getCollisionPoint();
//            GameObjectsContainer.get("POINT").moveTo(collPoint.x, collPoint.y);
//            GameObjectsContainer.get("LINE").getDrawable().asLine().setVertex(0, collisionEvent.getLinkedObjCollLinePt1());
//            GameObjectsContainer.get("LINE").getDrawable().asLine().setVertex(1, collisionEvent.getLinkedObjCollLinePt2());
//        }

        /*<---------------------------------------update----------------------------------------->*/


        /*<-----------------------------------------draw----------------------------------------->*/
        ContainerManager.getActiveGameObjectsContainer().onDrawFrame(AppServices.getGameCamera());
        ContainerManager.getActiveResourceContainer().onDrawFrame(AppServices.getGameCamera());
        ContainerManager.getActiveUIContainer().onDrawFrame(AppServices.getUICamera());

//        System.out.println("FPS: " + mainFPS);
//        System.out.println("FRAME TIME: " + (System.currentTimeMillis() - currentFrameStartTime));
    }
}
