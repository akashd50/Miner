package com.greymatter.miner.opengl.objects;

import android.opengl.Matrix;
import javax.vecmath.Vector3f;

public class Camera {
	private int width;
	private int height;
	private float[] projectionMatrix;
	private float[] viewMatrix;
	private Vector3f translation;
	private Vector3f lookAtVec;
	private Vector3f up;

	public Camera(int w, int h) {
		this.width = w;
		this.height = h;
		this.up = new Vector3f(0.0f, 1.0f, 0.0f);
		this.translation = new Vector3f(0f, 0f, 0f);
		this.lookAtVec = new Vector3f(0f, 0f, 0f);

		projectionMatrix = new float[16];
		viewMatrix = new float[16];

		float ratio = (float)width/(float)height;

		Matrix.orthoM(projectionMatrix, 0, -1.0f*ratio,1.0f*ratio,-1.0f,1.0f,1f, 100f);
		Matrix.setLookAtM(viewMatrix, 0, translation.x, translation.y, translation.z,
														lookAtVec.x, lookAtVec.y, lookAtVec.z,
														up.x, up.y, up.z);
	}

	public void translateTo(Vector3f position) {
		this.translation = position;
		Matrix.setLookAtM(viewMatrix, 0,translation.x, translation.y, translation.z,
				lookAtVec.x, lookAtVec.y, lookAtVec.z,
				up.x, up.y, up.z);
	}

	public void translateBy(Vector3f translation) {
		this.translation.add(translation);
		Matrix.setLookAtM(viewMatrix, 0,translation.x, translation.y, translation.z,
				lookAtVec.x, lookAtVec.y, lookAtVec.z,
				up.x, up.y, up.z);
	}

	public void lookAt(Vector3f lookAt) {
		this.lookAtVec = lookAt;
		Matrix.setLookAtM(viewMatrix, 0,translation.x, translation.y, translation.z,
				lookAtVec.x, lookAtVec.y, lookAtVec.z,
				up.x, up.y, up.z);
	}

	public void lookAt(Vector3f lookAt, Vector3f up) {
		this.lookAtVec = lookAt;
		this.up = up;
		Matrix.setLookAtM(viewMatrix, 0,translation.x, translation.y, translation.z,
				lookAtVec.x, lookAtVec.y, lookAtVec.z,
				up.x, up.y, up.z);
	}

	public void onDrawFrame() {

	}

	public void onScreenSizeChanged(int w, int h) {
		width = w;
		height = h;

		Matrix.orthoM(projectionMatrix, 0, -1.0f,1.0f,-1.0f,1.0f,0.5f, 100f);
		Matrix.setLookAtM(viewMatrix, 0,translation.x, translation.y, translation.z,
				lookAtVec.x, lookAtVec.y, lookAtVec.z,
				up.x, up.y, up.z);
	}

	public float[] getViewMatrix() {
		return this.viewMatrix;
	}

	public float[] getProjectionMatrix() {
		return this.projectionMatrix;
	}

	public Vector3f getTranslation() {
		return this.translation;
	}

	public Vector3f getLookAtVec()
	{
		return this.lookAtVec;
	}

	public float getViewportHeight()
	{
		return this.height;
	}

	public float getViewportWidth() {
		return this.width;
	}

}
