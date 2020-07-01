package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.ArrayList;

public class CollisionSystemContainer {
    private static HashMapE<String, Drawable> drawables;

    public static void add(Drawable drawable) {
        if(drawables == null) {
            drawables = new HashMapE<>();
        }
        drawables.put(drawable.getId(), drawable);
    }

    public static void remove(String id) {
        Drawable removed = null;
        if(drawables !=null) {
            removed = drawables.remove(id);
        }
    }

    public static void onDrawFrame() {
        drawables.forEach((id, drawable) -> {
            drawable.onDrawFrame();
        });
    }

    public static ArrayList<Drawable> getAllExcept(Drawable drawable) {
        ArrayList<Drawable> toReturn = new ArrayList<Drawable>(drawables.toList());
        toReturn.remove(drawable);
        return toReturn;
    }

    public static Drawable get(String id) {
        return drawables.get(id);
    }

    public static ArrayList<Drawable> getAll() {
        return drawables.toList();
    }
}
