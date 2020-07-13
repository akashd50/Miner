package com.greymatter.miner.mainui.renderers;

import android.util.Log;

import com.greymatter.miner.Res;
import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.InteractiveObject;
import com.greymatter.miner.game.objects.MainBase;
import com.greymatter.miner.game.objects.Planet;
import com.greymatter.miner.game.objects.Scanner;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.game.objects.resources.CoalBlock;
import com.greymatter.miner.opengl.objects.BooleanAnimator;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.FloatValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Quad;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.materials.colored.StaticColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.opengl.objects.IntegerValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.textureedged.TextureEdgedPolygon;
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
        MaterialContainer.add(new StaticTexturedMaterial(GROUND_MATERIAL).attachDiffuseTexture(GROUND_I));
        MaterialContainer.add(new StaticTexturedMaterial(ATMOSPHERE_MATERIAL).attachDiffuseTexture(ATM_RADIAL_II));
        MaterialContainer.add(new StaticTexturedMaterial(MAIN_BASE_MATERIAL).attachDiffuseTexture(MAIN_BASE_FINAL));
        MaterialContainer.add(new StaticTexturedMaterial(PLANET_GRASS_MATERIAL_I).attachDiffuseTexture(GRASS_PATCH_I));
        MaterialContainer.add(new StaticColoredMaterial("color"));
        MaterialContainer.add(new AnimatedTexturedMaterial(TREE_MATERIAL)
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
                                            .setMaterial(MaterialContainer.get(GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(RESOURCE_OBJECT));

        GameObjectsContainer.add(new Static(new RadialGradient("g")
                                            .setShape(shape).setRadius(1f)
                                            .setMidPoint(0.01f)
                                            .setMaterial(MaterialContainer.get("color"))
                                            .setShader(ShaderContainer.get(CIRCLE_GRADIENT_SHADER))
                                            .setCenterColor(new Vector4f(0f,0.2f,0.2f,0.6f))
                                            .setMidColor(new Vector4f(0f,0.4f,0.3f,0.4f))
                                            .setEdgeColor(new Vector4f(0f,0.7f,0.3f,0.4f))
                                            .build()));

        GameObjectsContainer.add(new Static(new Quad("q").load(new Shape("quad").loadQuad(1.0f).build())
                                            .setMaterial(MaterialContainer.get(GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(QUAD_SHADER))
                                            .build()));

        GameObjectsContainer.add(new Static(new Obj(ATMOSPHERE)
                                            .setShape(atmSimpleCircle)
                                            .setMaterial(MaterialContainer.get(ATMOSPHERE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build())
                                            .addTag(STATIC));

        GameObjectsContainer.add(new Planet(new Obj(PLANET)
                                            .setShape(circleSubDivIII)
                                            .setMaterial(MaterialContainer.get(GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build())
                                            .addTag(STATIC)
                                            .addTag(PHYSICS_OBJECT));

        GameObjectsContainer.add(new MainBase(new Obj(MAIN_BASE)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(MAIN_BASE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(STATIC)
                                            .addTag(PLACABLE_GAME_BUILDING));

        GameObjectsContainer.add(new InteractiveObject(new Obj(MAIN_CHARACTER)
                                            .setShape(boxShape)
                                            .setMaterial(MaterialContainer.get(GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(PHYSICS_OBJECT));

        GameObjectsContainer.add(new Scanner(new Obj(SAMPLE_SCANNER)
                                            .setShape(circleSubDivI)
                                            .setMaterial(MaterialContainer.get(GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build(),
                                            GameObjectsContainer.get("g").getDrawable().asRadialGradient())
                                            .setRangeDrawableAnimator(new FloatValueAnimator().setBounds(0f,1f).setPerFrameIncrement(0.01f))
                                            .setValueAnimator(new BooleanAnimator().withFPS(10))
                                            .addTag(PLACABLE_GAME_BUILDING)
                                            .addTag(PHYSICS_OBJECT));

        GameObjectsContainer.add(new InteractiveObject(new Obj(SAMPLE_TREE)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(TREE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build()));

        GameObjectsContainer.add(new InteractiveObject(new TextureEdgedPolygon(PLANET_GRASS_LAYER)
                                            .setShape(new Shape("edge").loadEdgeOutline(circleSubDivIII, 0.01f).build())
                                            //.load(GameObjectsContainer.get(PLANET).getRigidBody().asPolygonRB().getMeshVertices(), 0.01f)
                                            .setMaterial(MaterialContainer.get(PLANET_GRASS_MATERIAL_I))
                                            .setShader(ShaderContainer.get(QUAD_SHADER)).build()));

        GameObjectsContainer.add(new GameLight(new Obj("light"))
                                .setRadius(1f).setColor(1f,0f,0f,1f)
                                .setInnerCutoff(0.2f).setOuterCutoff(0.8f)
                                .attachTo(GameObjectsContainer.get(MAIN_BASE).asGameBuilding(), new Vector2f(-2.3f,0.3f)));
    }

    static void finishObjectsSetup() {
        GameObjectsContainer.get("coal").scaleTo(0.2f,0.2f).moveTo(10f,-4f, 1f);

        GameObjectsContainer.get(ATMOSPHERE).scaleTo(190f,190f).moveTo(0f,-120.5f, -10f);

        GameObjectsContainer.get(PLANET).scaleTo(120f,120f).moveTo(0f,-120.5f, 0f);

        GameObjectsContainer.get(MAIN_CHARACTER).scaleTo(0.5f,0.5f).moveBy(-0.5f,0f,0f);

        GameObjectsContainer.get(SAMPLE_SCANNER).scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,0f).upgrade();

        GameObjectsContainer.get(MAIN_BASE).scaleTo(4f,2.7f).moveTo(-2.4f,2f,-5f);

        GameObjectsContainer.get(PLANET_GRASS_LAYER).scaleTo(119.65f,119.65f).moveTo(0f,-120.5f, 1f);

        GameObjectsContainer.get(SAMPLE_TREE).scaleTo(1f,1.5f).moveTo(0f,0f, -6f);

        GameObjectsContainer.get("q").moveTo(0f,0f, 2f);

        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        GameObjectsContainer.add(new InteractiveObject(new Line(TEST_LINE)
                            .setShader(ShaderContainer.get(LINE_SHADER))
                            .addVertices(mainCharacter.getRigidBody().asPolygonRB().getTransformedVertices())
                            .build()));

        GameObjectsContainer.get("g").moveTo(0,0f, 2f).scaleTo(3f,3f);

        ToDrawContainer.add(GameObjectsContainer.get(TEST_LINE));
        ToDrawContainer.add(GameObjectsContainer.get(ATMOSPHERE));
        ToDrawContainer.add(GameObjectsContainer.get(PLANET));
        ToDrawContainer.add(GameObjectsContainer.get(MAIN_CHARACTER));
        ToDrawContainer.add(GameObjectsContainer.get(PLANET_GRASS_LAYER));
        ToDrawContainer.add(GameObjectsContainer.get(SAMPLE_TREE));
        ToDrawContainer.add(GameObjectsContainer.get("coal"));
        ToDrawContainer.add(GameObjectsContainer.get("q"));

        ActiveResourcesContainer.add(GameObjectsContainer.get("coal").asResourceBlock());
        //ActiveObjectsContainer.add(GameObjectsContainer.get("g"));
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
