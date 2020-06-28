package com.greymatter.miner.opengl.objects.drawables.object3d;

import android.opengl.GLES30;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import java.util.*;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Object3D extends Drawable {
	private Object3DData object3DData;
	private int normalBufferObject;
	private int uvBufferObject;

	public Object3D(String id, String file, Material material, Shader shader) {
		super(id);
		super.setShader(shader);
		super.setMaterial(material);

		object3DData = Object3DHelper.load(Constants.OBJECTS + file);

		super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		int vertexBufferObject = GLBufferHelper.putDataIntoArrayBuffer(object3DData.arrayVertices, 3,
																getShader(), Constants.IN_POSITION);
		super.setVertexBufferObject(vertexBufferObject);

		normalBufferObject = GLBufferHelper.putDataIntoArrayBuffer(object3DData.arrayNormals, 3,
																getShader(), Constants.IN_NORMAL);
		uvBufferObject = GLBufferHelper.putDataIntoArrayBuffer(object3DData.arrayUvs, 2,
																getShader(), Constants.IN_UV);
		GLBufferHelper.glUnbindVertexArray();
	}

	public void onDrawFrame() {
		super.onDrawFrame();

		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		if(getMaterial()!=null) { ShaderHelper.setMaterialProperties(getShader(), getMaterial()); }

		ShaderHelper.setUniformMatrix4fv(getShader(), Constants.MODEL, getModelMatrix());

		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, object3DData.faceConfiguration.size() * 3);

		GLBufferHelper.glUnbindVertexArray();
	}

	public ArrayList<Vector3f> getOuterMesh() {
		if(object3DData.outerMesh==null) {
			object3DData.outerMesh = Object3DHelper.generateRoughMesh2(object3DData);
		}
		return object3DData.outerMesh;
	}

	@Override
	public boolean isClicked(Vector2f touchPoint) {
		return VectorHelper.isPointInPolygon(touchPoint, getCollider().asPolygonCollider().getTransformedVertices());
	}

	public Object3DData getObject3DData() {
		return object3DData;
	}
}
