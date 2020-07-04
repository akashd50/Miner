package com.greymatter.miner.mainui.renderers;

import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.game.containers.GameObjectsContainer;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;

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
        Vector3f fromCenterToCam = VectorHelper.sub(MainGLObjectsHelper.camera.getTranslation(), planet.getCollider().getTranslation());
        fromCenterToCam.normalize();
        MainGLObjectsHelper.camera.setUpVector(fromCenterToCam);

        ArrayList<Vector3f> vertexData = new ArrayList<>();
        vertexData.add(mainCharacter.getCollider().getTranslation());
        Vector3f accPoint = new Vector3f(mainCharacter.getCollider().getTranslation());
        accPoint.add(VectorHelper.multiply(mainCharacter.getCollider().getVelocity(),40f));
        vertexData.add(accPoint);

        ((Line)testLine).withColor(new Vector4f(0f,1f,0f,1f)).updateVertexData(vertexData);
        testLine.getCollider().translateTo(new Vector3f(0f,0f,1f));

        /*<-----------------------------------------draw----------------------------------------->*/
        ToDrawContainer.onDrawFrame(MainGLObjectsHelper.camera);
    }
}
