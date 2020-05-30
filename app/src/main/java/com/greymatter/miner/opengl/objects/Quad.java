package com.greymatter.miner.opengl.objects;

import android.opengl.GLES30;
import android.opengl.Matrix;
import com.greymatter.miner.opengl.helpers.BufferHelper;
import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;

import javax.vecmath.*;

public class Quad {
	private Vector3f translation, rotation;
	private Vector2f scale;
	private Material material;
	private int vertexArray;
	private float[] modelMatrix;
	private Shader shader;

	public Quad(Vector3f position, Material material, Shader shader) {
		super();
		this.translation = position;
		this.rotation = new Vector3f(0f,0f,0f);
		this.scale = new Vector2f(1.0f,1.0f);
		this.material = material;
		this.shader = shader;
		this.modelMatrix = new float[16];

		init();
	}

	private void init() {
		float textureRatio = material.getDiffuseTexture().getRatio();
		if (textureRatio == 0.0f) {
			textureRatio = 1.0f;
		}

		float[] vertices = {1.0f * textureRatio, 1.0f, 0.0f,
							-1.0f * textureRatio, 1.0f, 0.0f,
							-1.0f * textureRatio, -1.0f, 0.0f,
							1.0f * textureRatio, 1.0f, 0.0f,
							-1.0f * textureRatio, -1.0f, 0.0f,
							1.0f * textureRatio, -1.0f, 0.0f};

		float[] uvs = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};

		vertexArray = GLBufferHelper.glGenVertexArray();
		GLBufferHelper.glBindVertexArray(vertexArray);

		int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(vertices, 3, shader, Constants.IN_POSITION);
		int uvBuffer = GLBufferHelper.putDataIntoArrayBuffer(uvs, 2, shader, Constants.IN_UV);

		GLBufferHelper.glUnbindVertexArray();
	}

	public void onDrawFrame() {
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix,0,translation.x,translation.y,translation.z);
		Matrix.scaleM(modelMatrix,0,scale.x,scale.y,1f);
		Matrix.rotateM(modelMatrix, 0, rotation.x, 1, 0, 0);
		Matrix.rotateM(modelMatrix, 0, rotation.y, 0, 1, 0);
		Matrix.rotateM(modelMatrix, 0, rotation.z, 0, 0, 1);

		GLBufferHelper.glBindVertexArray(vertexArray);
		ShaderHelper.setUniformMatrix4fv(shader, Constants.MODEL, modelMatrix);
		ShaderHelper.setMaterialProperties(shader, material);

		GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 6);

		GLBufferHelper.glUnbindVertexArray();
	}

	public void translateTo(Vector3f position) {
		this.translation = position;
	}

	public void translateBy(Vector3f translation) {
		this.translation.add(translation);
	}

	public void rotateTo(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void rotateBy(Vector3f rotation) {
		this.rotation.add(rotation);
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	public Material getMaterial() {
		return this.material;
	}

	public void setTexture(int tex) {
		this.material.getDiffuseTexture().setTexture(tex);
	}

	public int getVertexArrayObject() {
		return this.vertexArray;
	}

	public void scaleTo(Vector2f newScale) {
		this.scale = newScale;
	}

	public void scaleBy(Vector2f newScale) {
		this.scale.add(newScale);
	}
}
