package com.greymatter.miner.helpers;

import android.opengl.Matrix;

import javax.vecmath.Vector3f;

public class MatrixHelper {
    public static void translateM(float[] mat, Vector3f translation) {
        Matrix.translateM(mat, 0, translation.x, translation.y, translation.z);
    }

    public static void translateM(float[] mat, float x, float y, float z) {
        Matrix.translateM(mat, 0, x, y, z);
    }

    public static void rotateM(float[] mat, Vector3f rotation) {
        Matrix.rotateM(mat, 0, rotation.x, 1, 0, 0);
        Matrix.rotateM(mat, 0, rotation.y, 0, 1, 0);
        Matrix.rotateM(mat, 0, rotation.z, 0, 0, 1);
    }

    public static void rotateM(float[] mat, float x, float y, float z) {
        Matrix.rotateM(mat, 0, x, 1, 0, 0);
        Matrix.rotateM(mat, 0, y, 0, 1, 0);
        Matrix.rotateM(mat, 0, z, 0, 0, 1);
    }

    public static void scaleM(float[] mat, Vector3f scale) {
        Matrix.scaleM(mat, 0, scale.x, scale.y, scale.z);
    }

    public static void scaleM(float[] mat, float x, float y, float z) {
        Matrix.scaleM(mat, 0, x, y, z);
    }
}
