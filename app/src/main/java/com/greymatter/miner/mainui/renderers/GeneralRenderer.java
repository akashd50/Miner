package com.greymatter.miner.mainui.renderers;

import com.greymatter.miner.containers.ActiveObjectsContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;

import java.util.ArrayList;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import static com.greymatter.miner.game.GC.MAIN_CHARACTER;
import static com.greymatter.miner.game.GC.PLANET;
import static com.greymatter.miner.game.GC.TEST_LINE;

public class GeneralRenderer extends AbstractRenderer {

    public GeneralRenderer(TouchHelper touchHelper, Camera camera) {
        super(touchHelper, camera);
    }

    public synchronized void onDrawFrame() {
        super.onDrawFrame();

        Drawable planet = GameObjectsContainer.get(PLANET).getDrawable();
        Drawable mainCharacter = GameObjectsContainer.get(MAIN_CHARACTER).getDrawable();
        Drawable testLine = GameObjectsContainer.get(TEST_LINE).getDrawable();

        /*<---------------------------------------update----------------------------------------->*/
        ArrayList<Vector3f> vertexData = new ArrayList<>();
        vertexData.add(mainCharacter.getRigidBody().getTranslation());
        Vector3f accPoint = new Vector3f(mainCharacter.getRigidBody().getTranslation());
        accPoint.add(VectorHelper.multiply(mainCharacter.getRigidBody().getVelocity(),40f));
        vertexData.add(accPoint);

        ((Line)testLine).setColor(new Vector4f(0f,1f,0f,1f)).setVertices(vertexData).build();
        testLine.getTransforms().translateTo(new Vector3f(0f,0f,2f));

        ((RadialGradient)GameObjectsContainer.get("g").getDrawable()).updateMidPoint(0.01f);

        /*<-----------------------------------------draw----------------------------------------->*/
        ActiveObjectsContainer.onDrawFrame(MainGLObjectsHelper.camera);
    }
}
