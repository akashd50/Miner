package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.OnCollisionListener;
import java.util.HashMap;
import javax.vecmath.Vector3f;

public abstract class RigidBody {
    private ObjId id;
    private Vector3f upVector;
    private Transforms transforms;
    private RBProps rbProps;
    private boolean isStaticObject, dynamicallyUpdated;
    private OnCollisionListener onCollisionListener;
    private HashMap<String, CollisionEvent> lastCollisionEvents;
    public RigidBody(ObjId id) {
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
        transforms.setLinkedRigidBody(this);
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

    public abstract void updateParamsOverride();

    public boolean isStaticObject() {
        return isStaticObject;
    }

    public boolean isUpdatedPerMovement() {
        return dynamicallyUpdated;
    }

//    public Vector3f getTranslation() {
//        return transforms.getTranslation();
//    }
//
//    public Vector3f getRotation() {
//        return transforms.getRotation();
//    }
//
//    public Vector3f getScale() {
//        return transforms.getScale();
//    }

    public Transforms getTransforms() {
        return transforms;
    }

    public RBProps getRBProps() {
        return rbProps;
    }

    public Vector3f getUpVector() {
        return upVector;
    }

    public ObjId getId() {
        return this.id;
    }

    public CircularRB asCircularRB() {
        return (CircularRB)this;
    }

    public PolygonRB asPolygonRB() {
        return (PolygonRB) this;
    }
}
