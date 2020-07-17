package com.greymatter.miner.opengl.objects.drawables.object3d;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.mainui.touch.touchcheckers.PolygonTouchChecker;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

import java.util.*;

import javax.vecmath.Vector3f;

public class Obj extends Drawable {
	private int normalBufferObject;
	private int uvBufferObject;
	public Obj(ObjId id) {
		super(id);
	}

	@Override
	public Obj build() {
		super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		int vertexBufferObject = GLBufferHelper.putDataIntoArrayBuffer(getShape().getVerticesArray(), 3,
				getShader(), ShaderConst.IN_POSITION);
		super.setVertexBufferObject(vertexBufferObject);

//		normalBufferObject = GLBufferHelper.putDataIntoArrayBuffer(object3DData.arrayNormals, 3,
//				getShader(), Constants.IN_NORMAL);
		uvBufferObject = GLBufferHelper.putDataIntoArrayBuffer(getShape().getUVsArray(), 2,
				getShader(), ShaderConst.IN_UV);

		GLBufferHelper.glUnbindVertexArray();

		attachPolygonCollider();
		attachPolygonTouchChecker();
		return this;
	}

	public void onDrawFrame() {
		super.onDrawFrame();

		GLBufferHelper.glBindVertexArray(getVertexArrayObject());

		ShaderHelper.setMaterialProperties(getShader(), getMaterial());

		ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, getTransforms().getModelMatrix());

		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, getShape().getRawObjData().faceConfiguration.size() * 3);

		GLBufferHelper.glUnbindVertexArray();
	}

	public ArrayList<Vector3f> getOuterMesh() {
		return getShape().getOrderedOuterMesh();
	}

	public RawObjData getRawObjData() {
		return getShape().getRawObjData();
	}

	@Override
	public Obj attachPolygonTouchChecker() {
		setTouchChecker(new PolygonTouchChecker(getRigidBody().asPolygonRB()));
		return this;
	}

	@Override
	public Obj attachPolygonCollider() {
		this.setRigidBody(new PolygonRB(this.getOuterMesh()));
		return this;
	}

	public Obj attachOptimisedPolygonCollider(float optFac) {
		this.setRigidBody(new PolygonRB(ObjHelper.simplify(this.getOuterMesh(), optFac)));
		return this;
	}
}
