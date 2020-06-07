package com.greymatter.miner.opengl.objects;

import android.opengl.Matrix;
import javax.vecmath.Vector3f;

public class Camera {
	private int width, height;
	private float[] projectionMatrix;
	private float[] viewMatrix;
	private Vector3f translation;
	private Vector3f lookAtVec;
	private Vector3f up;
	private float ratio, zoomValue;

	public Camera(int w, int h) {
		this.width = w;
		this.height = h;
		this.up = new Vector3f(0.0f, 1.0f, 0.0f);
		this.translation = new Vector3f(0f, 0f, 0f);
		this.lookAtVec = new Vector3f(0f, 0f, 0f);
		this.zoomValue = 1.0f;
		this.projectionMatrix = new float[16];
		this.viewMatrix = new float[16];

		this.ratio = (float)width/(float)height;

		this.updateProjectionMatrix();
		this.updateViewMatrix();
	}

	public void translateTo(Vector3f position) {
		this.translation = position;
		this.updateViewMatrix();
	}

	public void translateBy(Vector3f translation) {
		this.translation.add(translation);
		this.updateViewMatrix();
	}

	public void lookAt(Vector3f lookAt) {
		this.lookAtVec = lookAt;
		this.updateViewMatrix();
	}

	public void lookAt(Vector3f lookAt, Vector3f up) {
		this.lookAtVec = lookAt;
		this.up = up;
		this.updateViewMatrix();
	}

	public void onDrawFrame() {

	}

	public void onScreenSizeChanged(int w, int h) {
		width = w;
		height = h;

		this.updateProjectionMatrix();
		this.updateViewMatrix();
	}

	public void updateZoomValue(float val) {
		zoomValue += val;
		this.updateProjectionMatrix();
	}

	public void setZoomValue(float val) {
		zoomValue += val;
		this.updateProjectionMatrix();
	}

	private void updateProjectionMatrix() {
		Matrix.orthoM(projectionMatrix, 0, -1.0f*ratio*zoomValue,1.0f*ratio*zoomValue,
				-1.0f*zoomValue,1.0f*zoomValue,
				1f, 100f);
	}

	private void updateViewMatrix() {
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
