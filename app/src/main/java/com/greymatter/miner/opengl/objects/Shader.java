package com.greymatter.miner.opengl.objects;

import com.greymatter.miner.enums.ShaderId;
import com.greymatter.miner.helpers.ResourceLoader;
import com.greymatter.miner.helpers.ShaderHelper;
import static com.greymatter.miner.Path.FRAG_SHADER_EXT;
import static com.greymatter.miner.Path.SHADERS_F;
import static com.greymatter.miner.Path.VERTEX_SHADER_EXT;

public class Shader {
    private int program;
    private ShaderId id;

    public Shader(ShaderId id) {
        this.id = id;
    }

    public Shader load(String path) {
        program = ShaderHelper.generateShadersAndProgram(ResourceLoader.loadFileResource(SHADERS_F + path + VERTEX_SHADER_EXT),
                ResourceLoader.loadFileResource(SHADERS_F + path + FRAG_SHADER_EXT));
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

    public ShaderId getId() {
        return id;
    }
}
