package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.loaders.enums.ObjId;
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
    private Vector3f pointOnSurface;
    public GameBuilding(ObjId id, Drawable drawable) {
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
                Vector3f dir = VectorHelper.sub(pointOnSurface, getLocation());
                dir.normalize();
                dir.z = 0f;
                object.getTransforms().translateBy(VectorHelper.multiply(dir, 0.4f));
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public void onTouchDown(GameObject gameObject, Vector2f pointer) {

            }

            @Override
            public void onTouchMove(GameObject gameObject, Vector2f pointer) {
                if(ViewModeManager.getActiveTouchMode().getViewMode() == ViewMode.BUILDING_MODE) {
                    OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
                }
            }

            @Override
            public void onTouchUp(GameObject gameObject, Vector2f pointer) {
                Transforms planetTransforms = GameObjectsContainer.get(ObjId.PLANET).getTransforms();
                float angleBetween = VectorHelper.angleBetweenRad(GameObjectsContainer.get(ObjId.PLANET).getDrawable(), gameObject.getDrawable());
                float px = planetTransforms.getTranslation().x + planetTransforms.getScale().y * (float)Math.cos(angleBetween);
                float py = getTransforms().getScale().y + planetTransforms.getTranslation().y + planetTransforms.getScale().y * (float)Math.sin(angleBetween);
                pointOnSurface = new Vector3f(px, py,0f);
                snapAnimator.resume();
            }
        });

    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        snapAnimator.update();
    }
}
