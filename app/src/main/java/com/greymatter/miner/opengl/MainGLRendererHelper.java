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
import com.greymatter.miner.physics.generalhelpers.VectorHelper;
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
        ballCollider.updateTransformationsPerMovement(true);
        planet.setCollider(planetCollider);
        ball.setCollider(ballCollider);

        planet.setMass(1f);
        planet.setRestitution(2f);

        ball.setAcceleration(new Vector3f(0f,-0.001f, 0f));
        ball.setMass(1.0f);
        ball.setRestitution(2f);

        ballCollider.setCollisionListener(new OnCollisionListener() {
            @Override
            public void impulseResolution(CollisionEvent event) {
                if(event.getCollisionStatus()) {
                    if (event.getCollisionNormal() != null) {
                        Log.v("Collision Normal: ", event.getCollisionNormal().toString());

                        //resolve collision
                        Vector3f relativeVelocity = VectorHelper.sub(event.getLinkedObject().getVelocity(),
                                event.getAgainstObject().getVelocity());
                        float vectorAlongNormal = VectorHelper.dot(relativeVelocity, event.getCollisionNormal());

                        if (vectorAlongNormal > 0) return;

                        float e = Math.min(event.getLinkedObject().getRestitution(),
                                event.getAgainstObject().getRestitution());
                        float j = -(1 + e) * vectorAlongNormal;
                        j /= 1 / event.getLinkedObject().getMass() + 1 / event.getAgainstObject().getMass();

                        Vector3f impulse = VectorHelper.multiply(event.getCollisionNormal(), j);

                        event.getLinkedObject().updateVelocity(impulse);
                    }
                }
            }

            @Override
            public void positionalCorrection(CollisionEvent event) {
                float percent = 0.2f;
                float slop = 0.02f;
                float correction = Math.max( event.getPenDepth() - slop, 0.0f )
                        / (1/event.getLinkedObject().getMass() + 1/event.getAgainstObject().getMass()) * percent;
                Vector3f correctionVector = VectorHelper.multiply(event.getCollisionNormal(), correction);
                correctionVector = VectorHelper.multiply(correctionVector, 1/event.getLinkedObject().getMass());
                event.getLinkedObject().translateBy(correctionVector);
            }

        });
    }

    static void initiatePhysicsProcesses() {
        addObjectsToCollisionSystem();
        CollisionDetectionSystem.initializeWorldCollisionDetectionSystem();
    }

    static void addObjectsToCollisionSystem() {
        CollisionDetectionSystem.addObject(planet);
        CollisionDetectionSystem.addObject(ball);
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}
