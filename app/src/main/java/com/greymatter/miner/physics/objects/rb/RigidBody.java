package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.OnCollisionListener;
import java.util.HashMap;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class RigidBody {
    private String id;
    private Vector3f upVector;
    private Transforms transforms;
    private RBProps rbProps;
    private boolean isStaticObject, dynamicallyUpdated;
    private OnCollisionListener onCollisionListener;
    private HashMap<String, CollisionEvent> lastCollisionEvents;
    public RigidBody(String id) {
        this.id = id;
        this.upVector = new Vector3f(0f,1f,0f);
        this.lastCollisionEvents = new HashMap<>();
        this.rbProps = new RBProps();
        this.dynamicallyUpdated = true;
    }

    public void update() {
        rbProps.update();
        this.transforms.translateBy(rbProps.getVelocity());
        this.transforms.rotateBy(0f,0f,rbProps.getAngularVelocity());
    }

    public Vector3f getUpVectorFromRotation() {
        upVector = VectorHelper.rotateAroundZ(new Vector3f(0f,1f,0f), (float)Math.toRadians(transforms.getRotation().z - 90));
        return upVector;
    }

    public void addOrUpdateCollisionEvent(CollisionEvent collisionEvent) {
        if(lastCollisionEvents.containsKey(collisionEvent.getAgainstObject().toString())) {
            lastCollisionEvents.replace(collisionEvent.getAgainstObject().toString(), collisionEvent);
        }else{
            lastCollisionEvents.put(collisionEvent.getAgainstObject().toString(), collisionEvent);
        }
    }
    public void addOrUpdateAgainstCollisionEvent(CollisionEvent collisionEvent) {
        if(lastCollisionEvents.containsKey(collisionEvent.getLinkedObject().toString())) {
            lastCollisionEvents.replace(collisionEvent.getLinkedObject().toString(), collisionEvent);
        }else{
            lastCollisionEvents.put(collisionEvent.getLinkedObject().toString(), collisionEvent);
        }
    }


    public CollisionEvent getLastCollisionEvent(RigidBody against) {
        return lastCollisionEvents.get(against.toString());
    }

    public RigidBody setUpVector(Vector3f vector) {
        this.upVector = new Vector3f(vector);
        return this;
    }

    public RigidBody isStaticObject(boolean isStatic) {
        this.isStaticObject = isStatic;
        return this;
    }

    public RigidBody setTransforms(Transforms transforms) {
        this.transforms = transforms;
        return this;
    }

    public OnCollisionListener getCollisionListener() {
        return onCollisionListener;
    }

    public RigidBody setCollisionListener(OnCollisionListener collisionListener) {
        this.onCollisionListener = collisionListener;
        return this;
    }

    public RigidBody updateTransformationsPerMovement(boolean dynamicallyUpdated) {
        this.dynamicallyUpdated = dynamicallyUpdated;
        return this;
    }

    public void onTransformsChanged() {
        if(dynamicallyUpdated) {
            this.updateParamsOverride();
        }
    }

    protected Vector2f revertAllTransformations(Vector2f normalizedTouchPoint, Transforms transforms, Transforms prevTransforms) {
        boolean shouldRecurse = transforms.isCopyTranslationFromParent()
                || transforms.isCopyRotationFromParent()
                || transforms.isCopyScaleFromParent();

        if(transforms.getParent() != null && shouldRecurse){
            revertAllTransformations(normalizedTouchPoint, transforms.getParent(), transforms);
        }

        if(prevTransforms.isCopyTranslationFromParent()) {
            VectorHelper.revertTranslation(normalizedTouchPoint, transforms.getTranslation());
        }

        if(prevTransforms.isCopyRotationFromParent()) {
            VectorHelper.revertRotation(normalizedTouchPoint, transforms.getRotation());
        }

        if(prevTransforms.isCopyScaleFromParent()) {
            VectorHelper.revertScale(normalizedTouchPoint, transforms.getScale());
        }
        return normalizedTouchPoint;
    }

    public abstract void updateParamsOverride();
    public abstract boolean isClicked(Vector2f touchPoint);

    public boolean isStaticObject() {
        return isStaticObject;
    }

    public boolean isDynamicallyUpdated() {
        return dynamicallyUpdated;
    }

    public Transforms getTransforms() {
        return transforms;
    }

    public RBProps getRBProps() {
        return rbProps;
    }

    public Vector3f getUpVector() {
        return upVector;
    }

    public String getId() {
        return this.id;
    }

    public CircularRB asCircularRB() {
        return (CircularRB)this;
    }

    public PolygonRB asPolygonRB() {
        return (PolygonRB) this;
    }
}
