package com.greymatter.miner.mainui.renderers;

import android.util.Log;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.animators.impl.DialogAnimator;
import com.greymatter.miner.animators.impl.ScannerAnimationHandler;
import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ShapeContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.enums.ShaderId;
import com.greymatter.miner.enums.ShapeId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.Animated;
import com.greymatter.miner.game.objects.ui.GameButton;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.ui.GameDialog;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.InteractiveObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.MainBase;
import com.greymatter.miner.game.objects.buildings.Planet;
import com.greymatter.miner.game.objects.buildings.Scanner;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.game.objects.resources.CoalBlock;
import com.greymatter.miner.helpers.GeneralCollisionListener;
import com.greymatter.miner.helpers.GeneralTouchListener;
import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.mainui.DrawableDef;
import com.greymatter.miner.mainui.touch.OnClickListener;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.objects.materials.colored.StaticColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.animators.IntegerValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.objects.materials.textured.StaticTexturedMaterial;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import com.greymatter.miner.Path;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

class MainGLObjectsHelper {
    static Camera camera;
    static boolean initialSetup = true;
    static void onSurfaceChanged(int width, int height) {
        if(initialSetup) {
            camera = new Camera(width, height);
            AppServices.setCamera(camera);
            initialSetup = false;
        }else{
            camera.onSurfaceChanged(width,height);
        }
    }

    public static void loadShaders() {
        ShaderContainer.addShader(new Shader(ShaderId.SIMPLE_TRIANGLE_SHADER).load(Path.SIMPLE_TRIANGLE_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.QUAD_SHADER).load(Path.QUAD_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.THREE_D_OBJECT_SHADER).load(Path.THREE_D_OBJECT_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.LINE_SHADER).load(Path.LINE_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.CIRCLE_GRADIENT_SHADER).load(Path.CIRCLE_GRADIENT_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER).load(Path.THREE_D_OBJECT_W_LIGHTING_SHADER));
    }

