package com.greymatter.miner.game.objects;

import com.greymatter.miner.opengl.objects.drawables.Drawable;

public abstract class GameObject {
    private Drawable objectDrawable;
    private String id;
    public GameObject(String id, Drawable drawable) {
        this.id = id;
        this.objectDrawable = drawable;
    }

    public void onDrawFrame() {
        objectDrawable.onDrawFrame();
    }

    public Drawable getObjectDrawable() {
        return objectDrawable;
    }

    public String getId() {
        return this.id;
    }

    public String toString() {
        return this.id;
    }
}
