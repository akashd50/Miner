package com.greymatter.miner.mainui.renderers;

import android.util.Log;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.game.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.InteractiveObject;
import com.greymatter.miner.game.objects.MainBase;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3DHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3D;
import com.greymatter.miner.opengl.objects.drawables.Quad;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.textureedged.TextureEdgedCircle;
import com.greymatter.miner.opengl.objects.drawables.textureedged.TextureEdgedPolygon;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.PolygonCollider;
import com.greymatter.miner.physics.objects.OnCollisionListener;
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
        ShaderContainer.addShader(new Shader(GRADIENT_SHADERS +CIRCLE_GRADIENT_SHADER));
    }

    static void loadMaterials() {
        MaterialContainer.add(new Material(GROUND_MATERIAL, GROUND_I, ""));
        MaterialContainer.add(new Material(ATMOSPHERE_MATERIAL,ATM_RADIAL_II, ""));
        MaterialContainer.add(new Material(MAIN_BASE_MATERIAL, MAIN_BASE_FINAL, ""));
        MaterialContainer.add(new Material("grass", "grass_patch_gimp.png", ""));
    }

    static void loadObjects() {
        GameObjectsContainer.add(new Static(new Object3D(ATMOSPHERE, ATM_SIMPLE_CIRCLE,
                            MaterialContainer.get(ATMOSPHERE_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_SHADER)).withTag(STATIC)));

        GameObjectsContainer.add(new Static(new Object3D(PLANET, CIRCLE_SUB_DIV_III,
                            MaterialContainer.get(GROUND_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_SHADER))
                            .withTag(STATIC)
                            .withTag(PHYSICS_OBJECT)));

        GameObjectsContainer.add(new MainBase(new Object3D(MAIN_BASE, UV_MAPPED_BOX,
                            MaterialContainer.get(MAIN_BASE_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_SHADER))
                            .withTag(STATIC)
                            .withTag(PLACABLE_GAME_BUILDING)));

        GameObjectsContainer.add(new InteractiveObject(new Object3D(MAIN_CHARACTER,BOX,
                            MaterialContainer.get(GROUND_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_SHADER)).withTag(PHYSICS_OBJECT)));

        GameObjectsContainer.add(new InteractiveObject(new Object3D(TEST_BALL,CIRCLE_SUB_DIV_I,
                            MaterialContainer.get(GROUND_MATERIAL),
                            ShaderContainer.get(THREE_D_OBJECT_SHADER)).withTag(PHYSICS_OBJECT)));

        GameObjectsContainer.add(new InteractiveObject(new TextureEdgedPolygon("edge",
                ShaderContainer.get(QUAD_SHADER),MaterialContainer.get("grass"))
                .buildWith(GameObjectsContainer.get(PLANET).getCollider().asPolygonCollider().getMeshVertices(), 0.005f)));
    }

    static void finishObjectsSetup() {
        Drawable atmosphere = GameObjectsContainer.get(ATMOSPHERE).getDrawable();
        atmosphere.getCollider().scaleTo(new Vector3f(190f,190f,1f));
        atmosphere.getCollider().translateTo(new Vector3f(0f,-100f, -10f));

        Drawable planet = GameObjectsContainer.get(PLANET).getDrawable();
        planet.getCollider().scaleTo(new Vector3f(120f,120f,1f));
        planet.getCollider().translateTo(new Vector3f(0f,-120.5f, 0f));

        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        mainCharacter.getCollider().scaleTo(new Vector3f(0.5f,0.5f,1f));
        mainCharacter.getCollider().translateBy(new Vector3f(-0.5f,0f,0f));

        Drawable testBall = GameObjectsContainer.get(TEST_BALL).getDrawable();
        testBall.getCollider().scaleTo(new Vector3f(0.2f,0.2f,1f));
        testBall.getCollider().translateBy(new Vector3f(-0.5f,2f,0f));

        Drawable mainBase = GameObjectsContainer.get(MAIN_BASE).getDrawable();
        mainBase.getCollider().scaleTo(new Vector3f(4f,2.7f,1f));
        mainBase.getCollider().translateTo(new Vector3f(-2.4f,2f,-5f));

        Drawable edge = GameObjectsContainer.get("edge").getDrawable();
        edge.getCollider().scaleTo(new Vector3f(119.6f,119.6f,1f));
        edge.getCollider().translateTo(new Vector3f(0f,-120.5f, 1f));
//        edge.getCollider().scaleTo(new Vector3f(2f,2f,1f));
//        edge.getCollider().translateTo(new Vector3f(0f,0f, 1f));

        GameObjectsContainer.add(new InteractiveObject(new Line(TEST_LINE, ShaderContainer.get(LINE_SHADER))
                .addVertices(mainCharacter.getCollider().asPolygonCollider()
                        .getTransformedVertices()).build()));

        ToDrawContainer.add(GameObjectsContainer.get(TEST_LINE));
        ToDrawContainer.add(GameObjectsContainer.get(ATMOSPHERE));
        ToDrawContainer.add(GameObjectsContainer.get(PLANET));
        ToDrawContainer.add(GameObjectsContainer.get(MAIN_CHARACTER));
        ToDrawContainer.add(GameObjectsContainer.get(TEST_BALL));
        ToDrawContainer.add(GameObjectsContainer.get("edge"));
    }

    static void loadPhysicsObjects() {
        Drawable planet = GameObjectsContainer.get(PLANET).getDrawable();
        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        Drawable mainBase = GameObjectsContainer.get(MAIN_BASE).getDrawable();
        Drawable testBall = GameObjectsContainer.get(TEST_BALL).getDrawable().asObject3D().withOptimisedPolygonCollider(0.1f);

        planet.getCollider().updateTransformationsPerMovement(true);
        planet.getCollider().isStaticObject(true);

        mainCharacter.getCollider().updateTransformationsPerMovement(true);
        mainCharacter.getCollider().isStaticObject(false);

        mainBase.getCollider().updateTransformationsPerMovement(true);

        testBall.getCollider().updateTransformationsPerMovement(true);
        testBall.getCollider().isStaticObject(false);

        planet.getCollider().setMass(1000f);
        planet.getCollider().setRestitution(0.3f);

        mainCharacter.getCollider().setMass(1f);
        mainCharacter.getCollider().setRestitution(1f);

        testBall.getCollider().setMass(1.0f);
        testBall.getCollider().setRestitution(1f);

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
        testBall.getCollider().setCollisionListener(listener);
    }

    static void initiatePhysicsSystem() {
        CollisionSystemContainer.add(GameObjectsContainer.get(PLANET).getDrawable());
        CollisionSystemContainer.add(GameObjectsContainer.get(MAIN_CHARACTER).getDrawable());
        CollisionSystemContainer.add(GameObjectsContainer.get(TEST_BALL).getDrawable());
        CollisionDetectionSystem.initialize();
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}
