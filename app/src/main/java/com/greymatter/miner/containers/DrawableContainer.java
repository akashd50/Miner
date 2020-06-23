package com.greymatter.miner.containers;

import com.greymatter.miner.game.objects.GameBuilding;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.HashMap;

public class DrawableContainer {
    private static HashMap<String, Drawable> drawables;

    public static void addDrawable(Drawable drawable) {
        if(drawables == null) {
            drawables = new HashMap<>();
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
}
