package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;

public class Miner extends GameBuilding {
    private GenericObject pipe;
    public Miner(Drawable drawable) {
        super(drawable.getId(), drawable);
    }

    public Miner(String id, Drawable drawable) {
        super(id, drawable);
    }

    private void initialize() {
        pipe = new GenericObject(new Line("pipe"));
        this.pipe.getTransforms().copyTranslationFromParent(true);
        this.addChild("PIPE", pipe);
    }
}