    static void loadMaterials() {
        MaterialContainer.add(new StaticTexturedMaterial(MatId.GROUND_MATERIAL).attachDiffuseTexture(Path.GROUND_I));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.DIALOG).attachDiffuseTexture(Path.DIALOG_I));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.ATMOSPHERE_MATERIAL).attachDiffuseTexture(Path.ATM_RADIAL_II));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.MAIN_BASE_MATERIAL).attachDiffuseTexture(Path.MAIN_BASE_FINAL));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.PLANET_GRASS_MATERIAL_I).attachDiffuseTexture(Path.GRASS_PATCH_I));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.BUTTON_MAT_I).attachDiffuseTexture(Path.BUTTON_I));
        MaterialContainer.add(new StaticColoredMaterial(MatId.GRADIENT_COLOR_MAT)
                        .addColor(ShaderConst.GRADIENT_CENTER_COLOR, new Vector4f(0f,0.2f,0.2f,0.6f))
                        .addColor(ShaderConst.GRADIENT_MID_COLOR, new Vector4f(0f,0.4f,0.3f,0.2f))
                        .addColor(ShaderConst.GRADIENT_EDGE_COLOR, new Vector4f(0f,0.7f,0.3f,0f)));

        MaterialContainer.add(new AnimatedTexturedMaterial(MatId.TREE_MATERIAL)
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_i.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_ii.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_iii.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_iv.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_v.png")
                .withAnimationHandler(new IntegerValueAnimator().withFPS(6).withTotalFrames(5)));
    }

    static void loadObjects() {
        ShapeContainer.add(new Shape(ShapeId.COLLISION_BOX).loadObj(Path.BOX).build());
        ShapeContainer.add(new Shape(ShapeId.CIRCLE_SIMPLE).loadObj(Path.CIRCLE_SIMPLE).build());
        ShapeContainer.add(new Shape(ShapeId.UV_MAPPED_BOX).loadObj(Path.UV_MAPPED_BOX).build());
        ShapeContainer.add(new Shape(ShapeId.CIRCLE_SUB_III).loadObj(Path.CIRCLE_SUB_DIV_III).build());
        ShapeContainer.add(new Shape(ShapeId.CIRCLE_SUB_I).loadObj(Path.CIRCLE_SUB_DIV_I).build());
        ShapeContainer.add(new Shape(ShapeId.PIE_45).loadPie(45f,1f).build());
        ShapeContainer.add(new Shape(ShapeId.CIRCLE_EDGE).loadEdgeOutline(ShapeContainer.get(ShapeId.CIRCLE_SUB_III), 0.01f).build());
        //ShapeContainer.add(new Shape(ShapeId.PLANET_TREE_EDGE).loadEdgeOutline(ShapeContainer.get(ShapeId.CIRCLE_SUB_I), 0.026f).build());
        ShapeContainer.add(new Shape(ShapeId.SIMPLE_QUAD).loadQuad(1.2f).build());

        GameObjectsContainer.add(new GameDialog(DrawableDef.create(ObjId.OBJECT_NOTIFICATION))
                                .setButtonI(new GameButton(DrawableDef.create(ObjId.NOT_BUTTON_I)))
                                .setButtonII(new GameButton(DrawableDef.create(ObjId.NOT_BUTTON_II)))
                                .setDefaultScale(new Vector3f(2f,1.5f,1f))
                                .setButtonIClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(GameObject object) {
                                        System.out.println("NOT I - Button Click -> " + object);
                                    }
                                }).setButtonIIClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(GameObject object) {
                                        System.out.println("NOT II - Button Click -> " + object);
                                    }
                                }).addTag(Tag.NOTIFICATION).shouldDraw(false)
                                .moveTo(0f,1.9f, 1.5f)
                                .translationFromParent(true));

        GameObjectsContainer.add(new CoalBlock(DrawableDef.create(ObjId.COAL_BLOCK_I))
                                .addTag(Tag.RESOURCE_OBJECT)
                                .scaleTo(0.2f,0.2f).moveTo(10f,-4f, -2f));

        GameObjectsContainer.add(new Animated(DrawableDef.create(ObjId.PIE_GRADIENT_I))
                                .moveTo(0,0f, 2f).scaleTo(4f,3f)
                                .setAnimator(new FloatValueAnimator().withFPS(60).setBounds(0f,1f).setPerFrameIncrement(0.04f).toAndFro(true))
                                .setOnAnimationFrameHandler((object, animator) -> {
                                    object.getDrawable().asRadialGradient().setMidPoint(animator.update().getUpdatedFloat());
                                }));

        GameObjectsContainer.add(new Static(DrawableDef.create(ObjId.ATMOSPHERE))
                                .addTag(Tag.STATIC)
                                .scaleTo(190f,190f).moveTo(0f,-120.5f, -10f));

        GameObjectsContainer.add(new Planet(DrawableDef.create(ObjId.PLANET))
                                .addTag(Tag.STATIC).addTag(Tag.PHYSICS_OBJECT)
                                .scaleTo(120f,120f).moveTo(0f,-120.5f, 0f));

        GameObjectsContainer.add(new MainBase(DrawableDef.create(ObjId.MAIN_BASE))
                                .addTag(Tag.STATIC)
                                .addTag(Tag.PLACABLE_GAME_BUILDING)
                                .scaleTo(4f,2.7f).moveTo(0f,0f,-5f)
                                .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new InteractiveObject(DrawableDef.create(ObjId.MAIN_CHARACTER))
                                .addTag(Tag.PHYSICS_OBJECT)
                                .scaleTo(0.5f,0.5f).moveBy(-0.5f,0f,0f)
                                .setOnTouchListener(new GeneralTouchListener())
                                .addChild(ObjId.OBJECT_NOTIFICATION, GameObjectsContainer.get(ObjId.OBJECT_NOTIFICATION))
                                .setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(GameObject object) {
                                        if(!object.getChild(ObjId.OBJECT_NOTIFICATION).shouldDraw()) {
                                            ((GameDialog) object.getChild(ObjId.OBJECT_NOTIFICATION)).show();
                                        }else{
                                            ((GameDialog) object.getChild(ObjId.OBJECT_NOTIFICATION)).hide();
                                        }
                                    }
                                }));

        GameObjectsContainer.add(new Scanner(DrawableDef.create(ObjId.SCANNER_I))
                                .setRangeObject(GameObjectsContainer.get(ObjId.PIE_GRADIENT_I))
                                .setAnimator(new BooleanAnimator().withFPS(10))
                                .setOnAnimationFrameHandler(new ScannerAnimationHandler())
                                .addTag(Tag.PLACABLE_GAME_BUILDING)
                                .addTag(Tag.PHYSICS_OBJECT)
                                .scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,-1f)
                                .setOnTouchListener(new GeneralTouchListener())
                                .setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(GameObject object) {
                                        if(!object.getChild(ObjId.OBJECT_NOTIFICATION).shouldDraw()) {
                                            ((GameDialog) object.getChild(ObjId.OBJECT_NOTIFICATION)).show();
                                        }else{
                                            ((GameDialog) object.getChild(ObjId.OBJECT_NOTIFICATION)).hide();
                                        }
                                    }
                                })
                                .addChild(ObjId.OBJECT_NOTIFICATION,
                                        new GameDialog(DrawableDef.create(ObjId.OBJECT_NOTIFICATION))
                                        .setButtonI(new GameButton(DrawableDef.create(ObjId.NOT_BUTTON_I)))
                                        .setButtonII(new GameButton(DrawableDef.create(ObjId.NOT_BUTTON_II)))
                                        .setDefaultScale(new Vector3f(2f,1.5f, 1f))
                                        .setButtonIClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(GameObject object) {
                                                System.out.println("NOT I - Button Click -> " + object);
                                            }
                                        }).setButtonIIClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(GameObject object) {
                                                System.out.println("NOT II - Button Click -> " + object);
                                            }
                                        }).addTag(Tag.NOTIFICATION).shouldDraw(false)
                                        .moveTo(0f,2.1f, 1.5f)
                                        .translationFromParent(true)));

        GameObjectsContainer.add(new Animated(DrawableDef.create(ObjId.TREE_I))
                                .scaleTo(1f,1.5f).moveTo(0f,0f, -6f));
