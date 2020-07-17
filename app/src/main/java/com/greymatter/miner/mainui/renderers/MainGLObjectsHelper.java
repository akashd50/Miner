package com.greymatter.miner.mainui.renderers;

import android.util.Log;
import com.greymatter.miner.Res;
import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.Animated;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.InteractiveObject;
import com.greymatter.miner.game.objects.buildings.MainBase;
import com.greymatter.miner.game.objects.buildings.Planet;
import com.greymatter.miner.game.objects.buildings.Scanner;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.game.objects.resources.CoalBlock;
import com.greymatter.miner.opengl.objects.animators.BooleanAnimator;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.animators.FloatValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.materials.colored.StaticColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.opengl.objects.animators.IntegerValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.TextureEdgedPolygon;
import com.greymatter.miner.opengl.objects.materials.textured.StaticTexturedMaterial;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.OnCollisionListener;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;
import static com.greymatter.miner.game.GC.*;
import static com.greymatter.miner.Res.*;

class MainGLObjectsHelper {
    static Camera camera;
    static boolean initialSetup = true;
    static void onSurfaceChanged(int width, int height) {
        if(initialSetup) {
            camera = new Camera(width, height);
            initialSetup = false;
        }else{
            camera.onSurfaceChanged(width,height);
        }
    }

    public static void loadShaders() {
        ShaderContainer.addShader(new Shader(SIMPLE_TRIANGLE_SHADER));
        ShaderContainer.addShader(new Shader(QUAD_SHADER));
        ShaderContainer.addShader(new Shader(THREE_D_OBJECT_SHADER));
        ShaderContainer.addShader(new Shader(LINE_SHADER));
        ShaderContainer.addShader(new Shader(CIRCLE_GRADIENT_SHADER));
        ShaderContainer.addShader(new Shader(THREE_D_OBJECT_W_LIGHTING_SHADER));
    }

