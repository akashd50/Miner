package com.greymatter.miner.opengl;

import android.util.Log;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.Object3DHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Object3D;
import com.greymatter.miner.opengl.objects.Quad;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.Triangle;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.PolygonCollider;
import com.greymatter.miner.physics.objects.OnCollisionListener;

import javax.vecmath.Vector3f;

class MainGLObjectsHelper {
    static Camera camera;
    static Shader simpleTriangleShader, quadShader, threeDObjectShader, lineShader;
    static Triangle triangle;
    static Material material, backdropMaterial, groundMaterial, characterMaterial;
    static Drawable backdropQuad, groundQuad, characterQuad, planet, ball, ball2, testLine;
    static Collider planetCollider, ballCollider, ball2Collider;

    static void onSurfaceChanged(int width, int height) {
        camera = new Camera(width, height);
    }

    static void loadShaders() {
        simpleTriangleShader = new Shader(Constants.SIMPLE_TRIANGLE_SHADER);
        quadShader = new Shader(Constants.QUAD_SHADER);
        threeDObjectShader = new Shader(Constants.THREE_D_OBJECT_SHADER);
        lineShader = new Shader(Constants.LINE_SHADER);
    }

    static void loadMaterials() {
        material = new Material("download.jpg","");
        backdropMaterial = new Material("backdrop_i.png", "");
        groundMaterial = new Material("ground_i.png", "");
        characterMaterial = new Material("character_i.png", "");
    }

    static void loadObjects() {
        triangle = new Triangle(simpleTriangleShader);
        backdropQuad = new Quad(backdropMaterial, quadShader);
        groundQuad = new Quad(groundMaterial, quadShader);
        characterQuad = new Quad(characterMaterial, quadShader);
        planet = new Object3D("objects/circle_sub_div_iii.obj", characterMaterial, threeDObjectShader);
        ball = new Object3D("objects/box.obj", characterMaterial, threeDObjectShader);
        ball2 = new Object3D("objects/circle_sub_div_i.obj", characterMaterial, threeDObjectShader);
    }

    static void loadPhysicsObjects() {
        planet.setCollider(new PolygonCollider(((Object3D)planet).getOuterMesh()));
        ball.setCollider(new PolygonCollider(((Object3D)ball).getOuterMesh()));
        ball2.setCollider(new PolygonCollider(Object3DHelper.simplify(((Object3D)ball2).getOuterMesh(),0.1f)));

        planet.updateTransformationsPerMovement(true);
        planet.isStaticObject(true);

        ball.updateTransformationsPerMovement(true);
        ball.isStaticObject(false);
        ball2.updateTransformationsPerMovement(true);
        ball2.isStaticObject(false);

        planet.setMass(1000f);
        planet.setRestitution(0.3f);

        ball.setMass(1.0f);
        ball.setRestitution(1f);

        ball2.setMass(1.0f);
        ball2.setRestitution(1f);

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
        ballCollider = ball.getCollider();
        ball2Collider = ball2.getCollider();

        ballCollider.setCollisionListener(listener);
        ball2Collider.setCollisionListener(listener);
    }

    static void initiatePhysicsProcesses() {
        addObjectsToCollisionSystem();
        CollisionDetectionSystem.initializeWorldCollisionDetectionSystem();
    }

    private static void addObjectsToCollisionSystem() {
        CollisionDetectionSystem.addObject(planet);
        CollisionDetectionSystem.addObject(ball);
        CollisionDetectionSystem.addObject(ball2);
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}
