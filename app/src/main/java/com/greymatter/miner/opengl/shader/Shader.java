package com.greymatter.miner.opengl.shader;

import com.greymatter.miner.loaders.enums.definitions.ShaderDef;
import com.greymatter.miner.helpers.ResourceLoader;
import static com.greymatter.miner.Path.FRAG_SHADER_EXT;
import static com.greymatter.miner.Path.VERTEX_SHADER_EXT;

public class Shader {
    private int program;
    private ShaderDef id;

    public Shader(ShaderDef id) {
        this.id = id;
    }

    public Shader load(String path) {
        program = ShaderHelper.generateShadersAndProgram(ResourceLoader.loadFileResource(path + VERTEX_SHADER_EXT),
                ResourceLoader.loadFileResource(path + FRAG_SHADER_EXT));
        return this;
    }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }

    public String toString() {
        return id.toString();
    }

    public ShaderDef getId() {
        return id;
    }
}
