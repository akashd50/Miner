package com.greymatter.miner.mainui;

import android.util.Log;

import com.greymatter.miner.game.ControllableGameObjectsContainer;
import com.greymatter.miner.game.GameBuildingsContainer;
import com.greymatter.miner.game.objects.ControllableGameObject;
import com.greymatter.miner.game.objects.Townhall;
import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.Object3DHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.drawables.Object3D;
import com.greymatter.miner.opengl.objects.drawables.Quad;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Triangle;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.PolygonCollider;
import com.greymatter.miner.physics.objects.OnCollisionListener;


class MainGLObjectsHelper {
    static Camera camera;
    static Shader simpleTriangleShader, quadShader, threeDObjectShader, lineShader, gradientShader;
    static Material groundMaterial, atmosphereMaterial, mainBaseMaterial;
    static Drawable mainBase, planet, mainCharacter, testBall, testLine, atmosphere;
    static Collider planetCollider, mainCharCollider, ball2Collider;

    static void onSurfaceChanged(int width, int height) {
        camera = new Camera(width, height);
    }

    static void loadShaders() {
        simpleTriangleShader = new Shader(Constants.SIMPLE_TRIANGLE_SHADER);
        quadShader = new Shader(Constants.QUAD_SHADER);
        threeDObjectShader = new Shader(Constants.THREE_D_OBJECT_SHADER);
        lineShader = new Shader(Constants.LINE_SHADER);
        gradientShader = new Shader(Constants.GRADIENT_SHADERS + Constants.CIRCLE_GRADIENT_SHADER);
    }

    static void loadMaterials() {
        groundMaterial = new Material(Constants.GROUND_I, "");
        atmosphereMaterial = new Material(Constants.ATM_RADIAL_II, "");
        mainBaseMaterial = new Material("main_base.png", "");
    }

    static void loadObjects() {
        atmosphere = new Object3D("atmosphere", Constants.ATM_SIMPLE_CIRCLE, atmosphereMaterial, threeDObjectShader);
        planet = new Object3D("planet", Constants.CIRCLE_SUB_DIV_III, groundMaterial, threeDObjectShader);
        mainCharacter = new Object3D("mainCharacter", Constants.BOX, groundMaterial, threeDObjectShader);
        testBall = new Object3D("testBall", Constants.CIRCLE_SUB_DIV_I, groundMaterial, threeDObjectShader);
        mainBase = new Quad("mainBase", mainBaseMaterial, quadShader);

        GameBuildingsContainer.addBuilding(new Townhall(mainBase));
        ControllableGameObjectsContainer.addObject(new ControllableGameObject(testBall));
        ControllableGameObjectsContainer.addObject(new ControllableGameObject(mainCharacter));
    }

    static void loadPhysicsObjects() {
        planet.setCollider(new PolygonCollider(((Object3D)planet).getOuterMesh()));
        mainCharacter.setCollider(new PolygonCollider(((Object3D) mainCharacter).getOuterMesh()));
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
        planetCollider = planet.getCollider();
        mainCharCollider = mainCharacter.getCollider();
        ball2Collider = testBall.getCollider();

        mainCharCollider.setCollisionListener(listener);
        ball2Collider.setCollisionListener(listener);
    }

    static void initiatePhysicsProcesses() {
        addObjectsToCollisionSystem();
        CollisionDetectionSystem.initializeWorldCollisionDetectionSystem();
    }

    private static void addObjectsToCollisionSystem() {
        CollisionDetectionSystem.addObject(planet);
        CollisionDetectionSystem.addObject(mainCharacter);
        CollisionDetectionSystem.addObject(testBall);
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}
