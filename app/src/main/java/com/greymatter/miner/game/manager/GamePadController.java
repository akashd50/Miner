package com.greymatter.miner.game.manager;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.GamePad;
import com.greymatter.miner.helpers.VectorHelper;
import javax.vecmath.Vector3f;

public class GamePadController {
    public static void setCurrentGamePadObject(GameObject gamePadObject) {
        ((GamePad) GameObjectsContainer.get("GAME_PAD")).setCurrentControllableObject(gamePadObject);
    }

    public static void onGamePadAnimationFrame(GamePad gamePad,
                                               GameObject object,
                                               ValueAnimator animator) {
       GameObject controllableObject = gamePad.getCurrentControllableObject();
        switch (controllableObject.getId()) {
            case "MAIN_CHARACTER":
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
