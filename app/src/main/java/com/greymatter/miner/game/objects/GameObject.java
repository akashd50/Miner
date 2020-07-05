package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.Collider;

import java.util.ArrayList;

public abstract class GameObject {
    private Drawable objectDrawable;
    private ArrayList<String> objectTags;
    private String id;
    public GameObject(String id, Drawable drawable) {
        this.id = id;
        this.objectDrawable = drawable;
        this.objectTags = new ArrayList<>();
    }

    public void onDrawFrame() {
        objectDrawable.onDrawFrame();
    }

    public Drawable getDrawable() {
        return objectDrawable;
    }
    public Collider getCollider() {
        return objectDrawable.getCollider();
    }

    public String getId() {
        return this.id;
    }

    public GameObject withTag(String tag) {
        this.objectTags.add(tag);
        return this;
    }

    public boolean hasTag(String tag) {
        return this.objectTags.contains(tag);
    }

    public int getNumTags() {return this.objectTags.size();}

    public String toString() {
        return this.id;
    }
}
