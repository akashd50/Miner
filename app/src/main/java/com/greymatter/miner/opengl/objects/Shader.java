package com.greymatter.miner.opengl.objects;

import com.greymatter.miner.opengl.helpers.ResourceLoader;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import static com.greymatter.miner.opengl.helpers.Constants.FRAG_SHADER;
import static com.greymatter.miner.opengl.helpers.Constants.SHADERS;
import static com.greymatter.miner.opengl.helpers.Constants.VERTEX_SHADER;

public class Shader {
    private int program;

    public Shader(){}

    public Shader(String vsPath, String fsPath) {
        program = ShaderHelper.generateShadersAndProgram(ResourceLoader.loadFileResource(vsPath),
                ResourceLoader.loadFileResource(fsPath));
    }

    public Shader(String shaderName) {
        program = ShaderHelper.generateShadersAndProgram(ResourceLoader.loadFileResource(SHADERS+shaderName+VERTEX_SHADER),
                ResourceLoader.loadFileResource(SHADERS+shaderName+FRAG_SHADER));
    }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }
}
