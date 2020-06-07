package com.greymatter.miner.containers;

import com.greymatter.miner.opengl.objects.Drawable;

import java.util.ArrayList;
import java.util.HashMap;

public class DrawableContainer {
    private static HashMap<String, ArrayList<Drawable>> drawablesGroupedByShader;

    public static void addNew(Drawable drawable) {
        if(drawablesGroupedByShader == null) {
            drawablesGroupedByShader = new HashMap<>();
        }

        if(drawablesGroupedByShader.containsKey(drawable.getShader().toString())) {
            drawablesGroupedByShader.get(drawable.getShader().toString()).add(drawable);
        }else{
            ArrayList<Drawable> toAdd = new ArrayList<>();
            toAdd.add(drawable);
            drawablesGroupedByShader.put(drawable.getShader().toString(), toAdd);
        }
    }
}
