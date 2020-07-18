package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.shader.ShaderHelper;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.materials.textured.TexturedMaterial;
import com.greymatter.miner.opengl.shader.Shader;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class Quad extends Drawable {
	private float textureRatio;
	private ArrayList<Vector3f> mesh;
	private Shape shape;
	public Quad(ObjId id) {
		super(id);
	}

	public Quad load(Shape shape) {
		this.shape = shape;
		return this;
	}

	public Quad build() {
		if(getMaterial() instanceof TexturedMaterial) {
			textureRatio = getMaterial().asTexturedMaterial().getActiveDiffuseTexture().getRatio();
		}

		if (textureRatio == 0.0f) {
			textureRatio = 1.0f;
		}
		textureRatio = 1.0f;

		super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(shape.getVerticesArray(), 3, getShader(), ShaderConst.IN_POSITION);
		int uvBuffer = GLBufferHelper.putDataIntoArrayBuffer(shape.getUVsArray(), 2, getShader(), ShaderConst.IN_UV);

		GLBufferHelper.glUnbindVertexArray();
		return this;
	}

	public void onDrawFrame() {
		super.onDrawFrame();

		GLBufferHelper.glBindVertexArray(getVertexArrayObject());
		ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, getTransforms().getModelMatrix());
		getMaterial().setShaderProperties(getShader());

		GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 4);

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
	public Quad setShader(Shader shader) {
		super.setShader(shader);
		return this;
	}

	@Override
	public Quad setMaterial(Material material) {
		super.setMaterial(material);
		return this;
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
