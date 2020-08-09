package com.greymatter.miner.mainui.renderers;

import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;

import java.util.ArrayList;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class GeneralRenderer extends AbstractRenderer {
    private long prevCountFinishTime;
    private int fps, mainFPS;
    public GeneralRenderer(TouchHelper touchHelper, Camera camera) {
        super(touchHelper, camera);
    }

    public synchronized void onDrawFrame() {
        if(System.currentTimeMillis() - prevCountFinishTime < 1000){
               fps++;
        }else{
            mainFPS = fps;
            prevCountFinishTime = System.currentTimeMillis();
            fps = 0;
        }

        long currentFrameStartTime = System.currentTimeMillis();

        super.onDrawFrame();

        Drawable planet = GameObjectsContainer.get(ObjId.PLANET).getDrawable();
        IGameObject mainCharacter = GameObjectsContainer.get(ObjId.MAIN_CHARACTER);
//        Drawable testLine = GameObjectsContainer.get(ObjId.TEST_LINE).getDrawable();

        /*<---------------------------------------update----------------------------------------->*/
//        ArrayList<Vector3f> vertexData = new ArrayList<>();
//        vertexData.add(mainCharacter.getTransforms().getTranslation());
//        Vector3f accPoint = new Vector3f(mainCharacter.getTransforms().getTranslation());
//        accPoint.add(VectorHelper.multiply(mainCharacter.getRigidBody().getRBProps().getVelocity(),40f));
//        vertexData.add(accPoint);

//        ((Line)testLine).setColor(new Vector4f(0f,1f,0f,1f)).setVertices(GameObjectsContainer.get(ObjId.OBJECT_NOTIFICATION).getChild(ObjId.DIALOG_BUTTON_I).getRigidBody().asPolygonRB().getTransformedVertices()).build();
//        testLine.getTransforms().translateTo(new Vector3f(0f,0f,2f));

        /*<-----------------------------------------draw----------------------------------------->*/
        ToDrawContainer.onDrawFrame(MainGLObjectsHelper.camera);

        System.out.println("FPS: " + mainFPS);
        System.out.println("FRAME TIME: " + (System.currentTimeMillis() - currentFrameStartTime));
    }
}
