package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.animators.FloatValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import javax.vecmath.Vector3f;

public class Scanner extends GameBuilding {
    private float _scannerRange, _scanningAngle;
    private RadialGradient rangeDrawable;
    private FloatValueAnimator rangeDrawableAnimator;
    private ResourceBlock currentlyTracking;
    public Scanner(Drawable drawable) {
        super(drawable.getId(), drawable);
    }

    public Scanner(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    @Override
    public void runPostInitialization() {
        rangeDrawable = getLinkedGameObject(ObjId.SCANNER_RANGE).getDrawable().asRadialGradient();
        rangeDrawable.getTransforms().setParent(this.getTransforms()).copyTranslationFromParent(true);
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();

        Vector3f sub = VectorHelper.sub(GameObjectsContainer.get(ObjId.PLANET).getLocation(), this.getLocation());
        rangeDrawable.getTransforms().rotateTo(0f,0f,(float)Math.toDegrees(Math.atan2(sub.y, sub.x)));

        rangeDrawable.setMidPoint(rangeDrawableAnimator.update().getUpdatedFloat());

        if(getValueAnimator().update().getUpdatedBoolean()) {
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
        float newRad = getObjectLevel() * rangeDrawable.getRadius();
        rangeDrawable.getTransforms().scaleTo(rangeDrawable.getTransforms().getScale().x, newRad);

        return this;
    }

    @Override
    public Scanner upgrade(int newLevel) {
        super.upgrade(newLevel);
        float newRad = getObjectLevel() * rangeDrawable.getRadius();
        rangeDrawable.getTransforms().scaleTo(rangeDrawable.getTransforms().getScale().x, newRad);

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

    public Scanner setRangeDrawable(GameObject rangeDrawable) {
        addLinkedGameObject(ObjId.SCANNER_RANGE, rangeDrawable);
        return this;
    }

    public Scanner setRangeDrawableAnimator(FloatValueAnimator rangeDrawableAnimator) {
        this.rangeDrawableAnimator = rangeDrawableAnimator;
        return this;
    }

    public float getRange() {
        return _scannerRange;
    }

    public float getAngle() {
        return _scanningAngle;
    }
}
