package com.greymatter.miner.game.objects;

import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;

import javax.vecmath.Vector3f;

import static com.greymatter.miner.game.GC.PLANET;

public class Scanner extends GameBuilding {
    private float _scannerRange, _scanningAngle;
    private Drawable rangeDrawable;
    public Scanner(Drawable drawable, Drawable rangeDrawable) {
        super(drawable.getId(), drawable);
        this.rangeDrawable = rangeDrawable;
    }
    public Scanner(String id, Drawable drawable) {
        super(id, drawable);
    }

    public boolean isResourceInRange(ResourceBlock resourceBlock) {
        Vector3f sub = VectorHelper.sub(resourceBlock.getLocation(), this.getLocation());
        float distance = VectorHelper.getLength(sub);
        if(distance < _scannerRange) {
            float angleBWResAndScanner = (float)Math.atan2(sub.y, sub.x);
            float leftEdge = (getCollider().getRotation().z) + 270 - _scanningAngle/2;
            float rightEdge = (getCollider().getRotation().z) + 270 + _scanningAngle/2;

            return angleBWResAndScanner > leftEdge && angleBWResAndScanner < rightEdge;
        }
        return false;
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
        Vector3f translation = getDrawable().getTransforms().getTranslation();
        rangeDrawable.getTransforms().translateTo(translation.x, translation.y);

        Vector3f sub = VectorHelper.sub(GameObjectsContainer.get(PLANET).getLocation(), this.getLocation());
        rangeDrawable.getTransforms().rotateTo(0f,0f,(float)Math.toDegrees(Math.atan2(sub.y, sub.x)));
    }

    public float getRange() {
        return _scannerRange;
    }

    public Scanner withRange(float scannerRange) {
        this._scannerRange = scannerRange;
        return this;
    }

    public float getAngle() {
        return _scanningAngle;
    }

    public Scanner withAngle(float scanningAngle) {
        this._scanningAngle = scanningAngle;
        return this;
    }
}
