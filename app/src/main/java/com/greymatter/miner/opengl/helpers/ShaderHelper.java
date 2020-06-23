package com.greymatter.miner.opengl.helpers;

import android.opengl.GLES30;
import android.util.Log;

import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Shader;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class ShaderHelper {

    public static void useProgram(Shader shader) {
        GLES30.glUseProgram(shader.getProgram());
    }

    public static void setUniformMatrix4fv(Shader shader, String fname, float[] matrix) {
        int location = GLES30.glGetUniformLocation(shader.getProgram(), fname);
        GLES30.glUniformMatrix4fv(location, 1, false, matrix, 0);
    }

    public static void setUniformVec3(Shader shader, String fname, Vector3f vec) {
        int location = GLES30.glGetUniformLocation(shader.getProgram(), fname);
        GLES30.glUniform3f(location, vec.x, vec.y, vec.z);
    }

    public static void setUniformVec4(Shader shader, String fname, Vector4f vec) {
        int location = GLES30.glGetUniformLocation(shader.getProgram(), fname);
        GLES30.glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
    }

    public static void setUniformFloat(Shader shader, String fname, float f) {
        int location = GLES30.glGetUniformLocation(shader.getProgram(), fname);
        GLES30.glUniform1f(location, f);
    }

    public static void setAttribVec3(Shader shader,String fname, Vector3f vec) {
        int location = GLES30.glGetAttribLocation(shader.getProgram(), fname);
        GLES30.glVertexAttrib3f(location, vec.x, vec.y, vec.z);
    }

    public static void setUniformInt(Shader shader,String fname, int i) {
        int location = GLES30.glGetUniformLocation(shader.getProgram(), fname);
        GLES30.glUniform1i(location, i);
    }

    public static void setTextureUnit2D(int texUnit, int texture) {
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + texUnit);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture);
    }

    public static void setTextureUnit2D(Shader shader,String textureUnit, int texUnit, int texture) {
        setUniformInt(shader, textureUnit, texUnit);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + texUnit);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture);
    }

    public static void setCameraProperties(Shader shader, Camera camera) {
        setUniformMatrix4fv(shader, Constants.PROJECTION, camera.getProjectionMatrix());
        setUniformMatrix4fv(shader,Constants.VIEW, camera.getViewMatrix());
        //setUniformVec3("cameraPosWS", *camera.getTranslation());
    }

    public static void setMaterialProperties(Shader shader, Material material) {
        setUniformInt(shader, "material.diffuseTexture", 0);
        setUniformInt(shader, "material.specularTexture", 1);

        if (material.hasDiffuseTexture()) {
            setTextureUnit2D(0, material.getDiffuseTexture().getTextureId());
        }
        if (material.hasRoughnessTexture()) {
            setTextureUnit2D(1, material.getRoughnessTexture().getTextureId());
        }
//        setUniformFloat(shader, "material.specMultiplier", material.getShinniness());
//        setUniformVec3(shader, "material.diffuse", material.getDiffuse());
//        setUniformVec3(shader, "material.specular", material.getSpecular());
//        setUniformVec3(shader, "material.ambient", material.getAmbient());
    }


    public static int generateShadersAndProgram(String vs, String fs){
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

    public static int loadShader(int type, String shaderCode){
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
