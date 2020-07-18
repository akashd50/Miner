package com.greymatter.miner.mainui.renderers;

import android.util.Log;
import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.containers.ActiveResourcesContainer;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.enums.ShaderId;
import com.greymatter.miner.enums.ShapeId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.Animated;
import com.greymatter.miner.game.objects.GameLight;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.InteractiveObject;
import com.greymatter.miner.game.objects.buildings.MainBase;
import com.greymatter.miner.game.objects.buildings.Planet;
import com.greymatter.miner.game.objects.buildings.Scanner;
import com.greymatter.miner.game.objects.Static;
import com.greymatter.miner.game.objects.resources.CoalBlock;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.animators.BooleanAnimator;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.animators.FloatValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.materials.colored.StaticColoredMaterial;
import com.greymatter.miner.opengl.objects.materials.textured.AnimatedTexturedMaterial;
import com.greymatter.miner.opengl.objects.animators.IntegerValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.objects.drawables.TextureEdgedPolygon;
import com.greymatter.miner.opengl.objects.materials.textured.StaticTexturedMaterial;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.OnCollisionListener;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;
import com.greymatter.miner.Path;

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
        ShaderContainer.addShader(new Shader(ShaderId.SIMPLE_TRIANGLE_SHADER).load(Path.SIMPLE_TRIANGLE_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.QUAD_SHADER).load(Path.QUAD_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.THREE_D_OBJECT_SHADER).load(Path.THREE_D_OBJECT_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.LINE_SHADER).load(Path.LINE_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.CIRCLE_GRADIENT_SHADER).load(Path.CIRCLE_GRADIENT_SHADER));
        ShaderContainer.addShader(new Shader(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER).load(Path.THREE_D_OBJECT_W_LIGHTING_SHADER));
    }

    static void loadMaterials() {
        MaterialContainer.add(new StaticTexturedMaterial(MatId.GROUND_MATERIAL).attachDiffuseTexture(Path.GROUND_I));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.ATMOSPHERE_MATERIAL).attachDiffuseTexture(Path.ATM_RADIAL_II));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.MAIN_BASE_MATERIAL).attachDiffuseTexture(Path.MAIN_BASE_FINAL));
        MaterialContainer.add(new StaticTexturedMaterial(MatId.PLANET_GRASS_MATERIAL_I).attachDiffuseTexture(Path.GRASS_PATCH_I));
        MaterialContainer.add(new StaticColoredMaterial(MatId.GRADIENT_COLOR_MAT)
                        .addColor(ShaderConst.GRADIENT_CENTER_COLOR, new Vector4f(0f,0.2f,0.2f,0.6f))
                        .addColor(ShaderConst.GRADIENT_MID_COLOR, new Vector4f(0f,0.4f,0.3f,0.4f))
                        .addColor(ShaderConst.GRADIENT_EDGE_COLOR, new Vector4f(0f,0.7f,0.3f,0.4f)));

        MaterialContainer.add(new AnimatedTexturedMaterial(MatId.TREE_MATERIAL)
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_i.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_ii.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_iii.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_iv.png")
                .addDiffuseTextureFrame(Path.TREE_ANIM_I_F + "c_tree_anim_v.png")
                .withAnimationHandler(new IntegerValueAnimator().withFPS(6).withTotalFrames(5)));
    }

    static void loadObjects() {
        Shape boxShape = new Shape(ShapeId.COLLISION_BOX).loadObj(Path.BOX).build();
        Shape atmSimpleCircle = new Shape(ShapeId.CIRCLE_SIMPLE).loadObj(Path.CIRCLE_SIMPLE).build();
        Shape uvmapped = new Shape(ShapeId.UV_MAPPED_BOX).loadObj(Path.UV_MAPPED_BOX).build();
        Shape circleSubDivIII = new Shape(ShapeId.CIRCLE_SUB_III).loadObj(Path.CIRCLE_SUB_DIV_III).build();
        Shape circleSubDivI = new Shape(ShapeId.CIRCLE_SUB_I).loadObj(Path.CIRCLE_SUB_DIV_I).build();
        Shape shape = new Shape(ShapeId.PIE_45).loadPie(45f,1f).build();
        Shape circleEdge = new Shape(ShapeId.CIRCLE_EDGE).loadEdgeOutline(circleSubDivIII, 0.01f).build();

        GameObjectsContainer.add(new CoalBlock(new Obj(ObjId.COAL_BLOCK_I)
                                            .setShape(boxShape)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .addTag(Tag.RESOURCE_OBJECT));

        GameObjectsContainer.add(new Animated(new RadialGradient(ObjId.PIE_GRADIENT_I)
                                            .setRadius(1f)
                                            .setShape(shape)
                                            .setMaterial(MaterialContainer.get(MatId.GRADIENT_COLOR_MAT))
                                            .setShader(ShaderContainer.get(ShaderId.CIRCLE_GRADIENT_SHADER))
                                            .build()));

        GameObjectsContainer.add(new Static(new Obj(ObjId.ATMOSPHERE)
                                            .setShape(atmSimpleCircle)
                                            .setMaterial(MaterialContainer.get(MatId.ATMOSPHERE_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_SHADER)).build())
                                            .addTag(Tag.STATIC));

        GameObjectsContainer.add(new Planet(new Obj(ObjId.PLANET)
                                            .setShape(circleSubDivIII)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_SHADER)).build())
                                            .addTag(Tag.STATIC)
                                            .addTag(Tag.PHYSICS_OBJECT));

        GameObjectsContainer.add(new MainBase(new Obj(ObjId.MAIN_BASE)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(MatId.MAIN_BASE_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .attachPolygonTouchChecker()
                                            .addTag(Tag.STATIC)
                                            .addTag(Tag.PLACABLE_GAME_BUILDING)
                                            .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new InteractiveObject(new Obj(ObjId.MAIN_CHARACTER)
                                            .setShape(boxShape)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build())
                                            .attachPolygonTouchChecker()
                                            .addTag(Tag.PHYSICS_OBJECT)
                                            .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new Scanner(new Obj(ObjId.SCANNER_I)
                                            .setShape(circleSubDivI)
                                            .setMaterial(MaterialContainer.get(MatId.GROUND_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_SHADER)).build())
                                .setRangeDrawable(GameObjectsContainer.get(ObjId.PIE_GRADIENT_I))
                                .setRangeDrawableAnimator(new FloatValueAnimator()
                                                            .withFPS(60)
                                                            .setBounds(0f,1f)
                                                            .setPerFrameIncrement(0.01f))
                                .setValueAnimator(new BooleanAnimator().withFPS(10))
                                .attachPolygonTouchChecker()
                                .addTag(Tag.PLACABLE_GAME_BUILDING)
                                .addTag(Tag.PHYSICS_OBJECT)
                                .setOnTouchListener(new GeneralTouchListener()));

        GameObjectsContainer.add(new Animated(new Obj(ObjId.TREE_I)
                                            .setShape(uvmapped)
                                            .setMaterial(MaterialContainer.get(MatId.TREE_MATERIAL))
                                            .setShader(ShaderContainer.get(ShaderId.THREE_D_OBJECT_W_LIGHTING_SHADER)).build()));

        GameObjectsContainer.add(new Static(new TextureEdgedPolygon(ObjId.PLANET_GRASS_LAYER)
                                            .setShape(circleEdge)
                                            .setMaterial(MaterialContainer.get(MatId.PLANET_GRASS_MATERIAL_I))
                                            .setShader(ShaderContainer.get(ShaderId.QUAD_SHADER)).build()));

        GameObjectsContainer.add(new GameLight(new Obj(ObjId.MAIN_BASE_LIGHT_I))
                                                .setRadius(1f)
                                                .setColor(1f,0f,0f,1f)
                                                .setInnerCutoff(0.2f).setOuterCutoff(0.8f)
                                                .attachTo(GameObjectsContainer.get(ObjId.MAIN_BASE).asGameBuilding(), new Vector2f(-2.3f,0.3f)));
    }

    static void finishObjectsSetup() {
        GameObjectsContainer.get(ObjId.COAL_BLOCK_I).scaleTo(0.2f,0.2f).moveTo(10f,-4f, 1f);

        GameObjectsContainer.get(ObjId.ATMOSPHERE).scaleTo(190f,190f).moveTo(0f,-120.5f, -10f);

        GameObjectsContainer.get(ObjId.PLANET).scaleTo(120f,120f).moveTo(0f,-120.5f, 0f);

        GameObjectsContainer.get(ObjId.MAIN_CHARACTER).scaleTo(0.5f,0.5f).moveBy(-0.5f,0f,0f);

        GameObjectsContainer.get(ObjId.SCANNER_I).scaleTo(0.6f,0.6f).moveBy(-0.5f,2f,0f)/*.upgrade(4)*/;

        GameObjectsContainer.get(ObjId.MAIN_BASE).scaleTo(4f,2.7f).moveTo(-2.4f,2f,-5f);

        GameObjectsContainer.get(ObjId.PLANET_GRASS_LAYER).scaleTo(119.65f,119.65f).moveTo(0f,-120.5f, 1f);

        GameObjectsContainer.get(ObjId.TREE_I).scaleTo(1f,1.5f).moveTo(0f,0f, -6f);

        Drawable mainCharacter = GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getDrawable();
        GameObjectsContainer.add(new InteractiveObject(new Line(ObjId.TEST_LINE)
                            .setShader(ShaderContainer.get(ShaderId.LINE_SHADER))
                            .addVertices(mainCharacter.getRigidBody().asPolygonRB().getTransformedVertices())
                            .build()));

        GameObjectsContainer.get(ObjId.PIE_GRADIENT_I).moveTo(0,0f, 2f).scaleTo(4f,3f);

        GameObjectsContainer.runPostInitialization();

        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TEST_LINE));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.ATMOSPHERE));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.MAIN_CHARACTER));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.PLANET_GRASS_LAYER));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.TREE_I));
        ToDrawContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I));

        ActiveResourcesContainer.add(GameObjectsContainer.get(ObjId.COAL_BLOCK_I).asResourceBlock());
    }

    static void loadPhysicsObjects() {
        Drawable planet = GameObjectsContainer.get(ObjId.PLANET).getDrawable();
        Drawable mainCharacter = GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getDrawable();
        Drawable mainBase = GameObjectsContainer.get(ObjId.MAIN_BASE).getDrawable();
        Drawable sampleScanner = GameObjectsContainer.get(ObjId.SCANNER_I).getDrawable().asObject3D().attachOptimisedPolygonCollider(0.1f);

        planet.getRigidBody().isStaticObject(true)
                            .getRBProps().setMass(1000000f).setRestitution(0.3f);

        mainCharacter.getRigidBody().isStaticObject(false)
                                    .getRBProps().setMass(1.5f).setRestitution(0.5f);

        sampleScanner.getRigidBody().isStaticObject(false)
                                    .getRBProps().setMass(1.0f).setRestitution(0.5f);

        //assign colliders and listeners
        mainCharacter.getRigidBody().setCollisionListener(new GeneralCollisionListener());
        sampleScanner.getRigidBody().setCollisionListener(new GeneralCollisionListener());
    }

    static void initiatePhysicsSystem() {
        CollisionSystemContainer.add(GameObjectsContainer.get(ObjId.PLANET).getRigidBody());
        CollisionSystemContainer.add(GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getRigidBody());
        CollisionDetectionSystem.initialize();
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}

class GeneralCollisionListener implements OnCollisionListener {
    @Override
    public void impulseResolution(CollisionEvent event) {
        OnCollisionListener.super.impulseResolutionDefault(event);
    }
    @Override
    public void positionalCorrection(CollisionEvent event) {
        OnCollisionListener.super.positionalCorrectionDefault(event);
    }
}

class GeneralTouchListener implements OnTouchListener {
    @Override
    public void onTouchDown(GameObject gameObject, Vector2f pointer) {

    }

    @Override
    public void onTouchMove(GameObject gameObject, Vector2f pointer) {
        OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
    }

    @Override
    public void onTouchUp(GameObject gameObject, Vector2f pointer) {

    }
}

class BuildingModeTouchListener implements OnTouchListener {
    @Override
    public void onTouchDown(GameObject gameObject, Vector2f pointer) {

    }

    @Override
    public void onTouchMove(GameObject gameObject, Vector2f pointer) {
        OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
    }

    @Override
    public void onTouchUp(GameObject gameObject, Vector2f pointer) {

    }
}
