package com.greymatter.miner.opengl.objects;

import android.opengl.GLES30;
import android.opengl.Matrix;
import com.greymatter.miner.opengl.helpers.BufferHelper;
import com.greymatter.miner.opengl.helpers.Constants;
import javax.vecmath.*;

public class Quad {
	private Vector3f translation, rotation, scale;
	private Material material;
	private int[] vertexArrayObject, vertexBufferObject, uvBufferObject;
	private float[] modelMatrix;
	private Shader shader;
	public int TEXTURE;

	public Quad(Vector3f position, Material material)
	{
		super();
		TEXTURE = 0;
		translation = position;
		rotation = new Vector3f();
		scale = new Vector3f();
		this.material = material;

//		init();
	}
	public final void init()
	{
		float textureRatio = material.getDiffuseTexture().getRatio();
		if (textureRatio == 0.0F)
		{
			textureRatio = 1.0F;
		}

		float[] vertices = {1.0f * textureRatio, 1.0f, 0.0f,
							-1.0f * textureRatio, 1.0f, 0.0f,
							-1.0f * textureRatio, -1.0f, 0.0f,
							1.0f * textureRatio, 1.0f, 0.0f,
							-1.0f * textureRatio, -1.0f, 0.0f,
							1.0f * textureRatio, -1.0f, 0.0f};
		float[] uvs = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};


		GLES30.glGenVertexArrays(1, vertexArrayObject, 1);
		GLES30.glBindVertexArray(vertexArrayObject[0]);

		GLES30.glGenBuffers(1, vertexBufferObject, 1);
		GLES30.glGenBuffers(1, uvBufferObject, 1);

		GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vertexBufferObject[0]);
		GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, (Float.SIZE / Byte.SIZE) * 18,
				BufferHelper.asFloatBuffer(vertices), GLES30.GL_STATIC_DRAW);
		GLES30.glVertexAttribPointer(0, 3,
				GLES30.GL_FLOAT, false,
				3 * Constants.SIZE_OF_FLOAT, 0);
		GLES30.glEnableVertexAttribArray(0);

		GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, uvBufferObject[0]);
		GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, (Float.SIZE / Byte.SIZE) * 12,
				BufferHelper.asFloatBuffer(uvs), GLES30.GL_STATIC_DRAW);
		GLES30.glVertexAttribPointer(2, 2,
				GLES30.GL_FLOAT, false,
				2 * Constants.SIZE_OF_FLOAT, 0);
		GLES30.glEnableVertexAttribArray(2);

		GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
		GLES30.glBindVertexArray(0);
	}

	public final void onDrawFrame() {
		modelMatrix = new float[16];
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix,0,translation.x,translation.y,translation.z);
		Matrix.scaleM(modelMatrix,0,scale.x,scale.y,scale.z);
		Matrix.rotateM(modelMatrix, 0, rotation.x, 1, 0, 0);
		Matrix.rotateM(modelMatrix, 0, rotation.y, 0, 1, 0);
		Matrix.rotateM(modelMatrix, 0, rotation.z, 0, 0, 1);

		GLES30.glBindVertexArray(vertexArrayObject[0]);
//		shader.setUniformMatrix4fv("model", glm.value_ptr(this.modelMatrix));
//		shader.setMaterialProperties(material);
		
//		float[] kernel = {1.0 / 16, 2.0 / 16, 1.0 / 16, 2.0 / 16, 4.0 / 16, 2.0 / 16, 1.0 / 16, 2.0 / 16, 1.0 / 16};
//
//		for (int i = 0; i < 9; i++)
//		{
//			String arrayLoc = "kernel[" + String.valueOf(i) + "]";
//			shader.setUniformFloat(arrayLoc, kernel[i]);
//		}

		GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 6);
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
		return this.vertexArrayObject[0];
	}
}
