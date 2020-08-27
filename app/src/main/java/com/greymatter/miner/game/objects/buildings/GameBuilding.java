package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameBuilding extends GameObjectWGL {
    private BooleanAnimator snapAnimator;
    private Vector3f snappingPoint;
    public GameBuilding(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        snapAnimator = new BooleanAnimator();
        snapAnimator.withFPS(60);
        snapAnimator.toAndFro(true).setToAnimateObject(this);
        snapAnimator.pause();
        snapAnimator.setOnAnimationFrameHandler((object, animator) -> {
            if(animator.getUpdatedBoolean()) {
                Vector3f dir = VectorHelper.sub(snappingPoint, getLocation());
                dir.normalize();
                dir.z = 0f;
                object.getTransforms().translateBy(VectorHelper.multiply(dir, 1f));

                if(VectorHelper.getDistanceWithSQRT(object.getTransforms().getTranslation(), snappingPoint) <= 0.05f) {
                    snapAnimator.pause();
                }
                object.getTransforms().rotateTo(0f,0f, VectorHelper.angleBetween(GameObjectsContainer.get(GameManager.getCurrentPlanet()).getDrawable(), object.getDrawable()) - 90);
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouchDown(GameObject gameObject, Vector2f pointer) {
                return false;
            }

            @Override
            public boolean onTouchMove(GameObject gameObject, Vector2f pointer) {
                if(ViewModeManager.getActiveTouchMode().getViewMode() == ViewMode.BUILDING_MODE) {
                    OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onTouchUp(GameObject gameObject, Vector2f pointer) {
                if(ViewModeManager.getActiveTouchMode().getViewMode() == ViewMode.BUILDING_MODE) {
                    Transforms planetTransforms = GameObjectsContainer.get(GameManager.getCurrentPlanet()).getTransforms();
                    float angleBetween = VectorHelper.angleBetweenRad(GameObjectsContainer.get(GameManager.getCurrentPlanet()).getDrawable(), gameObject.getDrawable());
                    float px = planetTransforms.getTranslation().x + planetTransforms.getScale().y * (float) Math.cos(angleBetween);
                    float py = getTransforms().getScale().y + planetTransforms.getTranslation().y + planetTransforms.getScale().y * (float) Math.sin(angleBetween);
                    snappingPoint = new Vector3f(px, py, 0f);
                    snapAnimator.resume();
                    return true;
                }
                return false;
            }
        });
    }

    public void snapTo(IGameObject object, float angleRad) {
        Transforms objectTransforms = object.getTransforms();
        float px = objectTransforms.getTranslation().x + objectTransforms.getScale().y * (float) Math.cos(angleRad);
        float py = getTransforms().getScale().y + objectTransforms.getTranslation().y + objectTransforms.getScale().y * (float) Math.sin(angleRad);
        snappingPoint = new Vector3f(px, py, 0f);
        snapAnimator.resume();
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        snapAnimator.update();
    }
}
