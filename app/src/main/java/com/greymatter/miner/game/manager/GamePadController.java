package com.greymatter.miner.game.manager;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.containers.ActiveUIContainer;
import com.greymatter.miner.containers.AllGameObjectsContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.GamePad;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import javax.vecmath.Vector3f;

import static com.greymatter.miner.game.GameConstants.*;

public class GamePadController {
    private static final AllGameObjectsContainer allGameObjectsContainer = ContainerManager.getAllGameObjectsContainer();

    public static void setCurrentGamePadObject(GameObject gamePadObject) {
        ((GamePad) allGameObjectsContainer.get(GAME_PAD))
                .setCurrentControllableObject(gamePadObject);
    }

    public static void onGamePadAnimationFrame(GamePad gamePad,
                                               GameObject object,
                                               ValueAnimator animator) {
       IGameObject controllableObject = gamePad.getCurrentControllableObject();
       if (controllableObject == null) return;

        switch (controllableObject.getId()) {
            case MAIN_CHARACTER_1:
                Vector3f left = VectorHelper.getNormal(AppServices.getGameCamera().getUpVector());
                controllableObject.getRigidBody().getRBProps()
                        .updateVelocity(VectorHelper.multiply(left, -gamePad.getFactor().x*0.01f));
                break;
            case "GAME_PAD_FRONT":
                controllableObject.getTransforms().translateBy(gamePad.getFactor().x*0.08f,gamePad.getFactor().y*0.08f);
                break;
        }
    }
}
