package com.greymatter.miner.loaders;

import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.game.GameConstants;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.manager.MinerManager;
import com.greymatter.miner.game.objects.PlayerCharacter;
import com.greymatter.miner.game.objects.buildings.OilDrill;
import com.greymatter.miner.game.objects.buildings.Mine;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.helpers.clicklisteners.SimpleDialogClickListener;
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
import static com.greymatter.miner.game.GameConstants.*;
import com.greymatter.miner.helpers.touchListeners.BuildingModeTouchListener;
import com.greymatter.miner.helpers.GeneralCollisionListener;
import com.greymatter.miner.helpers.touchListeners.GeneralTouchListener;
import com.greymatter.miner.mainui.LayoutHelper;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.opengl.objects.renderers.LineRenderer;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class WorldLoader extends Loader {
    public void load() {
        GameObjectsContainer.add(new Planet(PLANET_1)
                .scaleTo(300f,300f).moveTo(0f,-300.5f, ZHelper.BACK));

        GameObjectsContainer.add(new GenericObject(DrawableDef.create(DrawableDef.TREE_I))
                .scaleTo(1f,1.5f).moveTo(0f,0.5f, ZHelper.MID_BACK));
//
//        GameObjectsContainer.add(new Animated(DrawableDef.create(DrawableDef.PLANET_TREE_LAYER))
//                .scaleTo(119f,119f).moveTo(0f,-120.5f, -1f));

        GameObjectsContainer.add(new MainBase(MAIN_BASE_1));
        GameObjectsContainer.get(MAIN_BASE_1).scaleTo(4f,4f).moveTo(-7f,5f,ZHelper.FRONT_MID);

        GameObjectsContainer.add(new PlayerCharacter(MAIN_CHARACTER_1));
        GameObjectsContainer.get(MAIN_CHARACTER_1)
                .scaleTo(0.6f,0.6f)
                .moveBy(-0.5f,0f,ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener())
                .setOnClickListener(new SimpleDialogClickListener())
                .setDialog(new GameDialog().setButtonIClickListener(object -> {
                                            System.out.println("NOT I - Button Click -> " + object);
                                            return true;
                                        }).setButtonIIClickListener(object -> {
                                            System.out.println("NOT II - Button Click -> " + object);
                                            return true;
                                        }));
        GameObjectsContainer.get(MAIN_CHARACTER_1).getTransforms().rotateTo(0f,0f,90);

        //-------------------------------------
        GameObjectsContainer.add(new GenericObject(TEST_OBJ_1, DrawableDef.create(DrawableDef.TEST_OBJ_I))
                .addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.5f,0.5f).moveBy(-4f,2f, ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new GenericObject(TEST_OBJ_2, DrawableDef.create(DrawableDef.TEST_OBJ_II))
                .addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.5f,0.5f).moveBy(-2f,4f, ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener()));
        //-------------------------------------

        GameObjectsContainer.add(new Scanner(GameConstants.SCANNER_1));
        GameObjectsContainer.get(SCANNER_1).addTag(Tag.PLACABLE_GAME_BUILDING).addTag(Tag.DYNAMIC_PHYSICS_OBJECT);
        GameObjectsContainer.get(SCANNER_1).scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,ZHelper.FRONT);
        GameObjectsContainer.get(SCANNER_1).setOnTouchListener(new BuildingModeTouchListener());
        GameObjectsContainer.get(SCANNER_1).setOnClickListener(new SimpleDialogClickListener());
        GameObjectsContainer.get(SCANNER_1).setDialog(new GameDialog().setButtonIClickListener(object -> {
                                            System.out.println("NOT I - Button Click -> " + object);
                                            return true;
                                        }).setButtonIIClickListener(object -> {
                                            System.out.println("NOT II - Button Click -> " + object);
                                            return true;
                                        }));
        GameObjectsContainer.get(SCANNER_1).setSignal((GameSignal)
                new GameSignal().setOnClickListener(object -> {
                    Scanner scanner = (Scanner)GameObjectsContainer.get(GameConstants.SCANNER_1);
                    LayoutHelper.showDialog(LayoutHelper.getScannerOnResourceFindDialog(scanner, (scanner.getCurrentlyTracking())));
                    return true;
                }));

        GameObjectsContainer.add(new OilDrill(OIL_DRILL_1));
        GameObjectsContainer.get(OIL_DRILL_1).scaleTo(4f,3f)
                .moveTo(-1f,2f, ZHelper.FRONT-1)
                .addTag(Tag.PLACABLE_GAME_BUILDING);

        GameObjectsContainer.add(new Mine(MINE_1));

        GameObjectsContainer.add("POINT",new GenericObject(DrawableDef.create(DrawableDef.COAL_BLOCK_I))
                .scaleTo(0.2f,0.2f).moveTo(0f,0f,ZHelper.OVER_FRONT));

        GameObjectsContainer.add("LINE", new GenericObject(new Line("LINE").addVertex(new Vector3f(-10f,5f,0f))
                .addVertex(new Vector3f(10f,5f,0f)).build()).moveTo(0f,0f, ZHelper.OVER_FRONT));
        //GameObjectsContainer.get("LINE").getDrawable().asLine());

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

        ToDrawContainer.add(GameObjectsContainer.get(PLANET_1));
        ToDrawContainer.add(GameObjectsContainer.get(MAIN_BASE_1));
        ToDrawContainer.add(GameObjectsContainer.get(MAIN_CHARACTER_1));
        ToDrawContainer.add(GameObjectsContainer.get(TEST_OBJ_1));
        ToDrawContainer.add(GameObjectsContainer.get(TEST_OBJ_2));
        ToDrawContainer.add(GameObjectsContainer.get(DrawableDef.TREE_I.name()));

        GameObjectsContainer.get(MAIN_BASE_1).asGameBuilding().snapTo(GameObjectsContainer.get(GameManager.getCurrentPlanet()));
        GameObjectsContainer.get(MINE_1).asGameBuilding().snapTo(GameObjectsContainer.get(GameManager.getCurrentPlanet()));

        ToDrawContainer.add(GameObjectsContainer.get(MINE_1));
        ToDrawContainer.add(GameObjectsContainer.get("POINT"));
        ToDrawContainer.add(GameObjectsContainer.get("LINE"));
    }

    public void updatePhysicsProperties() {
        GameObjectsContainer.get(MAIN_BASE_1).setPolygonRB();

        GameObjectsContainer.get(PLANET_1).setPolygonRB().getRigidBody().isStaticObject(true)
                .getRBProps().setMass(1000000f).setRestitution(0.3f);

        GameObjectsContainer.get(MAIN_CHARACTER_1).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(2.0f).setRestitution(0.1f);

        GameObjectsContainer.get(TEST_OBJ_1).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0f);

        GameObjectsContainer.get(TEST_OBJ_2).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0f);

        GameObjectsContainer.get(OIL_DRILL_1).setPolygonRB().getRigidBody().isStaticObject(false)
                .setCollisionListener(new GeneralCollisionListener())
                .getRBProps().setMass(1.0f).setRestitution(0.1f);

        IGameObject sampleScanner = GameObjectsContainer.get(SCANNER_1);
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
