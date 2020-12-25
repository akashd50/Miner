package com.greymatter.miner.loaders;

import com.greymatter.miner.containers.ActiveGameObjectContainer;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.AllGameObjectsContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.GameConstants;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.PlayerCharacter;
import com.greymatter.miner.game.objects.buildings.OilDrill;
import com.greymatter.miner.game.objects.buildings.Mine;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.helpers.clicklisteners.SimpleDialogClickListener;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.MainBase;
import com.greymatter.miner.game.objects.Planet;
import com.greymatter.miner.game.objects.buildings.Scanner;

import static com.greymatter.miner.game.GameConstants.*;
import com.greymatter.miner.helpers.touchListeners.BuildingModeTouchListener;
import com.greymatter.miner.helpers.GeneralCollisionListener;
import com.greymatter.miner.helpers.touchListeners.GeneralTouchListener;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

public class WorldLoader extends Loader {
    private final AllGameObjectsContainer allGameObjectsContainer = ContainerManager.getAllGameObjectsContainer();
    private final ActiveGameObjectContainer activeGameObjectContainer = ContainerManager.getActiveGameObjectsContainer();

    public void load() {
        allGameObjectsContainer.add(new Planet(PLANET_1)
                .scaleTo(300f,300f).moveTo(0f,-300.5f, ZHelper.BACK));

        allGameObjectsContainer.add(new GenericObject(DrawableDef.create(DrawableDef.TREE_I))
                .scaleTo(1f,1.5f).moveTo(0f,0.5f, ZHelper.MID_BACK));
//
//        GameObjectsContainer.add(new Animated(DrawableDef.create(DrawableDef.PLANET_TREE_LAYER))
//                .scaleTo(119f,119f).moveTo(0f,-120.5f, -1f));

        allGameObjectsContainer.add(new MainBase(MAIN_BASE_1));
        allGameObjectsContainer.get(MAIN_BASE_1).scaleTo(4f,2.2f).moveTo(-7f,5f,ZHelper.FRONT_MID);
        //allGameObjectsContainer.get(MAIN_BASE_1).getOptionsMenu().addMoveButton();


        allGameObjectsContainer.add(new PlayerCharacter(MAIN_CHARACTER_1));
        allGameObjectsContainer.get(MAIN_CHARACTER_1)
                .scaleTo(0.6f,0.6f)
                .moveBy(-0.5f,0f,ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener());
                //.setOnClickListener(new SimpleDialogClickListener());
                //.setNotification(new ContextMenu("CMENU"));

        allGameObjectsContainer.get(MAIN_CHARACTER_1).getTransforms().rotateTo(0f,0f,90);

        //-------------------------------------
        allGameObjectsContainer.add(new GenericObject(TEST_OBJ_1, DrawableDef.create(DrawableDef.TEST_OBJ_I))
                .addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.5f,0.5f).moveBy(-4f,2f, ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener()));

        allGameObjectsContainer.add(new GenericObject(TEST_OBJ_2, DrawableDef.create(DrawableDef.TEST_OBJ_II))
                .addTag(Tag.DYNAMIC_PHYSICS_OBJECT)
                .scaleTo(0.5f,0.5f).moveBy(-2f,4f, ZHelper.FRONT)
                .setOnTouchListener(new GeneralTouchListener()));
        //-------------------------------------

        allGameObjectsContainer.add(new Scanner(GameConstants.SCANNER_1));
        allGameObjectsContainer.get(SCANNER_1).addTag(Tag.PLACABLE_GAME_BUILDING).addTag(Tag.DYNAMIC_PHYSICS_OBJECT);
        allGameObjectsContainer.get(SCANNER_1).scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,ZHelper.FRONT);
        allGameObjectsContainer.get(SCANNER_1).setOnTouchListener(new BuildingModeTouchListener());
        allGameObjectsContainer.get(SCANNER_1).setOnClickListener(new SimpleDialogClickListener());
