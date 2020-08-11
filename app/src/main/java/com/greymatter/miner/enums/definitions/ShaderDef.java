package com.greymatter.miner.enums.definitions;

import com.greymatter.miner.Path;
import com.greymatter.miner.containers.ShaderContainer;
import com.greymatter.miner.opengl.shader.Shader;

public enum ShaderDef {
    SIMPLE_TRIANGLE_SHADER(Path.SIMPLE_TRIANGLE_SHADER),
    QUAD_SHADER(Path.QUAD_SHADER),
    THREE_D_OBJECT_SHADER(Path.THREE_D_OBJECT_SHADER),
    LINE_SHADER(Path.LINE_SHADER),
    GRADIENT_SHADER(Path.GRADIENT_SHADER),
    LIGHTING_SHADER(Path.LIGHTING_SHADER);

    public final String SHADER_PATH;
    ShaderDef(String path) {
        SHADER_PATH = path;
    }

    public static Shader create(ShaderDef id) {
        return new Shader(id).load(id.SHADER_PATH);
    }

    public static void loadAll() {
        ShaderDef[] ids = ShaderDef.values();
        for (int i = 0; i < ids.length; i++) {
            ShaderContainer.addShader(create(ids[i]));
        }
    }
}
