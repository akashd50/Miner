package com.greymatter.miner.mainui.touch;

import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.helpers.VectorHelper;

import javax.vecmath.Vector2f;

public interface OnTouchListener {
    void onTouchDown(GameObject gameObject, Vector2f pointer);
    void onTouchMove(GameObject gameObject, Vector2f pointer);
    void onTouchUp(GameObject gameObject, Vector2f pointer);

    default void defaultOnTouchMove(GameObject gameObject, Vector2f pointer) {
        gameObject.moveTo(pointer);
        gameObject.getTransforms().rotateTo(0f,0f, VectorHelper.angleBetween(GameObjectsContainer.get(ObjId.PLANET).getDrawable(), gameObject.getDrawable()));
    }
}
