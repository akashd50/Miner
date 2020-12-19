package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.IntersectionEvent;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.helpers.touchListeners.GameBuildingMoveTouchListener;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class GameBuilding extends GameObjectWGL {
    private static final String BUILDING_MOVEMENT_TARGET = "building_movement_target";
    private FloatValueAnimator snapAnimator;
    private Vector3f snappingPoint, startingPoint;
    private GenericObject buildingMovementTarget;
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

        buildingMovementTarget = new GenericObject(BUILDING_MOVEMENT_TARGET, DrawableDef.create(DrawableDef.BUILDING_MOVE_TARGET));
        buildingMovementTarget.moveTo(this.getLocation().x, this.getLocation().y, this.getLocation().z + 1f);
        buildingMovementTarget.getDrawable().setOpacity(0.5f);
        buildingMovementTarget.setCircularRB();
        buildingMovementTarget.setOnTouchListener(new GameBuildingMoveTouchListener());

        this.addChild(BUILDING_MOVEMENT_TARGET, buildingMovementTarget);
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
    public void onTransformsChanged() {
        super.onTransformsChanged();
        if (buildingMovementTarget != null) {
            buildingMovementTarget.moveTo(this.getLocation().x, this.getLocation().y, this.getLocation().z + 1f);
        }
    }
}
