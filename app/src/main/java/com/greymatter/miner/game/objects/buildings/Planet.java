package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public class Planet extends GameBuilding {
    private GroupMap<String, Vector3f> resources;
    public Planet(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Planet(ObjId id, Drawable drawable) {
        super(id, drawable);
    }

    public void addResource(String tag, Vector3f location) {
        resources.add(tag, location);
    }
}
