package com.greymatter.miner.opengl.objects;

import com.greymatter.miner.helpers.ResourceLoader;
import com.greymatter.miner.helpers.ShaderHelper;
import static com.greymatter.miner.Res.FRAG_SHADER_EXT;
import static com.greymatter.miner.Res.SHADERS_F;
import static com.greymatter.miner.Res.VERTEX_SHADER_EXT;

public class Shader {
    private int program;
    private String id;

    public Shader(){}

    public Shader(String vsPath, String fsPath) {
        this.id = vsPath + " | "+ fsPath;
        program = ShaderHelper.generateShadersAndProgram(ResourceLoader.loadFileResource(vsPath),
                ResourceLoader.loadFileResource(fsPath));
    }

    public Shader(String path) {
        this.id = path;
        program = ShaderHelper.generateShadersAndProgram(ResourceLoader.loadFileResource(SHADERS_F + path + VERTEX_SHADER_EXT),
                ResourceLoader.loadFileResource(SHADERS_F + path + FRAG_SHADER_EXT));
    }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }

    public String toString() {
        return id;
    }
    public String getId() {
        return id;
    }

}