//        allGameObjectsContainer.get(SCANNER_1).setDialog(new GameDialog().setButtonIClickListener(object -> {
//                                            System.out.println("NOT I - Button Click -> " + object);
//                                            return true;
//                                        }).setButtonIIClickListener(object -> {
//                                            System.out.println("NOT II - Button Click -> " + object);
//                                            return true;
//                                        }));
//        allGameObjectsContainer.get(SCANNER_1).setSignal((GameSignal)
//                new GameSignal().setOnClickListener(object -> {
//                    Scanner scanner = (Scanner) allGameObjectsContainer.get(GameConstants.SCANNER_1);
//                    LayoutHelper.showDialog(LayoutHelper.getScannerOnResourceFindDialog(scanner, (scanner.getCurrentlyTracking())));
//                    return true;
//                }));

        allGameObjectsContainer.add(new OilDrill(OIL_DRILL_1));
        allGameObjectsContainer.get(OIL_DRILL_1).scaleTo(4f,3f)
                .moveTo(-1f,2f, ZHelper.FRONT-1)
                .addTag(Tag.PLACABLE_GAME_BUILDING);

        allGameObjectsContainer.add(new Mine(MINE_1));

//        allGameObjectsContainer.add("POINT",new GenericObject(DrawableDef.create(DrawableDef.COAL_BLOCK_I))
//                .scaleTo(0.2f,0.2f).moveTo(0f,0f,ZHelper.OVER_FRONT));
//
//        allGameObjectsContainer.add("LINE", new GenericObject(new Line("LINE").addVertex(new Vector3f(-10f,5f,0f))
//                .addVertex(new Vector3f(10f,5f,0f)).build()).moveTo(0f,0f, ZHelper.OVER_FRONT));
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

        activeGameObjectContainer.add(allGameObjectsContainer.get(PLANET_1));
        activeGameObjectContainer.add(allGameObjectsContainer.get(MAIN_BASE_1));
        activeGameObjectContainer.add(allGameObjectsContainer.get(MAIN_CHARACTER_1));
        activeGameObjectContainer.add(allGameObjectsContainer.get(TEST_OBJ_1));
        activeGameObjectContainer.add(allGameObjectsContainer.get(TEST_OBJ_2));
        activeGameObjectContainer.add(allGameObjectsContainer.get(DrawableDef.TREE_I.name()));

        allGameObjectsContainer.get(MAIN_BASE_1).asGameBuilding().getBuildingHelper().snapTo(GameManager.getCurrentPlanet());
        allGameObjectsContainer.get(MINE_1).asGameBuilding().getBuildingHelper().snapTo(GameManager.getCurrentPlanet());

        activeGameObjectContainer.add(allGameObjectsContainer.get(MINE_1));
//        activeGameObjectContainer.add(allGameObjectsContainer.get("POINT"));
//        activeGameObjectContainer.add(allGameObjectsContainer.get("LINE"));
    }

    public void updatePhysicsProperties() {
        allGameObjectsContainer.get(MAIN_BASE_1).setPolygonRB();

        allGameObjectsContainer.get(PLANET_1).setPolygonRB().getRigidBody().isStaticObject(true)
                .getRBProps().setMass(1000000f).setRestitution(0.3f);

        allGameObjectsContainer.get(MAIN_CHARACTER_1).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(2.0f).setRestitution(0.1f);

        allGameObjectsContainer.get(TEST_OBJ_1).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0f);

        allGameObjectsContainer.get(TEST_OBJ_2).setPolygonRB().getRigidBody().isStaticObject(false)
                .getRBProps().setMass(1.0f).setRestitution(0f);

        allGameObjectsContainer.get(OIL_DRILL_1).setPolygonRB().getRigidBody().isStaticObject(false)
                .setCollisionListener(new GeneralCollisionListener())
                .getRBProps().setMass(1.0f).setRestitution(0.1f);

        IGameObject sampleScanner = allGameObjectsContainer.get(SCANNER_1);
        sampleScanner.setRB(new PolygonRB(sampleScanner.getId(), sampleScanner.getDrawable().getOptimizedOOMesh(0.1f)));
        sampleScanner.getRigidBody().isStaticObject(false).getRBProps().setMass(1.0f).setRestitution(0.5f);

        //assign listeners
        allGameObjectsContainer.getAll().forEach(iGameObject -> {
            if(iGameObject.hasTag(Tag.DYNAMIC_PHYSICS_OBJECT)) {
                iGameObject.getRigidBody().setCollisionListener(new GeneralCollisionListener());
            }
        });

        activeGameObjectContainer.getAll().forEach(iGameObject -> {
            if(iGameObject.hasTag(Tag.STATIC_PHYSICS_OBJECT) || iGameObject.hasTag(Tag.DYNAMIC_PHYSICS_OBJECT)) {
                CollisionSystemContainer.add(iGameObject.getRigidBody());
            }
        });
    }

    @Override
    public void onPostSurfaceInitializationHelper() {

    }
}
