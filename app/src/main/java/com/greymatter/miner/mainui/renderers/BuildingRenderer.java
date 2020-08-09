package com.greymatter.miner.mainui.renderers;

import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;
import javax.vecmath.Vector4f;

public class BuildingRenderer extends AbstractRenderer {

    public BuildingRenderer(TouchHelper touchHelper, Camera camera) {
        super(touchHelper, camera);
    }

    public synchronized void onDrawFrame() {
        super.onDrawFrame();

        IGameObject planet = GameObjectsContainer.get(ObjId.PLANET);
        //Drawable testLine = GameObjectsContainer.get(ObjId.TEST_LINE).getDrawable();

        /*<---------------------------------------update----------------------------------------->*/
//        ((Line)testLine).setColor(new Vector4f(0f,0f,1f,1f))
//                .setVertices(planet.getRigidBody().asPolygonRB().getTransformedVertices()).build();

        /*<-----------------------------------------draw----------------------------------------->*/
        ToDrawContainer.onDrawFrame(MainGLObjectsHelper.camera);
    }
}
