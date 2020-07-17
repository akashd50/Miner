package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.MatId;
import com.greymatter.miner.opengl.objects.materials.Material;

import java.util.ArrayList;

public class MaterialContainer {
    private static HashMapE<MatId, Material> materials;

    public static void add(Material material) {
        if(materials == null) {
            materials = new HashMapE<>();
        }
        materials.put(material.getId(), material);
    }

    public static void remove(MatId id) {
        if(materials !=null) {
            materials.remove(id);
        }
    }

    public static Material get(MatId id) {
        return materials.get(id);
    }

    public static ArrayList<Material> getAll() {
        return materials.toList();
    }
}
