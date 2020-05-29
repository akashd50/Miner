package com.greymatter.miner.opengl.objects;

import android.opengl.GLES30;
import android.util.Log;

import com.greymatter.miner.opengl.helpers.ResourceLoader;

import static com.greymatter.miner.opengl.helpers.Constants.FRAG_SHADER;
import static com.greymatter.miner.opengl.helpers.Constants.SHADERS;
import static com.greymatter.miner.opengl.helpers.Constants.VERTEX_SHADER;

public class Shader {
    private int program;
    public Shader(){}

    public Shader(String vsPath, String fsPath) {
        program = generateShadersAndProgram(ResourceLoader.loadFileResource(vsPath),
                ResourceLoader.loadFileResource(fsPath));
    }

    public Shader(String shaderName) {
        program = generateShadersAndProgram(ResourceLoader.loadFileResource(SHADERS+"/"+shaderName+VERTEX_SHADER),
                ResourceLoader.loadFileResource(SHADERS+"/"+shaderName+FRAG_SHADER));
    }

    public void useProgram() { GLES30.glUseProgram(program); }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }

    private static int generateShadersAndProgram(String vs, String fs){
        int vertexShad = loadShader(GLES30.GL_VERTEX_SHADER,
                vs);
        int fragmentShad = loadShader(GLES30.GL_FRAGMENT_SHADER,
                fs);
        int mProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(mProgram, vertexShad);
        GLES30.glAttachShader(mProgram, fragmentShad);
        GLES30.glLinkProgram(mProgram);
        return mProgram;
    }

    private static int loadShader(int type, String shaderCode){
        int shader = GLES30.glCreateShader(type);
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);
        int[] success = new int[1];
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, success, 0);
        if (success[0]!=1) {
            String error = GLES30.glGetShaderInfoLog(shader);
            Log.v("ERROR::SHADER::COMPILATION_FAILED >>> ",error);
        }
        return shader;
    }
}
