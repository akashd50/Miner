package com.greymatter.miner.opengl;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.opengl.objects.Line;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Object3D;
import com.greymatter.miner.opengl.objects.Quad;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.Triangle;
import com.greymatter.miner.physics.Collider;
import com.greymatter.miner.physics.CollisionHandler;
import com.greymatter.miner.physics.objects.CircleCollider;

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

    static void loadObjects() {
        triangle = new Triangle(simpleTriangleShader);
        backdropQuad = new Quad(backdropMaterial, quadShader);
        groundQuad = new Quad(groundMaterial, quadShader);
        characterQuad = new Quad(characterMaterial, quadShader);
        planet = new Object3D("objects/circle_sub_div_i.obj", characterMaterial, threeDObjectShader);
        ball = new Object3D("objects/circle_sub_div_i.obj", characterMaterial, threeDObjectShader);

        Line line = new Line(lineShader);
//        line.addVertex(((Object3D)ball).right);
//        line.addVertex(((Object3D)ball).top);
//        line.addVertex(((Object3D)ball).left);
//        line.addVertex(((Object3D)ball).bottom);
//        line.addVertex(((Object3D)ball).right);

        line.addVertices(((Object3D)ball).getOuterMesh());
        line.build();
        testLine = line;
    }

    static void loadPhysicsObjects() {
        planetCollider = new CircleCollider(1f);
        ballCollider = new CircleCollider(1f);
        planet.setCollider(planetCollider);
        ball.setCollider(ballCollider);
    }

    static void loadMaterials() {
        material = new Material("download.jpg","");
        backdropMaterial = new Material("backdrop_i.png", "");
        groundMaterial = new Material("ground_i.png", "");
        characterMaterial = new Material("character_i.png", "");
    }

    static void initiatePhysicsProcesses() {
        CollisionHandler.setUpCollisionCheckThread(planet, ball);
    }
}
