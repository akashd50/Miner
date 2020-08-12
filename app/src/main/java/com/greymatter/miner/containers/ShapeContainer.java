package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.loaders.enums.definitions.ShapeDef;
import com.greymatter.miner.opengl.objects.drawables.Shape;

import java.util.ArrayList;

public class ShapeContainer {
    private static HashMapE<ShapeDef, Shape> shapes;
    public static void add(Shape shader) {
        if(shapes == null) {
            shapes = new HashMapE<>();
        }
        shapes.put(shader.getId(), shader);
    }

    public static void remove(ShapeDef id) {
        if(shapes !=null) {
            shapes.remove(id);
        }
    }

    public static Shape get(ShapeDef id) {
        return shapes.get(id);
    }

    public static ArrayList<Shape> getAll() {
        return shapes.toList();
    }
}
