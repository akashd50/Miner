package com.greymatter.miner.opengl.objects.materials.colored;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.opengl.objects.materials.Material;
import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Vector4f;

public abstract class ColoredMaterial extends Material {
    private int colorsIndex;
    private ArrayList<HashMapE<String,Vector4f>> colors;
    public ColoredMaterial(String id) {
        super(id);
        colorsIndex = 0;
        colors = new ArrayList<>();
    }

    protected ColoredMaterial addColor(int frameIndex, String tag, Vector4f color) {
        if(colors.isEmpty()) {
            colors.add(0, new HashMapE<>());
        }
        colors.get(frameIndex).put(tag, color);
        return this;
    }

    protected ArrayList<Vector4f> getColorsList(int index) {
        return colors.get(index).toList();
    }

    protected HashMap<String,Vector4f> getColorsHashMap(int index) {
        return colors.get(index);
    }

    public int updateCount() {return colorsIndex++;}

    public abstract ColoredMaterial addColor(String tag, Vector4f color);
    public abstract ColoredMaterial addColor(Vector4f color);
    public abstract ArrayList<Vector4f> getColorsList();
    public abstract HashMap<String,Vector4f> getColorsHashMap();
}
