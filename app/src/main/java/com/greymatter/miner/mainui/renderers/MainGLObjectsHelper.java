package com.greymatter.miner.mainui.renderers;

import android.util.Log;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.animators.impl.ScannerAnimationHandler;
import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.enums.ShaderId;
import com.greymatter.miner.enums.ShapeId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.Animated;
import com.greymatter.miner.game.objects.GameButton;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameNotification;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.InteractiveObject;
import com.greymatter.miner.game.objects.buildings.MainBase;
import com.greymatter.miner.game.objects.buildings.Planet;
import com.greymatter.miner.game.objects.buildings.Scanner;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.game.objects.resources.CoalBlock;
import com.greymatter.miner.helpers.GeneralCollisionListener;
import com.greymatter.miner.helpers.GeneralTouchListener;
import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.animators.FloatValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.materials.colored.StaticColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.animators.IntegerValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.objects.drawables.TextureEdgedPolygon;
import com.greymatter.miner.opengl.objects.materials.textured.StaticTexturedMaterial;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;

import javax.vecmath.Vector2f;
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
        MaterialContainer.add(new StaticTexturedMaterial(MatId.ATMOSPHERE_MATERIAL).attachDiffuseTexture(Path.ATM_RADIAL_II));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.MAIN_BASE_MATERIAL).attachDiffuseTexture(Path.MAIN_BASE_FINAL));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.PLANET_GRASS_MATERIAL_I).attachDiffuseTexture(Path.GRASS_PATCH_I));
        MaterialContainer.add(new StaticColoredMaterial(MatId.GRADIENT_COLOR_MAT)
                        .addColor(ShaderConst.GRADIENT_CENTER_COLOR, new Vector4f(0f,0.2f,0.2f,0.6f))
                        .addColor(ShaderConst.GRADIENT_MID_COLOR, new Vector4f(0f,0.4f,0.3f,0.4f))
                        .addColor(ShaderConst.GRADIENT_EDGE_COLOR, new Vector4f(0f,0.7f,0.3f,0.4f)));

        MaterialContainer.add(new AnimatedTexturedMaterial(MatId.TREE_MATERIAL)
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_i.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_ii.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_iii.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_iv.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_v.png")
                .withAnimationHandler(new IntegerValueAnimator().withFPS(6).withTotalFrames(5)));
    }

    static void loadObjects() {
        Shape boxShape = new Shape(ShapeId.COLLISION_BOX).loadObj(Path.BOX).build();
        Shape atmSimpleCircle = new Shape(ShapeId.CIRCLE_SIMPLE).loadObj(Path.CIRCLE_SIMPLE).build();
        Shape uvmapped = new Shape(ShapeId.UV_MAPPED_BOX).loadObj(Path.UV_MAPPED_BOX).build();
        Shape circleSubDivIII = new Shape(ShapeId.CIRCLE_SUB_III).loadObj(Path.CIRCLE_SUB_DIV_III).build();
        Shape circleSubDivI = new Shape(ShapeId.CIRCLE_SUB_I).loadObj(Path.CIRCLE_SUB_DIV_I).build();
        Shape shape = new Shape(ShapeId.PIE_45).loadPie(45f,1f).build();
        Shape circleEdge = new Shape(ShapeId.CIRCLE_EDGE).loadEdgeOutline(circleSubDivIII, 0.01f).build();



        GameObjectsContainer.add(new GameNotification(new Obj(ObjId.OBJECT_NOTIFICATION)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .setButtonI(new GameButton(getNewObj(ObjId.NOT_BUTTON_I, uvmapped, MaterialContainer.get(MatId.MAIN_BASE_MATERIAL), ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER))))
                                            .setButtonII(new GameButton(getNewObj(ObjId.NOT_BUTTON_II, uvmapped, MaterialContainer.get(MatId.MAIN_BASE_MATERIAL), ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER))))
                                            .addTag(Tag.NOTIFICATION));

        GameObjectsContainer.add(new CoalBlock(new Obj(ObjId.COAL_BLOCK_I)
                                            .setShape(boxShape)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(Tag.RESOURCE_OBJECT));

        GameObjectsContainer.add(new Animated(new RadialGradient(ObjId.PIE_GRADIENT_I)
                                            .setRadius(1f)
                                            .setShape(shape)
                                            .setMaterial(MaterialContainer.get(MatId.GRADIENT_COLOR_MAT))
                                            .setShader(ShaderContainer.get(ShaderId.CIRCLE_GRADIENT_SHADER))
                                            .build())
                                            .setAnimator(new FloatValueAnimator().withFPS(60).setBounds(0f,1f).setPerFrameIncrement(0.01f))
                                            .setOnAnimationFrameHandler((object, animator) -> {
                                                object.getDrawable().asRadialGradient().setMidPoint(animator.update().getUpdatedFloat());

                                            }));

        GameObjectsContainer.add(new Static(new Obj(ObjId.ATMOSPHERE)
                                            .setShape(atmSimpleCircle)
                                            .setMaterial(MaterialContainer.get(MatId.ATMOSPHERE_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(Tag.STATIC));

        GameObjectsContainer.add(new Planet(new Obj(ObjId.PLANET)
                                            .setShape(circleSubDivIII)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_SHADER)).build())
                                            .addTag(Tag.STATIC)
                                            .addTag(Tag.PHYSICS_OBJECT));

        GameObjectsContainer.add(new MainBase(new Obj(ObjId.MAIN_BASE)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(MatId.MAIN_BASE_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(Tag.STATIC)
                                            .addTag(Tag.PLACABLE_GAME_BUILDING)
                                            .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new InteractiveObject(new Obj(ObjId.MAIN_CHARACTER)
                                            .setShape(boxShape)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(Tag.PHYSICS_OBJECT)
                                            .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new Scanner(new Obj(ObjId.SCANNER_I)
                                            .setShape(circleSubDivI)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_SHADER)).build())
                                .setRangeObject(GameObjectsContainer.get(ObjId.PIE_GRADIENT_I))
                                .setAnimator(new BooleanAnimator().withFPS(10))
                                .setOnAnimationFrameHandler(new ScannerAnimationHandler())
                                .addTag(Tag.PLACABLE_GAME_BUILDING)
                                .addTag(Tag.PHYSICS_OBJECT)
                                .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new Animated(new Obj(ObjId.TREE_I)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(MatId.TREE_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build()));

        GameObjectsContainer.add(new Static(new TextureEdgedPolygon(ObjId.PLANET_GRASS_LAYER)
                                            .setShape(circleEdge)
                                            .setMaterial(MaterialContainer.get(MatId.PLANET_GRASS_MATERIAL_I))
                                            .setShader(ShaderContainer.get(ShaderId.QUAD_SHADER)).build()));

        GameObjectsContainer.add(new GameLight(new Obj(ObjId.MAIN_BASE_LIGHT_I))
                                                .setRadius(1f)
                                                .setColor(1f,0f,0f,1f)
                                                .setInnerCutoff(0.02f).setOuterCutoff(0.8f)
                                                .attachTo(GameObjectsContainer.get(ObjId.MAIN_BASE).asGameBuilding()).moveTo(new Vector2f(-0.33f,0.08f)));
    }

    static void finishObjectsSetup() {
        GameObjectsContainer.get(ObjId.COAL_BLOCK_I).scaleTo(0.2f,0.2f).moveTo(10f,-4f, 1f);

        GameObjectsContainer.get(ObjId.ATMOSPHERE).scaleTo(190f,190f).moveTo(0f,-120.5f, -10f);

        GameObjectsContainer.get(ObjId.PLANET).scaleTo(120f,120f).moveTo(0f,-120.5f, 0f);

        GameObjectsContainer.get(ObjId.MAIN_CHARACTER).scaleTo(0.5f,0.5f).moveBy(-0.5f,0f,0f);

        GameObjectsContainer.get(ObjId.SCANNER_I).scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,0f)/*.upgrade(4)*/;

        GameObjectsContainer.get(ObjId.MAIN_BASE).scaleTo(4f,2.7f).moveTo(0f,0f,-5f);

        GameObjectsContainer.get(ObjId.PLANET_GRASS_LAYER).scaleTo(119.65f,119.65f).moveTo(0f,-120.5f, 1f);

        GameObjectsContainer.get(ObjId.TREE_I).scaleTo(1f,1.5f).moveTo(0f,0f, -6f);

        GameObjectsContainer.get(ObjId.OBJECT_NOTIFICATION).scaleTo(1f,0.7f).moveTo(-2f,0.6f, 2f);

        GameObject mainCharacter = GameObjectsContainer.get(ObjId.MAIN_CHARACTER);
        GameObjectsContainer.add(new InteractiveObject(new Line(ObjId.TEST_LINE)
                            .setShader(ShaderContainer.get(ShaderId.LINE_SHADER))
                            .addVertices(mainCharacter.getRigidBody().asPolygonRB().getTransformedVertices())
                            .build()));

        GameObjectsContainer.get(ObjId.PIE_GRADIENT_I).moveTo(0,0f, 2f).scaleTo(4f,3f);

        GameObjectsContainer.runPostInitialization();

        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TEST_LINE));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.ATMOSPHERE));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.MAIN_CHARACTER));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET_GRASS_LAYER));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TREE_I));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.OBJECT_NOTIFICATION));

        ActiveResourcesContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I).asResourceBlock());
    }

    static void loadPhysicsObjects() {
        GameObject planet = GameObjectsContainer.get(ObjId.PLANET).setPolygonRB();
        GameObject mainCharacter = GameObjectsContainer.get(ObjId.MAIN_CHARACTER).setPolygonRB();
        GameObject mainBase = GameObjectsContainer.get(ObjId.MAIN_BASE).setPolygonRB();
        GameObject sampleScanner = GameObjectsContainer.get(ObjId.SCANNER_I);
        sampleScanner.setRigidBody(new PolygonRB(sampleScanner.getId(), sampleScanner.getDrawable().getOptimizedOOMesh(0.1f)));

        planet.getRigidBody().isStaticObject(true)
                            .getRBProps().setMass(1000000f).setRestitution(0.3f);

        mainCharacter.getRigidBody().isStaticObject(false)
                                    .getRBProps().setMass(1.5f).setRestitution(0.5f);

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

    static Drawable getNewObj(ObjId id,Shape shape, Material material, Shader shader) {
        return new Obj(id)
                .setShape(shape)
                .setMaterial(material)
                .setShader(shader).build();
    }
}