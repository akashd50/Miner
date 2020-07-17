package com.greymatter.miner.containers;

import com.greymatter.miner.containers.datastructureextensions.HashMapE;
import com.greymatter.miner.enums.ShaderId;
import com.greymatter.miner.opengl.objects.Shader;

import java.util.ArrayList;

public class ShaderContainer {
    private static HashMapE<ShaderId, Shader> shaders;
    public static void addShader(Shader shader) {
        if(shaders == null) {
            shaders = new HashMapE<>();
        }
        shaders.put(shader.getId(), shader);
    }

    public static void removeShader(ShaderId id) {
        if(shaders !=null) {
            shaders.remove(id);
        }
    }

    public static Shader get(ShaderId id) {
        return shaders.get(id);
    }

    public static ArrayList<Shader> getAll() {
        return shaders.toList();
    }
}
