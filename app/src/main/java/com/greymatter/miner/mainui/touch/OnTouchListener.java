package com.greymatter.miner.mainui.touch;

import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;

import javax.vecmath.Vector2f;

public interface OnTouchListener {
    boolean onTouchDown(IGameObject gameObject, Vector2f pointer);
    boolean onTouchMove(IGameObject gameObject, Vector2f pointer);
    boolean onTouchUp(IGameObject gameObject, Vector2f pointer);

    default void defaultOnTouchMove(IGameObject gameObject, Vector2f pointer) {
        pointer.x -= gameObject.getTouchDownOffset().x;
        pointer.y -= gameObject.getTouchDownOffset().y;

        gameObject.moveTo(pointer);
        gameObject.getTransforms().rotateTo(0f,0f, VectorHelper.angleBetweenDegrees(GameManager.getCurrentPlanet().getDrawable(), gameObject.getDrawable()) - 90);
    }
}
