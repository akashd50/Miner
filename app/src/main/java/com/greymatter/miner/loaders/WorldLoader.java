package com.greymatter.miner.loaders;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.game.objects.PlayerCharacter;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.MainBase;
import com.greymatter.miner.game.objects.buildings.Planet;
import com.greymatter.miner.game.objects.buildings.Scanner;
import com.greymatter.miner.game.objects.ui.GameDialog;
import com.greymatter.miner.game.objects.ui.GameSignal;
import com.greymatter.miner.helpers.touchListeners.BuildingModeTouchListener;
import com.greymatter.miner.helpers.GeneralCollisionListener;
import com.greymatter.miner.helpers.touchListeners.GeneralTouchListener;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

import javax.vecmath.Vector2f;

public class WorldLoader extends Loader {
    public void load() {
        //GameObjectsContainer.add();

        GameObjectsContainer.add(new Planet(DrawableDef.create(ObjId.PLANET))
                .scaleTo(120f,120f).moveTo(0f,-120.5f, ZHelper.BACK));

        GameObjectsContainer.add(new MainBase(DrawableDef.create(ObjId.MAIN_BASE))
                .scaleTo(4f,4f).moveTo(0f,0f,ZHelper.FRONT_MID));
                //.setOnTouchListener(new BuildingModeTouchListener()));

        GameObjectsContainer.add(new PlayerCharacter(DrawableDef.create(ObjId.MAIN_CHARACTER))
                .scaleTo(0.6f,0.6f).moveBy(-0.5f,0f,ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener())
                .setOnClickListener(object -> {
                    if(!object.getChild(ObjId.OBJECT_DIALOG).shouldDraw()) {
                        ((GameDialog) object.getChild(ObjId.OBJECT_DIALOG)).show();
                    }else{
                        ((GameDialog) object.getChild(ObjId.OBJECT_DIALOG)).hide();
                    }
                    return true;
                }).addChild(ObjId.OBJECT_DIALOG, new GameDialog().setButtonIClickListener(object -> {
                    System.out.println("NOT I - Button Click -> " + object);
                    return true;
                }).setButtonIIClickListener(object -> {
                    System.out.println("NOT II - Button Click -> " + object);
                    return true;
                })));

        new GameLight(new Obj(ObjId.MAIN_BASE_LIGHT_I))
                .setRadius(1f)
                .setColor(1f,0f,0f,1f)
                .setInnerCutoff(0.02f).setOuterCutoff(0.8f)
                .attachTo(GameObjectsContainer.get(ObjId.MAIN_BASE).asGameObjectWGL())
                .moveTo(new Vector2f(0.12f,-0.06f))
                .setAnimator(new FloatValueAnimator().setPerFrameIncrement(0.05f).toAndFro(true).withFPS(60)
                .setOnAnimationFrameHandler((object, animator) -> {
                    object.asGameLight().setIntensity(animator.getUpdatedFloat());
                }));

        GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getTransforms().rotateTo(0f,0f,90);

        //-------------------------------------
        GameObjectsContainer.add(new GenericObject(DrawableDef.create(ObjId.TEST_OBJ_I))
                .addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.5f,0.5f).moveBy(-4f,2f,ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new GenericObject(DrawableDef.create(ObjId.TEST_OBJ_II))
                .addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.5f,0.5f).moveBy(-2f,4f,ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener()));
        //-------------------------------------

        GameObjectsContainer.add(new Scanner(DrawableDef.create(ObjId.SCANNER_I))
                .addTag(Tag.PLACABLE_GAME_BUILDING).addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,ZHelper.FRONT)
                .setOnTouchListener(new BuildingModeTouchListener())
                .setOnClickListener(object -> {
                    if(!object.getChild(ObjId.OBJECT_SIGNAL).shouldDraw()) {
                        ((GameSignal) object.getChild(ObjId.OBJECT_SIGNAL)).show();
                    }else{
                        ((GameSignal) object.getChild(ObjId.OBJECT_SIGNAL)).hide();
                    }
                    return true;
                }).addChild(ObjId.OBJECT_DIALOG, new GameDialog().setButtonIClickListener(object -> {
                    System.out.println("NOT I - Button Click -> " + object);
                    return true;
                }).setButtonIIClickListener(object -> {
                    System.out.println("NOT II - Button Click -> " + object);
                    return true;
                }))
                .addChild(ObjId.OBJECT_SIGNAL, new GameSignal().setOnClickListener(object -> {
                    return true;
                })));

        GameObjectsContainer.add(new GenericObject(DrawableDef.create(ObjId.TREE_I))
                .scaleTo(1f,1.5f).moveTo(0f,0.5f, ZHelper.MID_BACK));
//
//        GameObjectsContainer.add(new Animated(DrawableDef.create(ObjId.PLANET_TREE_LAYER))
//                .scaleTo(119f,119f).moveTo(0f,-120.5f, -1f));
        GameObjectsContainer.add(new GenericObject(DrawableDef.create(ObjId.PLANET_GRASS_LAYER))
                .scaleTo(119.65f,119.65f).moveTo(0f,-120.5f, ZHelper.OVER_FRONT));

        updateContainer();
        updatePhysicsProperties();
    }

    public void updateContainer() {
        //        IGameObject mainCharacter = GameObjectsContainer.get(ObjId.MAIN_CHARACTER);
//        GameObjectsContainer.add(new InteractiveObject(new Line(ObjId.TEST_LINE)
//                            .setShader(ShaderContainer.get(ShaderDef.LINE_SHADER))
//                            .addVertices(mainCharacter.getRigidBody().asPolygonRB().getTransformedVertices())
//                            .build()));

        //ToDrawContainer.add(GameObjectsContainer.get(ObjId.TEST_LINE));
        //ToDrawContainer.add(GameObjectsContainer.get(ObjId.ATMOSPHERE));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.MAIN_CHARACTER));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TEST_OBJ_I));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TEST_OBJ_II));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET_GRASS_LAYER));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TREE_I));
        //ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET_TREE_LAYER));
    }

    public void updatePhysicsProperties() {
        GameObjectsContainer.get(ObjId.MAIN_BASE).setPolygonRB();

        GameObjectsContainer.get(ObjId.PLANET).setPolygonRB().getRigidBody().isStaticObject(true)
                .getRBProps().setMass(1000000f).setRestitution(0.3f);

        GameObjectsContainer.get(ObjId.MAIN_CHARACTER).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0.1f);

        GameObjectsContainer.get(ObjId.TEST_OBJ_I).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0f);

        GameObjectsContainer.get(ObjId.TEST_OBJ_II).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0f);

        IGameObject sampleScanner = GameObjectsContainer.get(ObjId.SCANNER_I);
        sampleScanner.setRB(new PolygonRB(sampleScanner.getId(), sampleScanner.getDrawable().getOptimizedOOMesh(0.1f)));
        sampleScanner.setPolygonTC();
        sampleScanner.getRigidBody().isStaticObject(false).getRBProps().setMass(1.0f).setRestitution(0.5f);

        //assign listeners
        GameObjectsContainer.getAll().forEach(iGameObject -> {
            if(iGameObject.hasTag(Tag.DYNAMIC_PHYSICS_OBJECT)) {
                iGameObject.getRigidBody().setCollisionListener(new GeneralCollisionListener());
            }
        });

        ToDrawContainer.getAll().forEach(iGameObject -> {
            if(iGameObject.hasTag(Tag.STATIC_PHYSICS_OBJECT) || iGameObject.hasTag(Tag.DYNAMIC_PHYSICS_OBJECT)) {
                CollisionSystemContainer.add(iGameObject.getRigidBody());
            }
        });
    }
}
