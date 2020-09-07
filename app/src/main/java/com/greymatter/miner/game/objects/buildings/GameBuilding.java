package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.IntersectionEvent;
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
                    onSnapAnimationComplete();
                }
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
                return true;
            }

            @Override
            public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
                if(ViewModeManager.getActiveTouchMode().getViewMode() == ViewMode.BUILDING_MODE) {
                    OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
                if(ViewModeManager.getActiveTouchMode().getViewMode() == ViewMode.BUILDING_MODE) {
                    IGameObject planet = GameObjectsContainer.get(GameManager.getCurrentPlanet());
                    snapTo(planet);
                    return true;
                }
                return false;
            }
        });
    }

    public GameBuilding snapTo(IGameObject object) {
        IntersectionEvent event = VectorHelper.findSnapPlaceOnRBSurface(object.getRigidBody().asPolygonRB(), getLocation());
        snappingPoint = event.intPoint;

        Vector3f normal = VectorHelper.getNormal(VectorHelper.sub(event.intLineB, event.intLineA));
        normal.normalize();

        float angle = (float)Math.atan2(normal.y, normal.x);
        float toDegrees = (float)Math.toDegrees(angle);
        getTransforms().rotateTo(0f,0f,toDegrees - 90);

        snappingPoint.x+= getTransforms().getScale().y * normal.x;
        snappingPoint.y+= getTransforms().getScale().y * normal.y;

        snapAnimator.resume();
        return this;
    }

    public GameBuilding snapTo(IGameObject object, float angleRad) {
        Transforms objectTransforms = object.getTransforms();
        float px = objectTransforms.getTranslation().x + objectTransforms.getScale().y * (float) Math.cos(angleRad);
        float py = getTransforms().getScale().y + objectTransforms.getTranslation().y + objectTransforms.getScale().y * (float) Math.sin(angleRad);
        snappingPoint = new Vector3f(px, py, 0f);
        snapAnimator.resume();
        return this;
    }

    public void onSnapAnimationComplete() {

    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        snapAnimator.update();
    }
}
