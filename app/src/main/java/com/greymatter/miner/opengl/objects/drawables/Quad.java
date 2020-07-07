package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.materials.textured.TexturedMaterial;
import com.greymatter.miner.opengl.objects.Shader;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class Quad extends Drawable {
	private float textureRatio;
	private ArrayList<Vector3f> mesh;
	public Quad(String id, TexturedMaterial material, Shader shader) {
		super(id);
		super.setShader(shader);
		super.setMaterial(material);

		initialize();
	}

	private void initialize() {
		if(getMaterial() instanceof TexturedMaterial) {
			textureRatio = getMaterial().asTexturedMaterial().getActiveDiffuseTexture().getRatio();
		}

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

		super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
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

	public ArrayList<Vector3f> getObjectMesh() {
		if(mesh==null){
			mesh = new ArrayList<>();
			mesh.add(new Vector3f(1.0f * textureRatio, 1.0f, 0.0f));
			mesh.add(new Vector3f(-1.0f * textureRatio, 1.0f, 0.0f));
			mesh.add(new Vector3f(-1.0f * textureRatio, -1.0f, 0.0f));
			mesh.add(new Vector3f(1.0f * textureRatio, -1.0f, 0.0f));
			//add the first point again to complete the mesh
			mesh.add(new Vector3f(1.0f * textureRatio, 1.0f, 0.0f));
		}
		return mesh;
	}

	@Override
	public Quad attachPolygonTouchChecker() {
		return this;
	}

	@Override
	public Quad attachPolygonCollider() {
		return this;
	}
}
