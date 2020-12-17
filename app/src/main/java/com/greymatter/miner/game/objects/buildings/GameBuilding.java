package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.IntersectionEvent;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameBuilding extends GameObjectWGL implements OnTouchListener, OnClickListener {
    private FloatValueAnimator snapAnimator;
    private Vector3f snappingPoint, startingPoint;
    public GameBuilding(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        startingPoint = getLocation();
        snapAnimator = new FloatValueAnimator();
        snapAnimator.withFPS(30).setSingleCycle(true).setToAnimateObject(this);
        snapAnimator.setPerFrameIncrement(0.1f);
        snapAnimator.pause();
        snapAnimator.setOnAnimationFrameHandler((object, animator) -> {
            float x = (startingPoint.x * (1f - animator.getUpdatedFloat()) + snappingPoint.x * animator.getUpdatedFloat());
            float y = (startingPoint.y * (1f - animator.getUpdatedFloat()) + snappingPoint.y * animator.getUpdatedFloat());
            object.moveTo(x,y);
        });

        this.setOnTouchListener(this);
        this.setOnClickListener(this);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        snapAnimator.update();
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

        snapAnimator.startFrom(0f,true).resume();
        return this;
    }

    public GameBuilding snapTo(IGameObject object, float angleRad) {
        Transforms objectTransforms = object.getTransforms();
        float px = objectTransforms.getTranslation().x + objectTransforms.getScale().y * (float) Math.cos(angleRad);
        float py = getTransforms().getScale().y + objectTransforms.getTranslation().y + objectTransforms.getScale().y * (float) Math.sin(angleRad);
        snappingPoint = new Vector3f(px, py, 0f);
        snapAnimator.startFrom(0f,true).resume();
        return this;
    }

    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        return true;
    }

    @Override
    public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
        if(ViewModeManager.getActiveTouchHandler().getViewMode() == ViewMode.BUILDING_MODE) {
            OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
        if(ViewModeManager.getActiveTouchHandler().getViewMode() == ViewMode.BUILDING_MODE) {
            IGameObject planet = GameManager.getCurrentPlanet();
            snapTo(planet);
            return true;
        }
        return false;
    }

    @Override
    public boolean onClick(IGameObject object) {
        return false;
    }
}
