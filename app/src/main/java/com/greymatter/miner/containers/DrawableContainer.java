package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.GroupMap;
import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.game.objects.GameBuilding;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.ArrayList;

public class DrawableContainer {
    private static HashMapE<String, Drawable> drawables;
    private static GroupMap<String, Drawable> groupedByShader;

    public static void add(Drawable drawable) {
        if(drawables == null) {
            drawables = new HashMapE<>();
        }

        if(groupedByShader == null) {
            groupedByShader = new GroupMap<>();
        }

        drawables.put(drawable.getId(), drawable);
        groupedByShader.add(drawable.getShader().getId(), drawable);
    }

    public static void remove(String id) {
        Drawable removed = null;
        if(drawables !=null) {
            removed = drawables.remove(id);
        }

        if(groupedByShader != null && removed != null) {
            groupedByShader.delete(id,removed);
        }
    }

    public static void onDrawFrame() {
        drawables.forEach((id, drawable) -> {
            drawable.onDrawFrame();
        });
    }

    public static void onDrawFrameByShader(Camera camera) {
        groupedByShader.forEach((shaderId, drawables) -> {
            Shader toUse = ShaderContainer.get(shaderId);
            ShaderHelper.useProgram(toUse);
            ShaderHelper.setCameraProperties(toUse, camera);

            drawables.forEach(Drawable::onDrawFrame);
        });
    }

    public static Drawable get(String id) {
        return drawables.get(id);
    }

    public static ArrayList<Drawable> getAll() {
        return drawables.toList();
    }
}
