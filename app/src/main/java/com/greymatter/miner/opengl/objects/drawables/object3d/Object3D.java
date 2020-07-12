package com.greymatter.miner.opengl.objects.drawables.object3d;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.mainui.touch.touchcheckers.PolygonTouchChecker;
import com.greymatter.miner.Res;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

import java.util.*;

import javax.vecmath.Vector3f;

public class Object3D extends Drawable {
	private Object3DData object3DData;
	private int normalBufferObject;
	private int uvBufferObject;

	public Object3D(String id) {
		super(id);
	}

	public Object3D load(String dataFile) {
		object3DData = Object3DHelper.load(Res.OBJECTS_F + dataFile);
		return this;
	}

	public Object3D build() {
		super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		int vertexBufferObject = GLBufferHelper.putDataIntoArrayBuffer(object3DData.arrayVertices, 3,
				getShader(), ShaderConst.IN_POSITION);
		super.setVertexBufferObject(vertexBufferObject);

//		normalBufferObject = GLBufferHelper.putDataIntoArrayBuffer(object3DData.arrayNormals, 3,
//				getShader(), Constants.IN_NORMAL);
		uvBufferObject = GLBufferHelper.putDataIntoArrayBuffer(object3DData.arrayUvs, 2,
				getShader(), ShaderConst.IN_UV);

		GLBufferHelper.glUnbindVertexArray();

		attachPolygonCollider();
		attachPolygonTouchChecker();
		return this;
	}

	public void onDrawFrame() {
		super.onDrawFrame();

		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		if(getMaterial()!=null) { ShaderHelper.setMaterialProperties(getShader(), getMaterial()); }

		ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, getTransforms().getModelMatrix());

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
	public Object3D attachPolygonTouchChecker() {
		setTouchChecker(new PolygonTouchChecker(getRigidBody().asPolygonRB()));
		return this;
	}

	@Override
	public Object3D attachPolygonCollider() {
		this.setRigidBody(new PolygonRB(this.getOuterMesh()));
		return this;
	}

	public Object3D attachOptimisedPolygonCollider(float optFac) {
		this.setRigidBody(new PolygonRB(Object3DHelper.simplify(this.getOuterMesh(), optFac)));
		return this;
	}

	@Override
	public Object3D setShader(Shader shader) {
		super.setShader(shader);
		return this;
	}

	@Override
	public Object3D setMaterial(Material material) {
		super.setMaterial(material);
		return this;
	}
}