//
//        GameObjectsContainer.add(new Animated(DrawableDef.create(ObjId.PLANET_TREE_LAYER))
//                .scaleTo(119f,119f).moveTo(0f,-120.5f, -1f));

        GameObjectsContainer.add(new Static(DrawableDef.create(ObjId.PLANET_GRASS_LAYER))
                                .scaleTo(119.65f,119.65f).moveTo(0f,-120.5f, 1f));

        GameObjectsContainer.add(new GameLight(new Obj(ObjId.MAIN_BASE_LIGHT_I))
                                .setRadius(1f)
                                .setColor(1f,0f,0f,1f)
                                .setInnerCutoff(0.02f).setOuterCutoff(0.8f)
                                .attachTo(GameObjectsContainer.get(ObjId.MAIN_BASE).asGameBuilding())
                                .moveTo(new Vector2f(-0.33f,0.08f)));

        GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getTransforms().rotateTo(0f,0f,90);
    }

    static void finishObjectsSetup() {
        IGameObject mainCharacter = GameObjectsContainer.get(ObjId.MAIN_CHARACTER);
        GameObjectsContainer.add(new InteractiveObject(new Line(ObjId.TEST_LINE)
                            .setShader(ShaderContainer.get(ShaderId.LINE_SHADER))
                            .addVertices(mainCharacter.getRigidBody().asPolygonRB().getTransformedVertices())
                            .build()));

        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TEST_LINE));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.ATMOSPHERE));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.MAIN_CHARACTER));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET_GRASS_LAYER));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TREE_I));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I));
        //ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET_TREE_LAYER));
        //ToDrawContainer.add(GameObjectsContainer.get(ObjId.OBJECT_NOTIFICATION));

        ActiveResourcesContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I).asResourceBlock());
    }

    static void loadPhysicsObjects() {
        IGameObject planet = GameObjectsContainer.get(ObjId.PLANET).setPolygonRB();
        IGameObject mainCharacter = GameObjectsContainer.get(ObjId.MAIN_CHARACTER).setPolygonRB();
        IGameObject mainBase = GameObjectsContainer.get(ObjId.MAIN_BASE).setPolygonRB();
        IGameObject sampleScanner = GameObjectsContainer.get(ObjId.SCANNER_I);
        sampleScanner.setRigidBody(new PolygonRB(sampleScanner.getId(), sampleScanner.getDrawable().getOptimizedOOMesh(0.1f)));
        sampleScanner.setPolygonTC();

        planet.getRigidBody().isStaticObject(true)
                            .getRBProps().setMass(1000000f).setRestitution(0.3f);

        mainCharacter.getRigidBody().isStaticObject(false)
                                    .getRBProps().setMass(1.0f).setRestitution(0.5f);

        sampleScanner.getRigidBody().isStaticObject(false)
                                    .getRBProps().setMass(1.0f).setRestitution(0.5f);

        //assign colliders and listeners
        mainCharacter.getRigidBody().setCollisionListener(new GeneralCollisionListener());
        sampleScanner.getRigidBody().setCollisionListener(new GeneralCollisionListener());
    }

    static void initiatePhysicsSystem() {
        CollisionSystemContainer.add(GameObjectsContainer.get(ObjId.PLANET).getRigidBody());
        CollisionSystemContainer.add(GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getRigidBody());
        CollisionDetectionSystem.initialize();
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}