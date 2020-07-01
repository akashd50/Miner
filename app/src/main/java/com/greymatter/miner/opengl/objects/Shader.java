package com.greymatter.miner.opengl.objects;

import com.greymatter.miner.opengl.helpers.ResourceLoader;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import static com.greymatter.miner.opengl.Constants.FRAG_SHADER;
import static com.greymatter.miner.opengl.Constants.SHADERS;
import static com.greymatter.miner.opengl.Constants.VERTEX_SHADER;

public class Shader {
    private int program;
    private String id;

    public Shader(){}

    public Shader(String vsPath, String fsPath) {
        this.id = vsPath + " | "+ fsPath;
        program = ShaderHelper.generateShadersAndProgram(ResourceLoader.loadFileResource(vsPath),
                ResourceLoader.loadFileResource(fsPath));
    }

    public Shader(String id) {
        this.id = id;
        program = ShaderHelper.generateShadersAndProgram(ResourceLoader.loadFileResource(SHADERS+ id +VERTEX_SHADER),
                ResourceLoader.loadFileResource(SHADERS+ id +FRAG_SHADER));
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
