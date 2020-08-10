package com.greymatter.miner.game.objects.ui;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public class GameSignal extends GameNotification {
    public GameSignal(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    public GameSignal() {
        super(ObjId.OBJECT_SIGNAL, DrawableDef.create(ObjId.OBJECT_SIGNAL));
        setDefaultScale(new Vector3f(0.5f,0.8f, 1f));
        addTag(Tag.SIGNAL);
    }
}
