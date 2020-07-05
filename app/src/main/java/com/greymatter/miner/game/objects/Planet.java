package com.greymatter.miner.game.objects;

import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector3f;

public class Planet extends GameBuilding {
    private GroupMap<String, Vector3f> resources;
    public Planet(Drawable drawable) {
        super(drawable.getId(), drawable);
    }
    public Planet(String id, Drawable drawable) {
        super(id, drawable);
    }

    public void addResource(String tag, Vector3f location) {
        resources.add(tag, location);
    }
}
