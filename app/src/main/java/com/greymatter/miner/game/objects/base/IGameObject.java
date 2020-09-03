package com.greymatter.miner.game.objects.base;

import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.ui.GameDialog;
import com.greymatter.miner.game.objects.ui.GameSignal;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObjectWGL;
import com.greymatter.miner.game.objects.GenericObject;
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
    String getId();

    IGameObject addChild(String id, IGameObject object);
    IGameObject getChild(String id);
    ArrayList<IGameObject> getChildrenWithTag(Tag tag);
    IGameObject setParent(IGameObject parent);
    IGameObject getParent();
    HashMapE<String, IGameObject> getChildren();

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

    boolean onTouchDownEvent(Vector2f pointer);
    boolean onTouchMoveEvent(Vector2f pointer);
    boolean onTouchUpEvent(Vector2f pointer);
    boolean isClicked(Vector2f pointer);
    Vector2f getTouchDownOffset();
    OnTouchListener getOnTouchListener();
    OnClickListener getOnClickListener();
    IGameObject setOnTouchListener(OnTouchListener onTouchListener);
    IGameObject setOnClickListener(OnClickListener onClickListener);
    IGameObject setTouchDownOffset(Vector2f offset);
    IGameObject setRB(RigidBody rigidBody);

    void onFrameUpdate();
    IGameObject addTag(Tag tag);
    IGameObject shouldDraw(boolean shouldDraw);
    IGameObject setAnimator(ValueAnimator valueAnimator);
    IGameObject setPolygonRB();
    IGameObject setGeneralRB();
    int getObjectLevel();
    int getNumTags();
    boolean shouldDraw();
    boolean shouldCheckClicks();
    boolean hasTag(Tag tag);
    Drawable getDrawable();
    RigidBody getRigidBody();
    ValueAnimator getAnimator();
    GameBuilding asGameBuilding();
    GameObjectWGL asGameObjectWGL();
    GameLight asGameLight();
    GenericObject asGenericObject();
    ResourceBlock asResourceBlock();
    IGameObject setDialog(GameDialog dialog);
    IGameObject setSignal(GameSignal signal);
    GameDialog getDialog();
    GameSignal getSignal();
}
