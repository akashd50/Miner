package com.greymatter.miner.opengl.objects;

import android.opengl.Matrix;
import javax.vecmath.Vector3f;

public class Camera {
	private int width, height;
	private float[] projectionMatrix;
	private float[] viewMatrix;
	private Vector3f translation, lookAtVec, up;
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

	public void onSurfaceChanged(int w, int h) {
		this.width = w;
		this.height = h;
		this.ratio = (float)width/(float)height;
		this.updateProjectionMatrix();
		this.updateViewMatrix();
	}

	public void translateTo(Vector3f position) {
		this.translation = position;
		this.setLookAt(new Vector3f(position.x, position.y, lookAtVec.z));
		this.updateViewMatrix();
	}

	public void translateXY(Vector3f position) {
		this.translation.x = position.x;
		this.translation.y = position.y;
		this.setLookAt(new Vector3f(position.x, position.y, lookAtVec.z));
		this.updateViewMatrix();
	}

	public void translateBy(Vector3f translation) {
		this.translation.add(translation);
		this.setLookAt(new Vector3f(this.translation.x, this.translation.y, 0f));
		this.updateViewMatrix();
	}

	public void setLookAt(Vector3f lookAt) {
		this.lookAtVec = lookAt;
		this.updateViewMatrix();
	}

	public void updateLookAt(Vector3f lookAt) {
		this.lookAtVec.add(lookAt);
		this.updateViewMatrix();
	}

	public void setLookAt(Vector3f lookAt, Vector3f up) {
		this.lookAtVec = lookAt;
		this.up = up;
		this.updateViewMatrix();
	}

	public synchronized void setUpVector(Vector3f up) {
		this.up = up;
		this.updateViewMatrix();
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
		zoomValue = val;
		this.updateProjectionMatrix();
	}

	private void updateProjectionMatrix() {
		Matrix.setIdentityM(projectionMatrix, 0);
		Matrix.orthoM(projectionMatrix, 0, -1.0f*ratio*zoomValue,1.0f*ratio*zoomValue,
				-1.0f*zoomValue,1.0f*zoomValue,
				1f, 100f);
	}

	private void updateViewMatrix() {
		Matrix.setIdentityM(viewMatrix, 0);
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

	public Vector3f getLookAtVector()
	{
		return this.lookAtVec;
	}

	public synchronized Vector3f getUpVector() {
		return up;
	}

	public float getViewportHeight()
	{
		return this.height;
	}

	public float getViewportWidth() {
		return this.width;
	}

	public float getCameraLeft() {
		return (float) -1.0 * ratio * zoomValue;
	}

	public float getCameraRight() {
		return (float) 1.0 * ratio * zoomValue;
	}

	public float getCameraTop() {
		return (float) 1.0 * zoomValue;
	}

	public float getCameraBottom() {
		return (float) -1.0 * zoomValue;
	}

	public float getCameraWidth() {
		return getCameraRight()*2;
	}

	public float getCameraHeight() {
		return getCameraTop()*2;
	}
}
