package com.greymatter.miner.opengl.objects.materials.colored;

import com.greymatter.miner.enums.definitions.MaterialDef;
import com.greymatter.miner.helpers.BufferHelper;

import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Vector4f;

public class StaticColoredMaterial extends ColoredMaterial {

    public StaticColoredMaterial(MaterialDef id) {
        super(id);
    }

    @Override
    public StaticColoredMaterial addColor(String tag, Vector4f color) {
        super.addColor(0, tag, color);
        return this;
    }

    @Override
    public StaticColoredMaterial addColor(Vector4f color) {
        super.addColor(0, String.valueOf(updateCount()), color);
        return this;
    }

    @Override
    public ArrayList<Vector4f> getColorsList() {
        return super.getColorsList(0);
    }

    @Override
    public HashMap<String, Vector4f> getColorsHashMap() {
        return super.getColorsHashMap(0);
    }

    public float[] getColorsAsArray() {
        return BufferHelper.vec4AsFloatArray(getColorsList(0));
    }

    public Vector4f getColor(int index) {
        return super.getColorsList(0).get(index);
    }

    public boolean isSingleColored() {
        return getColorsList().size() == 1;
    }
}
