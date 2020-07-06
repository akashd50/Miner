package com.greymatter.miner.mainui.renderers;

import android.util.Log;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ActiveObjectsContainer;
import com.greymatter.miner.game.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.InteractiveObject;
import com.greymatter.miner.game.objects.MainBase;
import com.greymatter.miner.game.objects.Planet;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.materials.AnimatedMaterial;
import com.greymatter.miner.opengl.objects.materials.AnimationHandler;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3D;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.textureedged.TextureEdgedPolygon;
import com.greymatter.miner.opengl.objects.materials.StaticMaterial;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.OnCollisionListener;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import static com.greymatter.miner.game.GC.*;
import static com.greymatter.miner.opengl.Constants.*;

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
        ShaderContainer.addShader(new Shader(GRADIENT_SHADERS_F +CIRCLE_GRADIENT_SHADER));
        ShaderContainer.addShader(new Shader(THREE_D_OBJECT_W_LIGHTING_SHADER));
    }

    static void loadMaterials() {
        MaterialContainer.add(new StaticMaterial(GROUND_MATERIAL, GROUND_I));
        MaterialContainer.add(new StaticMaterial(ATMOSPHERE_MATERIAL,ATM_RADIAL_II));
        MaterialContainer.add(new StaticMaterial(MAIN_BASE_MATERIAL, MAIN_BASE_FINAL));
        MaterialContainer.add(new StaticMaterial(PLANET_GRASS_MATERIAL_I, GRASS_PATCH_I));
        MaterialContainer.add(new AnimatedMaterial(TREE_MATERIAL)
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_i.png")
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_ii.png")
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_iii.png")
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_iv.png")
                .addDiffuseTextureFrame(TREE_ANIM_I_F + "c_tree_anim_v.png")
                .withAnimationHandler(new AnimationHandler().withFPS(6).withTotalFrames(5)));
    }

    static void loadObjects() {
        GameObjectsContainer.add(new Static(new Object3D(ATMOSPHERE, ATM_SIMPLE_CIRCLE,
                            MaterialContainer.get(ATMOSPHERE_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_SHADER))).withTag(STATIC));

        GameObjectsContainer.add(new Planet(new Object3D(PLANET, CIRCLE_SUB_DIV_III,
                            MaterialContainer.get(GROUND_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_SHADER)))
                            .withTag(STATIC)
                            .withTag(PHYSICS_OBJECT));

//        GameObjectsContainer.add(new Scanner(new Object3D(SAMPLE_SCANNER, BOX,
//                            MaterialContainer.get(GROUND_MATERIAL),
//                            ShaderContainer.get(THREE_D_OBJECT_SHADER))
//                            .withTag(PHYSICS_OBJECT)
//                            .withTag(PLACABLE_GAME_BUILDING)));

        GameObjectsContainer.add(new MainBase(new Object3D(MAIN_BASE, UV_MAPPED_BOX,
                            MaterialContainer.get(MAIN_BASE_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)))
                            .withTag(STATIC)
                            .withTag(PLACABLE_GAME_BUILDING));

        GameObjectsContainer.add(new InteractiveObject(new Object3D(MAIN_CHARACTER,BOX,
                            MaterialContainer.get(GROUND_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER))).withTag(PHYSICS_OBJECT));

        GameObjectsContainer.add(new InteractiveObject(new Object3D(SAMPLE_SCANNER,CIRCLE_SUB_DIV_I,
                            MaterialContainer.get(GROUND_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_SHADER)))
                            .withTag(PLACABLE_GAME_BUILDING)
                            .withTag(PHYSICS_OBJECT));

        GameObjectsContainer.add(new InteractiveObject(new Object3D(SAMPLE_TREE,UV_MAPPED_BOX,
                MaterialContainer.get(TREE_MATERIAL),
                ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER))));

        GameObjectsContainer.add(new InteractiveObject(new TextureEdgedPolygon(PLANET_GRASS_LAYER,
                ShaderContainer.get(QUAD_SHADER),MaterialContainer.get(PLANET_GRASS_MATERIAL_I))
                .buildWith(GameObjectsContainer.get(PLANET).getCollider().asPolygonCollider().getMeshVertices(), 0.01f)));

        GameObjectsContainer.add(new GameLight(new Object3D("light", UV_MAPPED_BOX,
                                MaterialContainer.get(GROUND_MATERIAL),
                                ShaderContainer.get(THREE_D_OBJECT_W_LIGHTING_SHADER)))
                                .withRadius(1f).withColor(1f,0f,0f,1f)
                                .withInnerCutoff(0.2f).withOuterCutoff(0.8f).withLocation(new Vector2f(0f,0f)));
    }

    static void finishObjectsSetup() {
        GameObjectsContainer.get(ATMOSPHERE).getCollider()
                            .scaleTo(190f,190f,1f)
                            .translateTo(new Vector3f(0f,-120.5f, -10f));

        GameObjectsContainer.get(PLANET).getCollider()
                            .scaleTo(120f,120f,1f)
                            .translateTo(new Vector3f(0f,-120.5f, 0f));

        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        mainCharacter.getCollider()
                    .scaleTo(0.5f,0.5f,1f)
                    .translateBy(new Vector3f(-0.5f,0f,0f));

        GameObjectsContainer.get(SAMPLE_SCANNER).getCollider()
                            .scaleTo(0.6f,0.6f,1f)
                            .translateBy(new Vector3f(-0.5f,2f,0f));

        GameObjectsContainer.get(MAIN_BASE).getCollider()
                            .scaleTo(4f,2.7f,1f)
                            .translateTo(new Vector3f(-2.4f,2f,-5f));

        GameObjectsContainer.get(PLANET_GRASS_LAYER).getCollider()
                            .scaleTo(119.65f,119.65f,1f)
                            .translateTo(new Vector3f(0f,-120.5f, 1f));

        GameObjectsContainer.get(SAMPLE_TREE).getCollider()
                            .scaleTo(1f,1.5f,1f)
                            .translateTo(new Vector3f(0f,0f, -6f));

//        GameObjectsContainer.get(SAMPLE_SCANNER).getCollider()
//                            .scaleTo(0.4f,0.7f,1f)
//                            .translateTo(new Vector3f(0f,1f, 0f));

        GameObjectsContainer.add(new InteractiveObject(new Line(TEST_LINE, ShaderContainer.get(LINE_SHADER))
                .addVertices(mainCharacter.getCollider().asPolygonCollider()
                        .getTransformedVertices()).build()));

        ActiveObjectsContainer.add(GameObjectsContainer.get(TEST_LINE));
        ActiveObjectsContainer.add(GameObjectsContainer.get(ATMOSPHERE));
        ActiveObjectsContainer.add(GameObjectsContainer.get(PLANET));
        ActiveObjectsContainer.add(GameObjectsContainer.get(MAIN_CHARACTER));
        //ToDrawContainer.add(GameObjectsContainer.get(TEST_BALL));
        ActiveObjectsContainer.add(GameObjectsContainer.get(PLANET_GRASS_LAYER));
        ActiveObjectsContainer.add(GameObjectsContainer.get(SAMPLE_TREE));
    }

    static void loadPhysicsObjects() {
        Drawable planet = GameObjectsContainer.get(PLANET).getDrawable();
        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        //Drawable sampleScanner = GameObjectsContainer.get(SAMPLE_SCANNER).getDrawable();
        Drawable mainBase = GameObjectsContainer.get(MAIN_BASE).getDrawable();
        Drawable sampleScanner = GameObjectsContainer.get(SAMPLE_SCANNER).getDrawable().asObject3D().withOptimisedPolygonCollider(0.1f);

        planet.getCollider().updateTransformationsPerMovement(true)
                            .isStaticObject(true)
                            .setMass(1000000f)
                            .setRestitution(0.3f);

        mainCharacter.getCollider().updateTransformationsPerMovement(true)
                                    .isStaticObject(false)
                                    .setMass(1f)
                                    .setRestitution(0.5f);

//        sampleScanner.getCollider().updateTransformationsPerMovement(true)
//                                    .isStaticObject(false)
//                                    .setMass(1f)
//                                    .setRestitution(1f);

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
        //sampleScanner.getCollider().setCollisionListener(listener);
    }

    static void initiatePhysicsSystem() {
        CollisionSystemContainer.add(GameObjectsContainer.get(PLANET).getCollider());
        CollisionSystemContainer.add(GameObjectsContainer.get(MAIN_CHARACTER).getCollider());
        //CollisionSystemContainer.add(GameObjectsContainer.get(TEST_BALL).getCollider());
        CollisionDetectionSystem.initialize();
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}
