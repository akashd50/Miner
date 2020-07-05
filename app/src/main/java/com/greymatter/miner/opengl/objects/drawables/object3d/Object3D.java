package com.greymatter.miner.opengl.objects.drawables.object3d;

import android.opengl.GLES30;

import com.greymatter.miner.mainui.touch.touchcheckers.PolygonTouchChecker;
import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.PolygonCollider;

import java.util.*;

import javax.vecmath.Vector3f;

public class Object3D extends Drawable {
	private Object3DData object3DData;
	private int normalBufferObject;
	private int uvBufferObject;

	public Object3D(String id, String file, Material material, Shader shader) {
		super(id);
		super.setShader(shader);
		super.setMaterial(material);

		object3DData = Object3DHelper.load(Constants.OBJECTS_F + file);

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

		withPolygonCollider();
		withPolygonTouchChecker();
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

	public Object3DData getObject3DData() {
		return object3DData;
	}

	@Override
	public Object3D withPolygonTouchChecker() {
		setTouchChecker(new PolygonTouchChecker(this, getCollider().asPolygonCollider()));
		return this;
	}

	@Override
	public Object3D withPolygonCollider() {
		this.setCollider(new PolygonCollider(this.getOuterMesh()));
		return this;
	}

	public Object3D withOptimisedPolygonCollider(float optFac) {
		this.setCollider(new PolygonCollider(Object3DHelper.simplify(this.getOuterMesh(), optFac)));
		return this;
	}
}
