package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.Animated;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.RigidBody;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public interface IGameObject {
    ObjId getId();

    IGameObject addChild(ObjId id, IGameObject object);
    IGameObject getChild(ObjId id);
    ArrayList<IGameObject> getChildrenWithTag(Tag tag);
    IGameObject setParent(IGameObject parent);
    IGameObject getParent();
    HashMapE<ObjId, IGameObject> getChildren();

    Transforms getTransforms();
    IGameObject moveBy(Vector2f moveTo);
    IGameObject moveBy(float x, float y);
    IGameObject moveBy(float x, float y, float z);
    IGameObject moveBy(Vector3f moveTo);
    IGameObject moveTo(Vector2f moveTo);
    IGameObject moveTo(Vector3f moveTo);
    IGameObject moveTo(float x, float y);
    IGameObject moveTo(float x, float y, float z);
    IGameObject scaleTo(float x, float y);
    IGameObject scaleBy(float x, float y);
    IGameObject translationFromParent(boolean val);
    IGameObject rotationFromParent(boolean val);
    IGameObject scaleFromParent(boolean val);
    Vector3f getLocation();

    void onFrameUpdate();
    void onDrawFrame();
    IGameObject setOnAnimationFrameHandler(OnAnimationFrameHandler handler);
    IGameObject addTag(Tag tag);
    IGameObject shouldDraw(boolean shouldDraw);
    IGameObject setAnimator(ValueAnimator valueAnimator);
    IGameObject setOnTouchListener(OnTouchListener onTouchListener);
    IGameObject setOnClickListener(OnClickListener onClickListener);
    IGameObject setRB(RigidBody rigidBody);
    IGameObject setPolygonRB();
    IGameObject setGeneralRB();
    IGameObject setPolygonTC();
    int getObjectLevel();
    int getNumTags();
    boolean shouldDraw();
    boolean hasTag(Tag tag);
    boolean onTouchDownEvent(Vector2f pointer);
    boolean onTouchMoveEvent(Vector2f pointer);
    boolean onTouchUpEvent(Vector2f pointer);
    Drawable getDrawable();
    RigidBody getRigidBody();
    ValueAnimator getAnimator();
    GameBuilding asGameBuilding();
    GameObjectWGL asGameObjectWGL();
    GameLight asGameLight();
    Animated asAnimatedObject();
    ResourceBlock asResourceBlock();
}
