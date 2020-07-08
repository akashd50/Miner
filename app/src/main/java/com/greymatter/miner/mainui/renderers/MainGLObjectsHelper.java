package com.greymatter.miner.mainui.renderers;

import android.util.Log;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ActiveObjectsContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.InteractiveObject;
import com.greymatter.miner.game.objects.MainBase;
import com.greymatter.miner.game.objects.Planet;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.gradients.CircleGradient;
import com.greymatter.miner.opengl.objects.materials.colored.StaticColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.opengl.objects.materials.AnimationHandler;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3D;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.textureedged.TextureEdgedPolygon;
import com.greymatter.miner.opengl.objects.materials.textured.StaticTexturedMaterial;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.OnCollisionListener;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
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
                .withAnimationHandler(new AnimationHandler().withFPS(6).withTotalFrames(5)));
    }

    static void loadObjects() {

        GameObjectsContainer.add(new Static(new CircleGradient("g")
                                            .load(0.5f).setMidPoint(0.3f)
                                            .setMaterial(MaterialContainer.get("color"))
                                            .setShader(ShaderContainer.get(CIRCLE_GRADIENT_SHADER))
                                            .setCenterColor(new Vector4f(1f,0f,0f,1f))
                                            .setMidColor(new Vector4f(0f,0f,1f,1f))
                                            .setEdgeColor(new Vector4f(0f,1f,0f,1f))
                                            .build()));

        GameObjectsContainer.add(new Static(new Object3D(ATMOSPHERE)
                                            .load(ATM_SIMPLE_CIRCLE)
                                            .setMaterial(MaterialContainer.get(ATMOSPHERE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build())
                                            .withTag(STATIC));

        GameObjectsContainer.add(new Planet(new Object3D(PLANET)
                                            .load(CIRCLE_SUB_DIV_III)
                                            .setMaterial(MaterialContainer.get(GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build())
                                            .withTag(STATIC)
                                            .withTag(PHYSICS_OBJECT));

        GameObjectsContainer.add(new MainBase(new Object3D(MAIN_BASE)
                                            .load(UV_MAPPED_BOX)
                                            .setMaterial(MaterialContainer.get(MAIN_BASE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .withTag(STATIC)
                                            .withTag(PLACABLE_GAME_BUILDING));

        GameObjectsContainer.add(new InteractiveObject(new Object3D(MAIN_CHARACTER)
                                            .load(BOX)
                                            .setMaterial(MaterialContainer.get(GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .withTag(PHYSICS_OBJECT));

        GameObjectsContainer.add(new InteractiveObject(new Object3D(SAMPLE_SCANNER)
                                            .load(CIRCLE_SUB_DIV_I)
                                            .setMaterial(MaterialContainer.get(GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_SHADER)).build())
                                            .withTag(PLACABLE_GAME_BUILDING)
                                            .withTag(PHYSICS_OBJECT));

        GameObjectsContainer.add(new InteractiveObject(new Object3D(SAMPLE_TREE)
                                            .load(UV_MAPPED_BOX)
                                            .setMaterial(MaterialContainer.get(TREE_MATERIAL))
                                            .setShader(ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)).build()));

        GameObjectsContainer.add(new InteractiveObject(new TextureEdgedPolygon(PLANET_GRASS_LAYER)
                                            .load(GameObjectsContainer.get(PLANET).getCollider().asPolygonCollider().getMeshVertices(), 0.01f)
                                            .setMaterial(MaterialContainer.get(PLANET_GRASS_MATERIAL_I))
                                            .setShader(ShaderContainer.get(QUAD_SHADER)).build()));

        GameObjectsContainer.add(new GameLight(new Object3D("light"))
                                .setRadius(1f).setColor(1f,0f,0f,1f)
                                .setInnerCutoff(0.2f).setOuterCutoff(0.8f)
                                .attachTo(GameObjectsContainer.get(MAIN_BASE).asGameBuilding(), new Vector2f(-2.3f,0.3f)));
    }

    static void finishObjectsSetup() {
        GameObjectsContainer.get(ATMOSPHERE).getCollider()
                            .scaleTo(190f,190f,1f)
                            .translateTo(new Vector3f(0f,-120.5f, -10f));

        GameObjectsContainer.get(PLANET).getCollider()
                            .scaleTo(120f,120f,1f)
                            .translateTo(new Vector3f(0f,-120.5f, 0f));

        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        mainCharacter.getCollider().scaleTo(0.5f,0.5f,1f)
                                    .translateBy(new Vector3f(-0.5f,0f,0f));

        GameObjectsContainer.get(SAMPLE_SCANNER).getCollider()
                            .scaleTo(0.6f,0.6f,1f)
                            .translateBy(new Vector3f(-0.5f,2f,0f));

        GameObjectsContainer.get(MAIN_BASE).moveTo(new Vector3f(-2.4f,2f,-5f))
                                           .getCollider()
                                           .scaleTo(4f,2.7f,1f);

        GameObjectsContainer.get(PLANET_GRASS_LAYER).getCollider()
                            .scaleTo(119.65f,119.65f,1f)
                            .translateTo(new Vector3f(0f,-120.5f, 1f));

        GameObjectsContainer.get(SAMPLE_TREE).getCollider()
                            .scaleTo(1f,1.5f,1f)
                            .translateTo(new Vector3f(0f,0f, -6f));

        GameObjectsContainer.add(new InteractiveObject(new Line(TEST_LINE)
                            .setShader(ShaderContainer.get(LINE_SHADER))
                            .addVertices(mainCharacter.getCollider().asPolygonCollider().getTransformedVertices())
                            .build()));

        GameObjectsContainer.get("g").getCollider().translateTo(new Vector3f(-1,0.5f, 2f));

        ActiveObjectsContainer.add(GameObjectsContainer.get(TEST_LINE));
        ActiveObjectsContainer.add(GameObjectsContainer.get(ATMOSPHERE));
        ActiveObjectsContainer.add(GameObjectsContainer.get(PLANET));
        ActiveObjectsContainer.add(GameObjectsContainer.get(MAIN_CHARACTER));
        ActiveObjectsContainer.add(GameObjectsContainer.get(PLANET_GRASS_LAYER));
        ActiveObjectsContainer.add(GameObjectsContainer.get(SAMPLE_TREE));
        ActiveObjectsContainer.add(GameObjectsContainer.get("g"));
    }

    static void loadPhysicsObjects() {
        Drawable planet = GameObjectsContainer.get(PLANET).getDrawable();
        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        Drawable mainBase = GameObjectsContainer.get(MAIN_BASE).getDrawable();
        Drawable sampleScanner = GameObjectsContainer.get(SAMPLE_SCANNER).getDrawable().asObject3D().attachOptimisedPolygonCollider(0.1f);

        planet.getCollider().updateTransformationsPerMovement(true)
                            .isStaticObject(true)
                            .setMass(1000000f)
                            .setRestitution(0.3f);

        mainCharacter.getCollider().updateTransformationsPerMovement(true)
                                    .isStaticObject(false)
                                    .setMass(1f)
                                    .setRestitution(0.5f);

        mainBase.getCollider().updateTransformationsPerMovement(true);

        sampleScanner.getCollider().updateTransformationsPerMovement(true)
                            .isStaticObject(false)
                            .setMass(2.0f)
                            .setRestitution(0.5f);

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
        mainCharacter.getCollider().setCollisionListener(listener);
        sampleScanner.getCollider().setCollisionListener(listener);
    }

    static void initiatePhysicsSystem() {
        CollisionSystemContainer.add(GameObjectsContainer.get(PLANET).getCollider());
        CollisionSystemContainer.add(GameObjectsContainer.get(MAIN_CHARACTER).getCollider());
        CollisionDetectionSystem.initialize();
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}
