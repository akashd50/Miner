package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.materials.textured.TexturedMaterial;

public class Quad extends Drawable {
	private float textureRatio;
	public Quad(String id) {
		super(id);
	}

	public Quad build() {
		if(getMaterial() instanceof TexturedMaterial) {
			textureRatio = getMaterial().asTexturedMaterial().getActiveMainTexture().getRatio();
		}

		if (textureRatio == 0.0f) {
			textureRatio = 1.0f;
		}
		textureRatio = 1.0f;

		super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(getShape().getVerticesArray(), 3, getRenderer().getShader(), ShaderConst.IN_POSITION);
		int uvBuffer = GLBufferHelper.putDataIntoArrayBuffer(getShape().getUVsArray(), 2, getRenderer().getShader(), ShaderConst.IN_UV);

		GLBufferHelper.glUnbindVertexArray();
		return this;
	}

	@Override
	public Quad setMaterial(Material material) {
		super.setMaterial(material);
		return this;
	}
}
