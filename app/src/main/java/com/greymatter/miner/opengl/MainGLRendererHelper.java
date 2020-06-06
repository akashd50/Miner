package com.greymatter.miner.opengl;

import android.util.Log;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.Object3DHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.opengl.objects.Line;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Object3D;
import com.greymatter.miner.opengl.objects.Quad;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.Triangle;
import com.greymatter.miner.physics.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.PolygonCollider;
import com.greymatter.miner.physics.objects.OnCollisionListener;

import javax.vecmath.Vector3f;

class MainGLRendererHelper {
    static Camera camera;
    static Shader simpleTriangleShader, quadShader, threeDObjectShader, lineShader;
    static Triangle triangle;
    static Material material, backdropMaterial, groundMaterial, characterMaterial;
    static Drawable backdropQuad, groundQuad, characterQuad, planet, ball, testLine;
    static Collider planetCollider, ballCollider;

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
        planet = new Object3D("objects/circle_sub_div_i.obj", characterMaterial, threeDObjectShader);
        ball = new Object3D("objects/circle_sub_div_i.obj", characterMaterial, threeDObjectShader);
        //testLine = new Line(lineShader).addVertices(((Object3D)ball).getOuterMesh()).build();
    }

    static void loadPhysicsObjects() {
        planetCollider = new PolygonCollider(((Object3D)planet).getOuterMesh());
        ballCollider = new PolygonCollider(Object3DHelper.simplify(((Object3D)ball).getOuterMesh(),0.1f));
        planetCollider.updateTransformationsPerMovement(false);
        ballCollider.updateTransformationsPerMovement(false);
        planet.setCollider(planetCollider);
        ball.setCollider(ballCollider);
        ball.getCollider().setAcceleration(new Vector3f(0f,-0.001f, 0f));

        ballCollider.setCollisionListener(new OnCollisionListener() {
            @Override
            public void onCollision(CollisionEvent event) {
                if(event.getCollisionStatus()) {
                    event.getLinkedObject().translateTo(new Vector3f(0.5f,1f,0f));
                    event.getLinkedObject().setVelocity(new Vector3f());
                }
            }
        });
    }

    static void initiatePhysicsProcesses() {
        addObjectsToCollisionSystem();
        CollisionDetectionSystem.initializeWorldCollisionDetectionSystem();
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }

    static void addObjectsToCollisionSystem() {
        CollisionDetectionSystem.addObject(planet);
        CollisionDetectionSystem.addObject(ball);
    }
}
