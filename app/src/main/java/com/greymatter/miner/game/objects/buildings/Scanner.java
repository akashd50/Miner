package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.Animated;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import javax.vecmath.Vector3f;

public class Scanner extends GameBuilding {
    private float _scannerRange, _scanningAngle;
    private Animated rangeObject;
    private ResourceBlock currentlyTracking;

    public Scanner(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Scanner(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void onFrameUpdate() {
        Vector3f sub = VectorHelper.sub(GameObjectsContainer.get(ObjId.PLANET).getLocation(), this.getLocation());
        rangeObject.getTransforms().rotateTo(0f,0f,(float)Math.toDegrees(Math.atan2(sub.y, sub.x)));

        super.onFrameUpdate();

        if(getAnimator().update().getUpdatedBoolean()) {
            currentlyTracking = currentlyTracking==null? findClosestResource() : currentlyTracking;
            if(!isResourceInRange(currentlyTracking)) {
                Vector3f dir = VectorHelper.sub(currentlyTracking.getLocation(), this.getLocation());
                dir.normalize();
                dir.z = 0f;
                this.getRigidBody().getRBProps().updateVelocity(VectorHelper.multiply(dir, 0.02f));
            }
        }
    }

    @Override
    public Scanner upgrade() {
        super.upgrade();
        float newRad = getObjectLevel() * rangeObject.getDrawable().asRadialGradient().getRadius();
        rangeObject.getTransforms().scaleTo(rangeObject.getTransforms().getScale().x, newRad);

        return this;
    }

    @Override
    public Scanner upgrade(int newLevel) {
        super.upgrade(newLevel);
        float newRad = getObjectLevel() * rangeObject.getDrawable().asRadialGradient().getRadius();
        rangeObject.getTransforms().scaleTo(rangeObject.getTransforms().getScale().x, newRad);

        return this;
    }


    private boolean isResourceInRange(ResourceBlock resourceBlock) {
        Vector3f sub = VectorHelper.sub(resourceBlock.getLocation(), this.getLocation());
        float distance = VectorHelper.getLength(sub);
        if(distance < _scannerRange) {
            float angleBWResAndScanner = (float)Math.atan2(sub.y, sub.x);
            float leftEdge = (getRigidBody().getRotation().z) + 270 - _scanningAngle/2;
            float rightEdge = (getRigidBody().getRotation().z) + 270 + _scanningAngle/2;

            return angleBWResAndScanner > leftEdge && angleBWResAndScanner < rightEdge;
        }
        return false;
    }

    private ResourceBlock findClosestResource() {
        float prevLength = 999999;
        ResourceBlock toReturn = null;
        for(ResourceBlock resourceBlock : ActiveResourcesContainer.getAll()) {
            float dist = VectorHelper.getLength(VectorHelper.sub(resourceBlock.getLocation(), this.getLocation()));
            if(dist < prevLength) {
                prevLength = dist;
                toReturn = resourceBlock;
            }
        }
        return toReturn;
    }

    public Scanner setRange(float scannerRange) {
        this._scannerRange = scannerRange;
        return this;
    }

    public Scanner setAngle(float scanningAngle) {
        this._scanningAngle = scanningAngle;
        return this;
    }

    public Scanner setRangeObject(GameObject rangeObject) {
        this.rangeObject = rangeObject.asAnimatedObject();
        this.rangeObject.getTransforms().setParent(this.getTransforms()).copyTranslationFromParent(true);
        this.addLinkedGameObject(ObjId.SCANNER_RANGE, rangeObject);
        return this;
    }

    public float getRange() {
        return _scannerRange;
    }

    public float getAngle() {
        return _scanningAngle;
    }
}
