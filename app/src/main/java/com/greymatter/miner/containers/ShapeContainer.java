package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ShaderId;
import com.greymatter.miner.enums.ShapeId;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.shader.Shader;

import java.util.ArrayList;

public class ShapeContainer {
    private static HashMapE<ShapeId, Shape> shapes;
    public static void add(Shape shader) {
        if(shapes == null) {
            shapes = new HashMapE<>();
        }
        shapes.put(shader.getId(), shader);
    }

    public static void remove(ShapeId id) {
        if(shapes !=null) {
            shapes.remove(id);
        }
    }

    public static Shape get(ShapeId id) {
        return shapes.get(id);
    }

    public static ArrayList<Shape> getAll() {
        return shapes.toList();
    }
}
