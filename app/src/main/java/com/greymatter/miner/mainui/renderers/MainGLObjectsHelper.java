package com.greymatter.miner.mainui.renderers;

import android.util.Log;
import com.greymatter.miner.containers.DrawableContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.game.containers.BackgroundObjectsContainer;
import com.greymatter.miner.game.containers.InteractiveGameObjectsContainer;
import com.greymatter.miner.game.containers.GameBuildingsContainer;
import com.greymatter.miner.game.objects.InteractiveGameObject;
import com.greymatter.miner.game.objects.Planet;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3DHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3D;
import com.greymatter.miner.opengl.objects.drawables.Quad;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.PolygonCollider;
import com.greymatter.miner.physics.objects.OnCollisionListener;
import javax.vecmath.Vector3f;
import static com.greymatter.miner.game.GC.*;

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
        ShaderContainer.addShader(new Shader(Constants.SIMPLE_TRIANGLE_SHADER));
        ShaderContainer.addShader(new Shader(Constants.QUAD_SHADER));
        ShaderContainer.addShader(new Shader(Constants.THREE_D_OBJECT_SHADER));
        ShaderContainer.addShader(new Shader(Constants.LINE_SHADER));
        ShaderContainer.addShader(new Shader(Constants.GRADIENT_SHADERS + Constants.CIRCLE_GRADIENT_SHADER));
    }

    static void loadMaterials() {
        MaterialContainer.add(new Material(GROUND_MATERIAL, Constants.GROUND_I, ""));
        MaterialContainer.add(new Material(ATMOSPHERE_MATERIAL, Constants.ATM_RADIAL_II, ""));
        MaterialContainer.add(new Material(MAIN_BASE_MATERIAL, "main_base.png", ""));
    }

    static void loadObjects() {
        DrawableContainer.add(new Object3D(ATMOSPHERE, Constants.ATM_SIMPLE_CIRCLE, MaterialContainer.get(ATMOSPHERE_MATERIAL), ShaderContainer.get(Constants.THREE_D_OBJECT_SHADER)));
        DrawableContainer.add(new Object3D(PLANET, Constants.CIRCLE_SUB_DIV_III, MaterialContainer.get(GROUND_MATERIAL), ShaderContainer.get(Constants.THREE_D_OBJECT_SHADER)));
        DrawableContainer.add(new Quad(MAIN_BASE, MaterialContainer.get(MAIN_BASE_MATERIAL), ShaderContainer.get(Constants.QUAD_SHADER)));
        DrawableContainer.add(new Object3D(MAIN_CHARACTER, Constants.BOX, MaterialContainer.get(GROUND_MATERIAL), ShaderContainer.get(Constants.THREE_D_OBJECT_SHADER)));
        DrawableContainer.add(new Object3D(TEST_BALL, Constants.CIRCLE_SUB_DIV_I, MaterialContainer.get(GROUND_MATERIAL), ShaderContainer.get(Constants.THREE_D_OBJECT_SHADER)));

        BackgroundObjectsContainer.add(new Static(DrawableContainer.get(ATMOSPHERE)));
        GameBuildingsContainer.add(new Planet(DrawableContainer.get(PLANET)));
        //GameBuildingsContainer.add(new Townhall(DrawableContainer.get(MAIN_BASE)));

        InteractiveGameObjectsContainer.add(new InteractiveGameObject(DrawableContainer.get(MAIN_CHARACTER)));
        InteractiveGameObjectsContainer.add(new InteractiveGameObject(DrawableContainer.get(TEST_BALL)));
    }

    static void finishObjectsSetup() {
        Drawable atmosphere = DrawableContainer.get(ATMOSPHERE);
        atmosphere.getCollider().scaleTo(new Vector3f(150f,150f,1f));
        atmosphere.getCollider().translateBy(new Vector3f(0f,-75f, -10f));

        Drawable planet = DrawableContainer.get(PLANET);
        planet.getCollider().scaleTo(new Vector3f(100f,100f,1f));
        planet.getCollider().translateBy(new Vector3f(0f,-100.5f, 0f));

        Drawable mainCharacter = DrawableContainer.get(MAIN_CHARACTER);
        mainCharacter.getCollider().scaleTo(new Vector3f(0.5f,0.5f,1f));
        mainCharacter.getCollider().translateBy(new Vector3f(-0.5f,0f,0f));

        Drawable testBall = DrawableContainer.get(TEST_BALL);
        testBall.getCollider().scaleTo(new Vector3f(0.2f,0.2f,1f));
        testBall.getCollider().translateBy(new Vector3f(-0.5f,2f,0f));

        Drawable mainBase = DrawableContainer.get(MAIN_BASE);
        mainBase.getCollider().scaleTo(new Vector3f(4f,2.7f,1f));
        mainBase.getCollider().translateTo(new Vector3f(-2.4f,2f,-5f));

        DrawableContainer.add(new Line(TEST_LINE, ShaderContainer.get(Constants.LINE_SHADER))
                .addVertices(mainCharacter.getCollider().asPolygonCollider()
                        .getTransformedVertices()).build());

        InteractiveGameObjectsContainer.add(new InteractiveGameObject(DrawableContainer.get(TEST_LINE)));
    }

    static void loadPhysicsObjects() {
        Drawable planet = DrawableContainer.get(PLANET);
        planet.setCollider(new PolygonCollider(((Object3D)planet).getOuterMesh()));

        Drawable mainCharacter = DrawableContainer.get(MAIN_CHARACTER);
        mainCharacter.setCollider(new PolygonCollider(((Object3D) mainCharacter).getOuterMesh()));

        Drawable testBall = DrawableContainer.get(TEST_BALL);
        testBall.setCollider(new PolygonCollider(Object3DHelper.simplify(((Object3D) testBall).getOuterMesh(),0.1f)));

        planet.getCollider().updateTransformationsPerMovement(true);
        planet.getCollider().isStaticObject(true);

        mainCharacter.getCollider().updateTransformationsPerMovement(true);
        mainCharacter.getCollider().isStaticObject(false);

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

    static void initiatePhysicsProcesses() {
        addObjectsToCollisionSystem();
        CollisionDetectionSystem.initializeWorldCollisionDetectionSystem();
    }

    private static void addObjectsToCollisionSystem() {
        CollisionDetectionSystem.addObject(DrawableContainer.get(PLANET));
        CollisionDetectionSystem.addObject(DrawableContainer.get(MAIN_CHARACTER));
        CollisionDetectionSystem.addObject(DrawableContainer.get(TEST_BALL));
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}