    static void loadMaterials() {
        MaterialContainer.add(new StaticTexturedMaterial(MatId.GROUND_MATERIAL).attachDiffuseTexture(GROUND_I));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.ATMOSPHERE_MATERIAL).attachDiffuseTexture(ATM_RADIAL_II));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.MAIN_BASE_MATERIAL).attachDiffuseTexture(MAIN_BASE_FINAL));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.PLANET_GRASS_MATERIAL_I).attachDiffuseTexture(GRASS_PATCH_I));
        MaterialContainer.add(new StaticColoredMaterial(MatId.GRADIENT_COLOR_MAT)
                        .addColor(ShaderConst.GRADIENT_CENTER_COLOR, new Vector4f(0f,0.2f,0.2f,0.6f))
                        .addColor(ShaderConst.GRADIENT_MID_COLOR, new Vector4f(0f,0.4f,0.3f,0.4f))
                        .addColor(ShaderConst.GRADIENT_EDGE_COLOR, new Vector4f(0f,0.7f,0.3f,0.4f)));

        MaterialContainer.add(new AnimatedTexturedMaterial(MatId.TREE_MATERIAL)
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_i.png")
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_ii.png")
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_iii.png")
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_iv.png")
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_v.png")
                .withAnimationHandler(new IntegerValueAnimator().withFPS(6).withTotalFrames(5)));
    }

    static void loadObjects() {
        Shape boxShape = new Shape("box").loadObj(Res.OBJECTS_F + BOX).build();
        Shape atmSimpleCircle = new Shape("atmSimple").loadObj(Res.OBJECTS_F + ATM_SIMPLE_CIRCLE).build();
        Shape uvmapped = new Shape("uvMapped").loadObj(Res.OBJECTS_F + UV_MAPPED_BOX).build();
        Shape circleSubDivIII = new Shape("c3").loadObj(Res.OBJECTS_F + CIRCLE_SUB_DIV_III).build();
        Shape circleSubDivI = new Shape("c1").loadObj(Res.OBJECTS_F + CIRCLE_SUB_DIV_I).build();
        Shape shape = new Shape("tri").loadPie(45f,1f).build();

        GameObjectsContainer.add(new CoalBlock(new Obj("coal")
                                            .setShape(boxShape)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(Tag.RESOURCE_OBJECT));

        GameObjectsContainer.add(new Animated(new RadialGradient("range")
                                            .setRadius(1f)
                                            .setShape(shape)
                                            .setMaterial(MaterialContainer.get(MatId.GRADIENT_COLOR_MAT))
                                            .setShader(ShaderContainer.get(CIRCLE_GRADIENT_SHADER))
                                            .build()));

        GameObjectsContainer.add(new Static(new Obj(ATMOSPHERE)
                                            .setShape(atmSimpleCircle)
                                            .setMaterial(MaterialContainer.get(MatId.ATMOSPHERE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build())
                                            .addTag(Tag.STATIC));

        GameObjectsContainer.add(new Planet(new Obj(PLANET)
                                            .setShape(circleSubDivIII)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build())
                                            .addTag(Tag.STATIC)
                                            .addTag(Tag.PHYSICS_OBJECT));

        GameObjectsContainer.add(new MainBase(new Obj(MAIN_BASE)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(MatId.MAIN_BASE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(Tag.STATIC)
                                            .addTag(Tag.PLACABLE_GAME_BUILDING));

        GameObjectsContainer.add(new InteractiveObject(new Obj(MAIN_CHARACTER)
                                            .setShape(boxShape)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(Tag.PHYSICS_OBJECT));

        GameObjectsContainer.add(new Scanner(new Obj(SAMPLE_SCANNER)
                                            .setShape(circleSubDivI)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build())
                                            .setRangeDrawableAnimator(new FloatValueAnimator().setBounds(0f,1f).setPerFrameIncrement(0.01f))
                                            .setValueAnimator(new BooleanAnimator().withFPS(10))
                                            .addLinkedGameObject(GameObjectsContainer.get("range"))
                                            .addTag(Tag.PLACABLE_GAME_BUILDING)
                                            .addTag(Tag.PHYSICS_OBJECT));

        GameObjectsContainer.add(new Animated(new Obj(SAMPLE_TREE)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(MatId.TREE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build()));

        GameObjectsContainer.add(new Static(new TextureEdgedPolygon(PLANET_GRASS_LAYER)
                                            .setShape(new Shape("edge").loadEdgeOutline(circleSubDivIII, 0.01f).build())
                                            .setMaterial(MaterialContainer.get(MatId.PLANET_GRASS_MATERIAL_I))
                                            .setShader(ShaderContainer.get(QUAD_SHADER)).build()));

        GameObjectsContainer.add(new GameLight(new Obj("light"))
                                                .setRadius(1f)
                                                .setColor(1f,0f,0f,1f)
                                                .setInnerCutoff(0.2f).setOuterCutoff(0.8f)
                                                .attachTo(GameObjectsContainer.get(MAIN_BASE).asGameBuilding(), new Vector2f(-2.3f,0.3f)));
    }

    static void finishObjectsSetup() {
        GameObjectsContainer.get("coal").scaleTo(0.2f,0.2f).moveTo(10f,-4f, 1f);

        GameObjectsContainer.get(ATMOSPHERE).scaleTo(190f,190f).moveTo(0f,-120.5f, -10f);

        GameObjectsContainer.get(PLANET).scaleTo(120f,120f).moveTo(0f,-120.5f, 0f);

        GameObjectsContainer.get(MAIN_CHARACTER).scaleTo(0.5f,0.5f).moveBy(-0.5f,0f,0f);

        GameObjectsContainer.get(SAMPLE_SCANNER).scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,0f)/*.upgrade(4)*/;

        GameObjectsContainer.get(MAIN_BASE).scaleTo(4f,2.7f).moveTo(-2.4f,2f,-5f);

        GameObjectsContainer.get(PLANET_GRASS_LAYER).scaleTo(119.65f,119.65f).moveTo(0f,-120.5f, 1f);

        GameObjectsContainer.get(SAMPLE_TREE).scaleTo(1f,1.5f).moveTo(0f,0f, -6f);

        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        GameObjectsContainer.add(new InteractiveObject(new Line(TEST_LINE)
                            .setShader(ShaderContainer.get(LINE_SHADER))
                            .addVertices(mainCharacter.getRigidBody().asPolygonRB().getTransformedVertices())
                            .build()));

        GameObjectsContainer.get("range").moveTo(0,0f, 2f).scaleTo(4f,3f);

        GameObjectsContainer.runPostInitialization();

        ToDrawContainer.add(GameObjectsContainer.get(TEST_LINE));
        ToDrawContainer.add(GameObjectsContainer.get(ATMOSPHERE));
        ToDrawContainer.add(GameObjectsContainer.get(PLANET));
        ToDrawContainer.add(GameObjectsContainer.get(MAIN_CHARACTER));
        ToDrawContainer.add(GameObjectsContainer.get(PLANET_GRASS_LAYER));
        ToDrawContainer.add(GameObjectsContainer.get(SAMPLE_TREE));
        ToDrawContainer.add(GameObjectsContainer.get("coal"));

        ActiveResourcesContainer.add(GameObjectsContainer.get("coal").asResourceBlock());
    }

    static void loadPhysicsObjects() {
        Drawable planet = GameObjectsContainer.get(PLANET).getDrawable();
        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        Drawable mainBase = GameObjectsContainer.get(MAIN_BASE).getDrawable();
        Drawable sampleScanner = GameObjectsContainer.get(SAMPLE_SCANNER).getDrawable().asObject3D().attachOptimisedPolygonCollider(0.1f);

        planet.getRigidBody().isStaticObject(true)
                            .getRBProps().setMass(1000000f).setRestitution(0.3f);

        mainCharacter.getRigidBody().isStaticObject(false)
                                    .getRBProps().setMass(1.5f).setRestitution(0.5f);

        sampleScanner.getRigidBody().isStaticObject(false)
                                    .getRBProps().setMass(1.0f).setRestitution(0.5f);

        OnCollisionListener listener = new OnCollisionListener() {
            @Override
            public void impulseResolution(CollisionEvent event) {
                OnCollisionListener.super.impulseResolutionDefault(event);
            }
            @Override
            public void positionalCorrection(CollisionEvent event) {
                OnCollisionListener.super.positionalCorrectionDefault(event);
            }
        };

        //assign colliders and listeners
        mainCharacter.getRigidBody().setCollisionListener(listener);
        sampleScanner.getRigidBody().setCollisionListener(listener);
    }

    static void initiatePhysicsSystem() {
        CollisionSystemContainer.add(GameObjectsContainer.get(PLANET).getRigidBody());
        CollisionSystemContainer.add(GameObjectsContainer.get(MAIN_CHARACTER).getRigidBody());
        CollisionDetectionSystem.initialize();
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}
