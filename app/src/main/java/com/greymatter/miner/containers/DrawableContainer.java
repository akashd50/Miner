package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.ArrayList;

public class DrawableContainer {
    private static HashMapE<String, Drawable> drawables;

    public static void addDrawable(Drawable drawable) {
        if(drawables == null) {
            drawables = new HashMapE<>();
        }
        drawables.put(drawable.getId(), drawable);
    }

    public static void removeDrawable(String id) {
        if(drawables !=null) {
            drawables.remove(id);
        }
    }

    public static void onDrawFrame() {
        drawables.forEach((id, drawable) -> {
            drawable.onDrawFrame();
        });
    }

    public ArrayList<Drawable> getAll() {
        return drawables.toList();
    }
}
