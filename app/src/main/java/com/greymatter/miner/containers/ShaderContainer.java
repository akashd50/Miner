package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.definitions.ShaderDef;
import com.greymatter.miner.opengl.shader.Shader;

import java.util.ArrayList;

public class ShaderContainer {
    private static HashMapE<ShaderDef, Shader> shaders;
    public static void addShader(Shader shader) {
        if(shaders == null) {
            shaders = new HashMapE<>();
        }
        shaders.put(shader.getId(), shader);
    }

    public static void removeShader(ShaderDef id) {
        if(shaders !=null) {
            shaders.remove(id);
        }
    }

    public static Shader get(ShaderDef id) {
        return shaders.get(id);
    }

    public static ArrayList<Shader> getAll() {
        return shaders.toList();
    }
}
