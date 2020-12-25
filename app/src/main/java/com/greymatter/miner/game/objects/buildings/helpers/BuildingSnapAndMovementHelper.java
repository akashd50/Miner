package com.greymatter.miner.game.objects.buildings.helpers;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.helpers.IntersectionEvent;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.Transforms;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class BuildingSnapAndMovementHelper implements OnTouchListener {
    private FloatValueAnimator snapAnimator;
    private GameBuilding building;
    private Vector3f snappingPoint;
    public BuildingSnapAndMovementHelper(GameBuilding building) {
        this.building = building;
        initialize();
    }

    private void initialize() {
        snapAnimator = new FloatValueAnimator();
        snapAnimator.withFPS(30).setSingleCycle(true).setToAnimateObject(building);
        snapAnimator.setPerFrameIncrement(0.1f);
        snapAnimator.pause();
        snapAnimator.setOnAnimationFrameHandler((object, animator) -> {
            Vector3f location = building.getLocalLocation();
            float animValue = animator.getUpdatedFloat();
            float x = (location.x * (1f - animValue) + snappingPoint.x * animValue);
            float y = (location.y * (1f - animValue) + snappingPoint.y * animValue);
            object.moveTo(x,y);
        });
    }

    public void update() {
        snapAnimator.update();
    }

    public void snapTo(IGameObject object) {
        Transforms buildingTransforms = building.getTransforms();
        IntersectionEvent event = VectorHelper.findSnapPlaceOnRBSurface(object.getRigidBody().asPolygonRB(), building.getLocalLocation());
        snappingPoint = event.intPoint;

        Vector3f normal = VectorHelper.getNormal(VectorHelper.sub(event.intLineB, event.intLineA));
        normal.normalize();

        float angle = (float)Math.atan2(normal.y, normal.x);
        float toDegrees = (float)Math.toDegrees(angle);
        buildingTransforms.rotateTo(0f,0f,toDegrees - 90);

        snappingPoint.x+= buildingTransforms.getScale().y * normal.x;
        snappingPoint.y+= buildingTransforms.getScale().y * normal.y;

        snapAnimator.startFrom(0f,true).resume();
    }

    public void snapTo(IGameObject object, float angleRad) {
        Transforms buildingTransforms = building.getTransforms();
        Transforms objectTransforms = object.getTransforms();

        float px = objectTransforms.getTranslation().x + objectTransforms.getScale().y * (float) Math.cos(angleRad);
        float py = buildingTransforms.getScale().y + objectTransforms.getTranslation().y + objectTransforms.getScale().y * (float) Math.sin(angleRad);
        snappingPoint = new Vector3f(px, py, 0f);
        snapAnimator.startFrom(0f,true).resume();
    }

    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        return true;
    }

    @Override
    public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
        building.setTouchDownOffset(gameObject.getTouchDownOffset());
        OnTouchListener.super.defaultOnTouchMove(building, pointer);
        return true;
    }

    @Override
    public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
        IGameObject planet = GameManager.getCurrentPlanet();
        snapTo(planet);
        return true;
    }

    public void startMoving() {
        building.getMovementPointer().shouldDraw(true);
    }

    public void stopMoving() {
        building.getMovementPointer().shouldDraw(false);
    }
}
