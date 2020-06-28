package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Quad extends Drawable {
	private float textureRatio;
	public Quad(String id, Material material, Shader shader) {
		super(id);
		super.setShader(shader);
		super.setMaterial(material);

		initialize();
	}

	private void initialize() {
		textureRatio = getMaterial().getDiffuseTexture().getRatio();
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

		setVertexArrayObject(GLBufferHelper.glGenVertexArray());
		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(vertices, 3, getShader(), Constants.IN_POSITION);
		int uvBuffer = GLBufferHelper.putDataIntoArrayBuffer(uvs, 2, getShader(), Constants.IN_UV);

		GLBufferHelper.glUnbindVertexArray();
	}

	public void onDrawFrame() {
		super.onDrawFrame();

		GLBufferHelper.glBindVertexArray(getVertexArrayObject());
		ShaderHelper.setUniformMatrix4fv(getShader(), Constants.MODEL, getModelMatrix());
		ShaderHelper.setMaterialProperties(getShader(), getMaterial());

		GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 6);

		GLBufferHelper.glUnbindVertexArray();
	}

	@Override
	public boolean isClicked(Vector2f touchPoint) {
		float left = (-1f * textureRatio * getCollider().getScale().x + getCollider().getTranslation().x);
		float right = (1f * textureRatio * getCollider().getScale().x + getCollider().getTranslation().x);
		float top = (1f * getCollider().getScale().y + getCollider().getTranslation().y);
		float bottom = (-1f * getCollider().getScale().y + getCollider().getTranslation().y);
		return (touchPoint.x > left && touchPoint.x < right
		&& touchPoint.y < top && touchPoint.y > bottom);
	}
}
