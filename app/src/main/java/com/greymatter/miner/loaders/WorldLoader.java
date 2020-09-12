package com.greymatter.miner.loaders;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.UIToDrawContainer;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.manager.MinerManager;
import com.greymatter.miner.game.objects.GamePad;
import com.greymatter.miner.game.objects.PlayerCharacter;
import com.greymatter.miner.game.objects.buildings.GasPump;
import com.greymatter.miner.game.objects.buildings.Mine;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.MainBase;
import com.greymatter.miner.game.objects.Planet;
import com.greymatter.miner.game.objects.buildings.Scanner;
import com.greymatter.miner.game.objects.ui.GameDialog;
import com.greymatter.miner.game.objects.ui.GameSignal;
import com.greymatter.miner.helpers.touchListeners.BuildingModeTouchListener;
import com.greymatter.miner.helpers.GeneralCollisionListener;
import com.greymatter.miner.helpers.touchListeners.GeneralTouchListener;
import com.greymatter.miner.mainui.LayoutHelper;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

import javax.vecmath.Vector2f;

public class WorldLoader extends Loader {
    public void load() {
        GameObjectsContainer.add(new Planet(DrawableDef.create(DrawableDef.PLANET_1))
                .scaleTo(300f,300f).moveTo(0f,-300.5f, ZHelper.BACK));

        GameObjectsContainer.add(new GenericObject(DrawableDef.create(DrawableDef.TREE_I))
                .scaleTo(1f,1.5f).moveTo(0f,0.5f, ZHelper.MID_BACK));
//
//        GameObjectsContainer.add(new Animated(DrawableDef.create(DrawableDef.PLANET_TREE_LAYER))
//                .scaleTo(119f,119f).moveTo(0f,-120.5f, -1f));
        GameObjectsContainer.add(new GenericObject(DrawableDef.create(DrawableDef.PLANET_GRASS_LAYER))
                .scaleTo(299f,299f).moveTo(0f,-300.5f, ZHelper.OVER_FRONT));

        GameObjectsContainer.add(new MainBase(DrawableDef.create(DrawableDef.MAIN_BASE))
                .scaleTo(4f,4f).moveTo(-7f,5f,ZHelper.FRONT_MID));

        GameObjectsContainer.add(new PlayerCharacter(DrawableDef.create(DrawableDef.MAIN_CHARACTER))
                .scaleTo(0.6f,0.6f).moveBy(-0.5f,0f,ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener())
                .setOnClickListener(object -> {
                    if(!object.getDialog().shouldDraw()) {
                        object.getDialog().show();
                    }else{
                        object.getDialog().hide();
                    }
                    return true;
                }).setDialog(new GameDialog().setButtonIClickListener(object -> {
                                            System.out.println("NOT I - Button Click -> " + object);
                                            return true;
                                        }).setButtonIIClickListener(object -> {
                                            System.out.println("NOT II - Button Click -> " + object);
                                            return true;
                                        })));

        new GameLight(new Obj("MAIN_BASE_LIGHT_I"))
                .setRadius(1f)
                .setColor(1f,0f,0f,1f)
                .setInnerCutoff(0.02f).setOuterCutoff(0.8f)
                .attachTo(GameObjectsContainer.get(DrawableDef.MAIN_BASE.name()).asGameObjectWGL())
                .moveTo(new Vector2f(0.12f,-0.06f))
                .setAnimator(new FloatValueAnimator().setPerFrameIncrement(0.05f).toAndFro(true).withFPS(60)
                .setOnAnimationFrameHandler((object, animator) -> {
                    object.asGameLight().setIntensity(animator.getUpdatedFloat());
                }));

        GameObjectsContainer.get(DrawableDef.MAIN_CHARACTER.name()).getTransforms().rotateTo(0f,0f,90);

        //-------------------------------------
        GameObjectsContainer.add(new GenericObject(DrawableDef.create(DrawableDef.TEST_OBJ_I))
                .addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.5f,0.5f).moveBy(-4f,2f,ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new GenericObject(DrawableDef.create(DrawableDef.TEST_OBJ_II))
                .addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.5f,0.5f).moveBy(-2f,4f,ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener()));
        //-------------------------------------

