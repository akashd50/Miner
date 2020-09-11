package com.greymatter.miner.game.objects;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class GamePad extends GameObject implements OnTouchListener, OnAnimationFrameHandler {
    private GenericObject padBackground;
    private float movementRadius;
    private Vector2f defaultOnScreenLocation;
    private Vector2f factor;
    public GamePad(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    public GamePad(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {
        movementRadius = 0.3f;
        factor = new Vector2f();
        defaultOnScreenLocation = new Vector2f(-1.2f,-0.5f);

        this.addTag(Tag.UI_OBJECT);
        this.moveTo(defaultOnScreenLocation.x, defaultOnScreenLocation.y, ZHelper.OVER_FRONT+2f).scaleTo(0.09f,0.09f);

        this.padBackground = new GenericObject(DrawableDef.create(DrawableDef.GAME_PAD_BACKGROUND));
        this.padBackground.moveTo(defaultOnScreenLocation.x, defaultOnScreenLocation.y,ZHelper.OVER_FRONT+1f)
                .scaleTo(movementRadius,movementRadius);

        this.addChild(padBackground.getId(), padBackground);
        this.setOnTouchListener(this);
        this.setAnimator(new BooleanAnimator().withFPS(10).setOnAnimationFrameHandler(this));
    }

    public Vector2f getFactor() {
        return factor;
    }

    public GamePad setDefaultOnScreenLocation(float x, float y) {
        this.defaultOnScreenLocation.set(x,y);
        this.moveTo(defaultOnScreenLocation);
        padBackground.moveTo(defaultOnScreenLocation);
        return this;
    }

    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        getAnimator().resume();
        return true;
    }

    @Override
    public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
        float distance = (float)VectorHelper.getDistanceWithSQRT(defaultOnScreenLocation, pointer);
        if(distance<=movementRadius) {
            OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
        } else {
            float angleToPoint = VectorHelper.angleBetweenRad(defaultOnScreenLocation, pointer);
            float x = defaultOnScreenLocation.x + movementRadius * (float)Math.cos(angleToPoint);
            float y = defaultOnScreenLocation.y + movementRadius * (float)Math.sin(angleToPoint);
            moveTo(x,y);
        }
        factor.x = (defaultOnScreenLocation.x - getLocation().x)/movementRadius;
        factor.y = (defaultOnScreenLocation.y - getLocation().y)/movementRadius;
        return true;
    }

    @Override
    public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
        moveTo(defaultOnScreenLocation);
        factor.set(0f,0f);
        getAnimator().pause();
        return true;
    }

    @Override
    public void onAnimationFrame(GameObject object, ValueAnimator animator) {
        Vector3f left = VectorHelper.getNormal(AppServices.getGameCamera().getUpVector());
        GameObjectsContainer.get("MAIN_CHARACTER").getRigidBody().getRBProps()
                .updateVelocity(VectorHelper.multiply(left, getFactor().x*0.01f));
    }
}
