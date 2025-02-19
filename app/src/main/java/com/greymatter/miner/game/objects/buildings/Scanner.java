package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.animators.impl.ScannerAnimationHandler;
import com.greymatter.miner.containers.AllResourcesContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import javax.vecmath.Vector3f;

public class Scanner extends GameBuilding {
    private float scannerRange, scanningAngle;
    private GenericObject rangeObject;
    private ResourceBlock currentlyTracking;

    public Scanner(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public Scanner(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public Scanner(String id) {
        super(id, DrawableDef.create(DrawableDef.SCANNER_1));
        initialize();
    }

    private void initialize() {
        this.setRangeObject(new GenericObject(DrawableDef.create(DrawableDef.PIE_GRADIENT_I))
                                    .moveTo(0,0f, 2f).scaleTo(getObjectLevel()*5f,getObjectLevel()*5f)
                                    .setAnimator(new FloatValueAnimator().withFPS(60)
                                                .setBounds(0f,1f)
                                                .setPerFrameIncrement(0.04f).toAndFro(true)
                                                .setOnAnimationFrameHandler((object, animator) -> {
                                                    object.getDrawable().asRadialGradient().setMidPoint(animator.getUpdatedFloat());
                                                })));

        scannerRange = rangeObject.getDrawable().getShape().getRadius() * rangeObject.getTransforms().getScale().x;
        scanningAngle = rangeObject.getDrawable().getShape().getInnerAngle();
        this.setAnimator(new BooleanAnimator().withFPS(10).setOnAnimationFrameHandler(new ScannerAnimationHandler()));
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        Vector3f sub = VectorHelper.sub(GameManager.getCurrentPlanet().getLocation(), this.getLocation());
        rangeObject.getTransforms().rotateTo(0f,0f,(float)Math.toDegrees(Math.atan2(sub.y, sub.x)));
    }

    @Override
    public Scanner upgrade() {
        super.upgrade();
        scaleTo(getObjectLevel()*5f,getObjectLevel()*5f);
        scannerRange = rangeObject.getDrawable().getShape().getRadius() * rangeObject.getTransforms().getScale().x;

        return this;
    }

    @Override
    public Scanner upgrade(int newLevel) {
        super.upgrade(newLevel);
        float newRad = getObjectLevel() * rangeObject.getDrawable().asRadialGradient().getRadius();
        rangeObject.getTransforms().scaleTo(rangeObject.getTransforms().getScale().x, newRad);

        return this;
    }

    public boolean isResourceInRange(ResourceBlock resourceBlock) {
        Vector3f sub = VectorHelper.sub(resourceBlock.getLocation(), this.getLocation());
        float distance = VectorHelper.getLength(sub);
        if(distance < scannerRange) {
            float rotationZ = rangeObject.getTransforms().getRotation().z;
            float angleBWResAndScanner = VectorHelper.angleBetweenDegrees(getDrawable(), resourceBlock.getDrawable());
            float leftEdge = (rotationZ) - scanningAngle /2;
            float rightEdge = (rotationZ) + scanningAngle /2;
            return angleBWResAndScanner > leftEdge && angleBWResAndScanner < rightEdge;
        }
        return false;
    }

    public ResourceBlock findClosestResource() {
        float prevLength = 999999;
        ResourceBlock toReturn = null;
        for(IGameObject resourceBlock : ContainerManager.getAllResourcesContainer().getAll()) {
            float dist = VectorHelper.getLength(VectorHelper.sub(resourceBlock.getLocation(), this.getLocation()));
            if(dist < prevLength) {
                prevLength = dist;
                toReturn = (ResourceBlock)resourceBlock;
            }
        }
        return toReturn;
    }

    public Scanner setRange(float scannerRange) {
        this.scannerRange = scannerRange;
        return this;
    }

    public Scanner setAngle(float scanningAngle) {
        this.scanningAngle = scanningAngle;
        return this;
    }

    public Scanner setRangeObject(IGameObject rangeObject) {
        this.rangeObject = rangeObject.asGenericObject();
        this.rangeObject.getTransforms().copyTranslationFromParent(true);
        this.addChild("SCANNER_RANGE", rangeObject);
        return this;
    }

    public Scanner setCurrentlyTracking(ResourceBlock currentlyTracking) {
        this.currentlyTracking = currentlyTracking;
        return this;
    }

    public ResourceBlock getCurrentlyTracking() {
        return currentlyTracking;
    }

    public float getRange() {
        return scannerRange;
    }

    public float getAngle() {
        return scanningAngle;
    }
}