        GameObjectsContainer.add(new Scanner(DrawableDef.create(DrawableDef.SCANNER_1))
                .addTag(Tag.PLACABLE_GAME_BUILDING).addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,ZHelper.FRONT)
                .setOnTouchListener(new BuildingModeTouchListener())
                .setOnClickListener(object -> {
                    if(!object.getDialog().shouldDraw()) {
                        object.getDialog().show();
                    }else{
                        object.getDialog().hide();
                    }
                    return true;
                }).setDialog(new GameDialog().setButtonIClickListener(object -> {
                                            System.out.println("NOT I - Button Click -> " + object);
                                            return true;
                                        }).setButtonIIClickListener(object -> {
                                            System.out.println("NOT II - Button Click -> " + object);
                                            return true;
                                        }))
                .setSignal((GameSignal) new GameSignal().setOnClickListener(object -> {
                   LayoutHelper.showDialog(LayoutHelper.getScannerOnResourceFindDialog((Scanner)GameObjectsContainer.get(DrawableDef.SCANNER_1.name()),
                            ((Scanner)GameObjectsContainer.get(DrawableDef.SCANNER_1.name())).getCurrentlyTracking()));
                    return true;
                })));

        GameObjectsContainer.add(MinerManager.getNextMinerId(), new GasPump(DrawableDef.create(DrawableDef.MINER_1))
                .scaleTo(1.5f,1f).moveTo(-1f,2f, ZHelper.FRONT-1)
                .addTag(Tag.PLACABLE_GAME_BUILDING));

        GameObjectsContainer.add("MINE",new Mine(DrawableDef.create(DrawableDef.MINE_1)));

        updateContainer();
        updatePhysicsProperties();
    }

    public void updateContainer() {
//        IGameObject mainCharacter = GameObjectsContainer.get(DrawableDef.MAIN_CHARACTER);
//        GameObjectsContainer.add(new InteractiveObject(new Line(DrawableDef.TEST_LINE)
//                            .setShader(ShaderContainer.get(ShaderDef.LINE_SHADER))
//                            .addVertices(mainCharacter.getRigidBody().asPolygonRB().getTransformedVertices())
//                            .build()));
        //ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.TEST_LINE));

        ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.PLANET_1.name()));
        ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.MAIN_BASE.name()));
        ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.MAIN_CHARACTER.name()));
        ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.TEST_OBJ_I.name()));
        ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.TEST_OBJ_II.name()));
        ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.PLANET_GRASS_LAYER.name()));
        ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.TREE_I.name()));

        GameObjectsContainer.get(DrawableDef.MAIN_BASE.name()).asGameBuilding().snapTo(GameObjectsContainer.get(GameManager.getCurrentPlanet()));
        GameObjectsContainer.get("MINE").asGameBuilding().snapTo(GameObjectsContainer.get(GameManager.getCurrentPlanet()));

        ToDrawContainer.add(GameObjectsContainer.get("MINE"));
    }

    public void updatePhysicsProperties() {
        GameObjectsContainer.get(DrawableDef.MAIN_BASE.name()).setPolygonRB();

        GameObjectsContainer.get(DrawableDef.PLANET_1.name()).setPolygonRB().getRigidBody().isStaticObject(true)
                .getRBProps().setMass(1000000f).setRestitution(0.3f);

        GameObjectsContainer.get(DrawableDef.MAIN_CHARACTER.name()).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0.1f);

        GameObjectsContainer.get(DrawableDef.TEST_OBJ_I.name()).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0f);

        GameObjectsContainer.get(DrawableDef.TEST_OBJ_II.name()).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0f);

        GameObjectsContainer.get(DrawableDef.MINER_1.name()).setPolygonRB().getRigidBody().isStaticObject(false)
                .setCollisionListener(new GeneralCollisionListener())
                .getRBProps().setMass(1.0f).setRestitution(0.1f);

        IGameObject sampleScanner = GameObjectsContainer.get(DrawableDef.SCANNER_1.name());
        sampleScanner.setRB(new PolygonRB(sampleScanner.getId(), sampleScanner.getDrawable().getOptimizedOOMesh(0.1f)));
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

    @Override
    public void onPostSurfaceInitializationHelper() {

    }
}
