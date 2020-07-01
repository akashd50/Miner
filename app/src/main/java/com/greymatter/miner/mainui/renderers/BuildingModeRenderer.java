package com.greymatter.miner.mainui.renderers;

import com.greymatter.miner.containers.DrawableContainer;
import com.greymatter.miner.game.containers.BackgroundObjectsContainer;
import com.greymatter.miner.game.containers.GameBuildingsContainer;
import com.greymatter.miner.game.containers.InteractiveGameObjectsContainer;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;

import static com.greymatter.miner.game.GC.MAIN_CHARACTER;
import static com.greymatter.miner.game.GC.PLANET;
import static com.greymatter.miner.game.GC.TEST_LINE;

public class BuildingModeRenderer extends AbstractRendererMode {

    public BuildingModeRenderer(TouchHelper touchHelper, Camera camera) {
        super(touchHelper, camera);
    }

    public synchronized void onDrawFrame() {
        super.onDrawFrame();

        Drawable planet = DrawableContainer.get(PLANET);
        Drawable mainCharacter = DrawableContainer.get(MAIN_CHARACTER);
        Drawable testLine = DrawableContainer.get(TEST_LINE);

        /*<---------------------------------------update----------------------------------------->*/
        ((Line)testLine).updateVertexData(planet.getCollider().asPolygonCollider().getTransformedVertices());

        /*<-----------------------------------------draw----------------------------------------->*/
        BackgroundObjectsContainer.onDrawFrameByShader(MainGLObjectsHelper.camera);
        GameBuildingsContainer.onDrawFrameByShader(MainGLObjectsHelper.camera);
        InteractiveGameObjectsContainer.onDrawFrameByShader(MainGLObjectsHelper.camera);
    }
}
