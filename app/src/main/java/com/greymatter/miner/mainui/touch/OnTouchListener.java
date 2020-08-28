package com.greymatter.miner.mainui.touch;

import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.helpers.VectorHelper;

import javax.vecmath.Vector2f;

public interface OnTouchListener {
    boolean onTouchDown(GameObject gameObject, Vector2f pointer);
    boolean onTouchMove(GameObject gameObject, Vector2f pointer);
    boolean onTouchUp(GameObject gameObject, Vector2f pointer);

    default void defaultOnTouchMove(GameObject gameObject, Vector2f pointer) {
        pointer.x -= gameObject.getTouchDownOffset().x;
        pointer.y -= gameObject.getTouchDownOffset().y;

        gameObject.moveTo(pointer);
        gameObject.getTransforms().rotateTo(0f,0f, VectorHelper.angleBetween(GameObjectsContainer.get(GameManager.getCurrentPlanet()).getDrawable(), gameObject.getDrawable()) - 90);
    }
}
