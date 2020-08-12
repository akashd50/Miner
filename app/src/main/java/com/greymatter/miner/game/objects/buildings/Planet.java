package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.game.objects.GenericObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.loaders.enums.definitions.DrawableDef;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public class Planet extends GameBuilding {
    private GroupMap<String, Vector3f> resources;
    public Planet(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public Planet(ObjId id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        IGameObject atmosphere = new GenericObject(DrawableDef.create(ObjId.ATMOSPHERE))
                                    .addTag(Tag.STATIC)
                                    .scaleTo(190f,190f).moveTo(0f,0f, -10f);
        atmosphere.getTransforms().copyTranslationFromParent(true);
        addChild(ObjId.ATMOSPHERE, atmosphere);
        addTag(Tag.STATIC);
        addTag(Tag.STATIC_PHYSICS_OBJECT);
    }

    public void addResource(String tag, Vector3f location) {
        resources.add(tag, location);
    }
}
